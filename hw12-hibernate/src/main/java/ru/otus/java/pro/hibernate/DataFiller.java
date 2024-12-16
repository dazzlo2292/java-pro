package ru.otus.java.pro.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.java.pro.hibernate.entities.Customer;
import ru.otus.java.pro.hibernate.entities.Product;

public class DataFiller {
    private final SessionFactory sessionFactory;

    public DataFiller(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void start() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Customer c1 = new Customer("Customer 1");
        Customer c2 = new Customer("Customer 2");
        Customer c3 = new Customer("Customer 3");

        Product p1 = new Product("Product 1", 10);
        Product p2 = new Product("Product 2", 50);
        Product p3 = new Product("Product 2", 100);

        session.merge(c1);
        session.merge(c2);
        session.merge(c3);

        session.merge(p1);
        session.merge(p2);
        session.merge(p3);

        session.getTransaction().commit();
        session.close();

        addReference();
    }

    private void addReference() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Customer c1 = session.get(Customer.class, 1);
        Customer c2 = session.get(Customer.class, 2);
        Customer c3 = session.get(Customer.class, 3);

        Product p1 = session.get(Product.class, 1);
        Product p2 = session.get(Product.class, 2);
        Product p3 = session.get(Product.class, 3);

        c1.getProducts().add(p1);
        c2.getProducts().add(p1);
        c2.getProducts().add(p2);
        c3.getProducts().add(p1);
        c3.getProducts().add(p2);
        c3.getProducts().add(p3);

        session.getTransaction().commit();
        session.close();
    }
}
