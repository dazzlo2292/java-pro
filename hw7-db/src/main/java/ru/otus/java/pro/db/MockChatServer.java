package ru.otus.java.pro.db;

import java.sql.SQLException;

public class MockChatServer {
    public static void main(String[] args) {
        DataSource dataSource = null;
        try {
            System.out.println("Сервер чата запущен");
            dataSource = new DataSource("jdbc:postgresql://localhost:5432/otus_db", "otus", "otus");
            dataSource.connect();

            UsersDao usersDao = new UsersDao(dataSource);

            DbMigrator migrator =  new DbMigrator(dataSource);
            migrator.migrate();

            System.out.println(usersDao.getAllUsers());
            AbstractRepository<User> usersRepository = new AbstractRepository<>(dataSource, User.class);
            // Добавление пользователей
            usersRepository.save(new User(null,"Test login", "Test password", "Test nickname"));
            usersRepository.save(new User(null,"Test login", "Test password", "Test nickname"));
            usersRepository.save(new User(null,"Test login", "Test password", "Test nickname"));
            // Удаление пользователя с Id = 1
            usersRepository.deleteById(1);
            // Изменение пользователя с Id = 2
            usersRepository.update(new User(2, "Update", "Update", "Update"));
            // Поиск пользователя с Id = 2
            System.out.println(usersRepository.findById(2));
            // Поиск всех пользователей
            for (User user : usersRepository.findAll()) {
                System.out.println(user);
            }

//            AuthenticationService authenticationService = new AuthenticationService(usersDao);
//            UsersStatisticService usersStatisticService = new UsersStatisticService(usersDao);
//            BonusService bonusService = new BonusService(dataSource);
//            bonusService.init();

            // Основная работа сервера чата
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
            System.out.println("Сервер чата завершил свою работу");
        }
    }
}
