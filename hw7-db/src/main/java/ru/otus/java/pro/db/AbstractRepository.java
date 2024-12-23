package ru.otus.java.pro.db;

import ru.otus.java.pro.db.annotations.*;
import ru.otus.java.pro.db.exceptions.ORMException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AbstractRepository<T> {
    private final DataSource dataSource;
    private final Class<T> clazz;

    private PreparedStatement psInsert;
    private PreparedStatement psDeleteById;
    private PreparedStatement psFindById;
    private PreparedStatement psFindAll;
    private PreparedStatement psUpdate;

    private String tableName;

    private Field cachedIdField;
    private List<Field> cachedDataFields;

    private Map<String,Method> cachedMethods;

    public AbstractRepository(DataSource dataSource, Class<T> cls) {
        this.dataSource = dataSource;
        this.clazz = cls;
        this.checkMarkup(cls);

        this.prepareInsert(cls);
        this.prepareDeleteById(cls);
        this.prepareFindById(cls);
        this.prepareFindAll(cls);
        this.prepareUpdate(cls);
    }

    private void checkMarkup(Class<T> cls) {
        if (!cls.isAnnotationPresent(RepositoryTable.class)) {
            throw new ORMException("Класс не предназначен для создания репозитория, не хватает аннотации @RepositoryTable");
        }
        tableName = cls.getAnnotation(RepositoryTable.class).title();

        // Fields
        cachedDataFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryDataField.class))
                .collect(Collectors.toList());
        if (cachedDataFields.isEmpty()) {
            throw new ORMException("Класс не предназначен для создания репозитория — нет полей с аннотацией @RepositoryInsertField");
        }

        for (int i = 0; i < cls.getDeclaredFields().length; i++) {
            Field idField = cls.getDeclaredFields()[i];
            if (idField.isAnnotationPresent(RepositoryIdField.class)) {
                if (cachedIdField == null) {
                    cachedIdField = idField;
                } else {
                    throw new ORMException("Класс не предназначен для создания репозитория — более одного поля с аннотацией @RepositoryIdField");
                }
            }
        }
        if (cachedIdField == null) {
            throw new ORMException("Класс не предназначен для создания репозитория — нет поля с аннотацией @RepositoryIdField");
        }

        List <Method> tempMethods = Arrays.stream(cls.getDeclaredMethods())
                .toList();
        cachedMethods = new HashMap<>();
        for (Method method : tempMethods) {
            cachedMethods.put(method.getName(), method);
        }
    }

    private String prepareMethodName(String prefix, Field field) {
        if (prefix.equals("get") && field.getType().getTypeName().equals("boolean")) {
            prefix = "is";
        }

        String methodName = prefix + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
        if (cachedMethods.get(methodName) == null) {
            throw new ORMException("В классе отсутствует метод: " + methodName);
        }
        return methodName;
    }

    public void save(T entity) {
        try {
            String methodName;
            for (int i = 0; i < cachedDataFields.size(); i++) {
                methodName =  prepareMethodName("get",cachedDataFields.get(i));
                psInsert.setObject(i + 1, cachedMethods.get(methodName).invoke(entity));
            }
            psInsert.executeUpdate();
        } catch (Exception e) {
            throw new ORMException("Что-то пошло не так при сохранении: " + entity);
        }
    }

    public void update(T entity) {
        try {
            String methodName;
            for (int i = 0; i < cachedDataFields.size(); i++) {
                methodName =  prepareMethodName("get",cachedDataFields.get(i));
                psUpdate.setObject(i + 1, cachedMethods.get(methodName).invoke(entity));
            }
            methodName = prepareMethodName("get",cachedIdField);
            psUpdate.setObject(cachedDataFields.size() + 1, cachedMethods.get(methodName).invoke(entity));
            psUpdate.executeUpdate();
        } catch (Exception e) {
            throw new ORMException("Что-то пошло не так при изменении: " + entity);
        }
    }

    public List<T> findAll() {
        T entity;
        List<T> entities = new ArrayList<>();
        try {
            ResultSet rs = psFindAll.executeQuery();
            while (rs.next()) {
                entity = createObject(clazz);
                fillEntityFields(rs, entity);
                entities.add(entity);
            }
        } catch (Exception e) {
            throw new ORMException("Что-то пошло не так при выборе всех сущностей");
        }
        return entities;
    }

    public T findById(Integer id) {
        T entity = null;
        try {
            psFindById.setLong(1, id);
            ResultSet rs = psFindById.executeQuery();
            while (rs.next()) {
                entity = createObject(clazz);
                fillEntityFields(rs, entity);
            }
        } catch (Exception e) {
            throw new ORMException("Что-то пошло не так при выборе сущности с ID = " + id);
        }
        return entity;
    }

    private void fillEntityFields(ResultSet rs, T entity) throws IllegalAccessException, SQLException, InvocationTargetException {
        String nameForSearch;
        nameForSearch = prepareMethodName("set",cachedIdField);
        cachedMethods.get(nameForSearch).invoke(entity, rs.getObject(cachedIdField.getAnnotation(RepositoryIdField.class).title()));

        for (Field cachedDataField : cachedDataFields) {
            nameForSearch = prepareMethodName("set",cachedDataField);
            cachedMethods.get(nameForSearch).invoke(entity, rs.getObject(cachedDataField.getAnnotation(RepositoryDataField.class).title()));
        }
    }

    private T createObject(Class<T> cls) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = cls.getConstructor();
        return constructor.newInstance();
    }

    public void deleteById(Integer id) {
        try {
            psDeleteById.setLong(1, id);
            psDeleteById.executeUpdate();
        } catch (Exception e) {
            throw new ORMException("Что-то пошло не так при удалении сущности с ID = " + id);
        }
    }

    private void prepareFindById(Class<T> cls) {
        StringBuilder query = new StringBuilder("select * from ");
        query.append(tableName).append(" where ");
        // 'select * from users_tab where '
        query.append(cachedIdField.getAnnotation(RepositoryIdField.class).title()).append(" = ?;");
        // 'select * from users_tab where id = ?;'
        try {
            psFindById = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }

    public void prepareFindAll(Class<T> cls) {
        StringBuilder query = new StringBuilder("select * from ");
        query.append(tableName);
        // 'select * from users_tab'
        try {
            psFindAll = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }

    private void prepareDeleteById(Class<T> cls) {
        StringBuilder query = new StringBuilder("delete from ");
        query.append(tableName).append(" where ");
        // 'delete from users_tab where '
        query.append(cachedIdField.getAnnotation(RepositoryIdField.class).title()).append(" = ?;");
        // 'delete from users_tab where id = ?;'
        try {
            psDeleteById = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }

    private void prepareInsert(Class<T> cls) {
        StringBuilder query = new StringBuilder("insert into ");
        query.append(tableName).append(" (");
        // 'insert into users_tab ('
        for (Field f : cachedDataFields) {
            query.append(f.getAnnotation(RepositoryDataField.class).title()).append(", ");
        }
        // 'insert into users_tab (login, password, nickname, '
        query.setLength(query.length() - 2);
        query.append(") values (");
        // 'insert into users_tab (login, password, nickname) values ('
        for (Field ignored : cachedDataFields) {
            query.append("?, ");
        }
        query.setLength(query.length() - 2);
        query.append(");");
        // 'insert into users_tab (login, password, nickname) values (?, ?, ?);'
        try {
            psInsert = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }

    private void prepareUpdate(Class<T> cls) {
        StringBuilder query = new StringBuilder("update ");
        query.append(tableName).append(" set ");
        // 'update from user_tab set '
        for (Field f : cachedDataFields) {
            query.append(f.getAnnotation(RepositoryDataField.class).title()).append(" = ?, ");
        }
        // 'update from user_tab set login = ?, password = ?, nickname = ?, '
        query.setLength(query.length() - 2);
        query.append(" where ");
        // 'update from user_tab set login = ?, password = ?, nickname = ? where '
        query.append(cachedIdField.getAnnotation(RepositoryIdField.class).title()).append(" = ?;");
        // 'update from user_tab set login = ?, password = ?, nickname = ? where id = ?;'
        try {
            psUpdate = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }
}
