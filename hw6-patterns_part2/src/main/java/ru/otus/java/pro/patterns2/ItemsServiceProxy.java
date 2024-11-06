package ru.otus.java.pro.patterns2;

import ru.otus.java.pro.patterns2.db.ItemDao;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemsServiceProxy {
    private final Connection connection;
    private final ItemsService service;

    public ItemsServiceProxy(Connection connection) throws SQLException {
        this.connection = connection;
        this.service = new ItemsService(new ItemDao(this.connection));
    }

    public void createItems(int count) throws SQLException {
        try {
            connection.setAutoCommit(false);
            service.createItems(count);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void updateAllPrices() throws SQLException {
        try {
            connection.setAutoCommit(false);
            service.updateAllPrices();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
