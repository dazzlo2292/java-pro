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
    private String fieldName;

    private List<Field> cachedDataFields;
    private List<Field> cachedIdFields;
    private List<Field> cachedInsertFields;

    private Map<String, Method> cachedGetters;
    private Map<String, Method> cachedSetters;

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
        cachedInsertFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryInsertField.class))
                .collect(Collectors.toList());
        if (cachedInsertFields.isEmpty()) {
            throw new ORMException("Класс не предназначен для создания репозитория — нет полей с аннотацией @RepositoryInsertField");
        }

        cachedIdFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryIdField.class))
                .collect(Collectors.toList());
        if (cachedIdFields.isEmpty()) {
            throw new ORMException("Класс не предназначен для создания репозитория — нет поля с аннотацией @RepositoryIdField");
        }
        if (cachedIdFields.size() > 1) {
            throw new ORMException("Класс не предназначен для создания репозитория — более одного поля с аннотацией @RepositoryIdField");
        }

        cachedDataFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryDataField.class))
                .collect(Collectors.toList());

        // Getters
        cachedGetters = new HashMap<>();
        List<Method> gettersList = Arrays.stream(cls.getDeclaredMethods())
                .filter(f -> f.isAnnotationPresent(RepositoryGetter.class))
                .toList();

        for (Method method : gettersList) {
            cachedGetters.put(method.getAnnotation(RepositoryGetter.class).field(), method);
        }
        if (cachedGetters.isEmpty()) {
            throw new ORMException("Класс не предназначен для создания репозитория — нет методов с аннотацией @RepositoryGetter");
        }

        // Setters
        cachedSetters = new HashMap<>();
        List<Method> settersList = Arrays.stream(cls.getDeclaredMethods())
                .filter(f -> f.isAnnotationPresent(RepositorySetter.class))
                .toList();

        for (Method method : settersList) {
            cachedSetters.put(method.getAnnotation(RepositorySetter.class).field(), method);
        }
        if (cachedSetters.isEmpty()) {
            throw new ORMException("Класс не предназначен для создания репозитория — нет методов с аннотацией @RepositorySetter");
        }
    }

    public void save(T entity) {
        try {
            for (int i = 0; i < cachedInsertFields.size(); i++) {
                fieldName =  cachedInsertFields.get(i).getName();
                psInsert.setObject(i + 1, cachedGetters.get(fieldName).invoke(entity));
            }
            psInsert.executeUpdate();
        } catch (Exception e) {
            throw new ORMException("Что-то пошло не так при сохранении: " + entity);
        }
    }

    public void update(T entity) {
        try {
            for (int i = 0; i < cachedInsertFields.size(); i++) {
                fieldName =  cachedInsertFields.get(i).getName();
                psUpdate.setObject(i + 1, cachedGetters.get(fieldName).invoke(entity));
                if (i == cachedInsertFields.size() - 1) {
                    fieldName = cachedIdFields.get(0).getName();
                    psUpdate.setObject(i + 2, cachedGetters.get(fieldName).invoke(entity));
                }
            }
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
        for (Field cachedDataField : cachedDataFields) {
            fieldName = cachedDataField.getName();
            cachedSetters.get(fieldName).invoke(entity, rs.getObject(fieldName));
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
        for (Field f : cachedIdFields) {
            query.append(f.getAnnotation(RepositoryIdField.class).title()).append(" = ");
        }
        // 'select * from users_tab where id = '
        for (Field ignored : cachedIdFields) {
            query.append("?;");
        }
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
        for (Field f : cachedIdFields) {
            query.append(f.getAnnotation(RepositoryIdField.class).title()).append(" = ");
        }
        // 'delete from users_tab where id = '
        for (Field ignored : cachedIdFields) {
            query.append("?;");
        }
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
        for (Field f : cachedInsertFields) {
            query.append(f.getAnnotation(RepositoryInsertField.class).title()).append(", ");
        }
        // 'insert into users_tab (login, password, nickname, '
        query.setLength(query.length() - 2);
        query.append(") values (");
        // 'insert into users_tab (login, password, nickname) values ('
        for (Field ignored : cachedInsertFields) {
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
        for (Field f : cachedInsertFields) {
            query.append(f.getAnnotation(RepositoryInsertField.class).title()).append(" = ?, ");
        }
        // 'update from user_tab set login = ?, password = ?, nickname = ?, '
        query.setLength(query.length() - 2);
        query.append(" where ");
        // 'update from user_tab set login = ?, password = ?, nickname = ? where '
        for (Field f : cachedIdFields) {
            query.append(f.getAnnotation(RepositoryIdField.class).title()).append(" = ?;");
        }
        // 'update from user_tab set login = ?, password = ?, nickname = ? where id = ?;'
        try {
            psUpdate = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }
}
