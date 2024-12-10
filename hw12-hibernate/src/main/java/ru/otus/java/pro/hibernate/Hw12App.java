package ru.otus.java.pro.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Hw12App {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Customer customer = session.get(Customer.class, 1);
        Customer inserted = session.merge(customer);

        session.getTransaction().commit();

        session.close();
    }
}
