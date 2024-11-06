package ru.otus.java.pro.patterns2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DataSource {
    private static final Connection CONNECTION;

    static {
        try {
            CONNECTION = DriverManager.getConnection("jdbc:postgresql://localhost:5432/otus_db", "otus", "otus");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource() {}

    public static Connection getConnection() {
        return CONNECTION;
    }
}
