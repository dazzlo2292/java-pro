package ru.otus.java.pro.db.services;

import ru.otus.java.pro.db.DataSource;

import java.sql.SQLException;

public class BonusService {
    private final DataSource dataSource;

    public BonusService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void init() throws SQLException {
        dataSource.getStatement().executeUpdate(
                "create table if not exists bonuses (" +
                        "    id          bigserial primary key," +
                        "    amount      int," +
                        "    login       varchar(255)" +
                        ")"
        );
        System.out.println("Сервис бонусов успешно запущен");
    }

    public void createBonus(String login, int amount) {
        // dataSource.getStatement()...
    }
}
