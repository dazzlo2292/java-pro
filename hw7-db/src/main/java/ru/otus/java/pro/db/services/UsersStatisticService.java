package ru.otus.java.pro.db.services;

import ru.otus.java.pro.db.User;
import ru.otus.java.pro.db.UsersDao;

public class UsersStatisticService {
    private final UsersDao usersDao;

    public UsersStatisticService(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public void businessLogicMethod(Long id) {
        User user = usersDao.getUserById(id).get();
        // ... какая-то обработка данных по юзеру
        System.out.println(user);
    }
}
