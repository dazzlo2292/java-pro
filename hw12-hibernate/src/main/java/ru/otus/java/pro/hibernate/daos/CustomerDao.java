package ru.otus.java.pro.hibernate.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.java.pro.hibernate.entities.Customer;
import ru.otus.java.pro.hibernate.entities.Product;

public class CustomerDao {
    private final SessionFactory sessionFactory;

    public CustomerDao(SessionFactory sessionFactory) {
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

    public void delete(Class<?> cls, int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(session.get(cls,id));
        session.getTransaction().commit();
        System.out.println("Customer (id = " + id + ") deleted");
        session.close();
    }
}
