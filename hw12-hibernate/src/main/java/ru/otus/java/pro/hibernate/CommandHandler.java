package ru.otus.java.pro.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.java.pro.hibernate.entities.Customer;
import ru.otus.java.pro.hibernate.entities.Product;

public class CommandHandler {
    private final SessionFactory sessionFactory;

    public CommandHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void getProducts(int customerId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Customer customer = session.get(Customer.class, customerId);
        for (Product p : customer.getProducts()) {
            System.out.println(p);
        }
        session.close();
    }

    public void getCustomers(int productId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Product product = session.get(Product.class, productId);
        for (Customer c : product.getCustomers()) {
            System.out.println(c);
        }
        session.close();
    }

    public void deleteEntity(String type, int id) {
        Class<?> cls;
        if (type.equals("product")) {
            cls = Product.class;
            delete(cls,id);
            System.out.println("Product (id = " + id + ") deleted");
            return;
        }
        if (type.equals("customer")) {
            cls = Customer.class;
            delete(cls,id);
            System.out.println("Customer (id = " + id + ") deleted");
            return;
        }
        System.out.println("Entity type \"" + type + "\" not found!");
    }

    private void delete(Class<?> cls, int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(session.get(cls,id));
        session.getTransaction().commit();
        session.close();
    }
}
