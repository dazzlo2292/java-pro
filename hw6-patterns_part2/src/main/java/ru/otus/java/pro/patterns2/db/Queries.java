package ru.otus.java.pro.patterns2.db;

public class Queries {
    public final static String Q_GET_ALL_ITEMS = """
            SELECT it.id, it.title, it.price FROM items_tab it;
            """;

    public final static String Q_ADD_ITEM = """
            INSERT INTO items_tab (title, price) VALUES (?, ?);
            """;

    public final static String Q_SET_PRICE = """
            UPDATE items_tab SET price = ? WHERE id = ?;
            """;
}
