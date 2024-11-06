package ru.otus.java.pro.patterns2.db;

import ru.otus.java.pro.patterns2.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    Connection connection;

    public ItemDao(Connection connection) {
        this.connection = connection;
    }

    public List<Item> getItemList() {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(Queries.Q_GET_ALL_ITEMS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getInt("id"),rs.getString("title"),rs.getInt("price"));
                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query execution error", e);
        }
        return items;
    }

    public void save(Item item) {
        try (PreparedStatement ps = connection.prepareStatement(Queries.Q_ADD_ITEM)) {
            ps.setString(1,item.title());
            ps.setInt(2,item.price());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Query execution error", e);
        }
    }

    public void setPrice(Item item, int price) {
        try (PreparedStatement ps = connection.prepareStatement(Queries.Q_SET_PRICE)) {
            ps.setInt(1, price);
            ps.setInt(2, item.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Query execution error", e);
        }
    }
}
