package ru.otus.java.pro.jpql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.java.pro.jpql.entities.Address;
import ru.otus.java.pro.jpql.entities.Client;
import ru.otus.java.pro.jpql.entities.Phone;

public class DataFiller {
    private final SessionFactory sessionFactory;

    public DataFiller(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void start() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Client c1 = new Client("Client 1", new Address("Street 1"));
        c1.getPhones().add(new Phone("79998887711", c1));

        Client c2 = new Client("Client 2", new Address("Street 2"));
        c2.getPhones().add(new Phone("79998887722", c2));
        c2.getPhones().add(new Phone("79998887723", c2));

        if (c1.getId() == 0) {
            session.persist(c1);
        } else {
            session.merge(c1);
        }

        if (c2.getId() == 0) {
            session.persist(c2);
        } else {
            session.merge(c2);
        }

        session.getTransaction().commit();
        session.close();

    }
}
