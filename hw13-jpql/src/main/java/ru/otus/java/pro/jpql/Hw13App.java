package ru.otus.java.pro.jpql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.java.pro.jpql.configurations.JavaBased;
import ru.otus.java.pro.jpql.entities.Client;

public class Hw13App {
    public static void main(String[] args) {
        try (SessionFactory factory = JavaBased.prepare()) {
            DataFiller dataFiller = new DataFiller(factory);
            dataFiller.start();

            Session session = factory.getCurrentSession();
            session.beginTransaction();
            System.out.println(session.get(Client.class, 2));
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
