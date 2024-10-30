package ru.otus.java.pro.patterns2;

import ru.otus.java.pro.patterns2.db.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Hw6App {
    public static void main(String[] args) {
        try (Connection connection = DataSource.getConnection()) {
            ItemsServiceProxy service = new ItemsServiceProxy(connection);

            service.createItems(10);
            service.updateAllPrices();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
