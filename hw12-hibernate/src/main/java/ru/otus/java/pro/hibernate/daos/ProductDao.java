package ru.otus.java.pro.hibernate.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.java.pro.hibernate.entities.Customer;
import ru.otus.java.pro.hibernate.entities.Product;

public class ProductDao {
    private final SessionFactory sessionFactory;

    public ProductDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

    public void delete(Class<?> cls, int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(session.get(cls,id));
        session.getTransaction().commit();
        System.out.println("Product (id = " + id + ") deleted");
        session.close();
    }
}
