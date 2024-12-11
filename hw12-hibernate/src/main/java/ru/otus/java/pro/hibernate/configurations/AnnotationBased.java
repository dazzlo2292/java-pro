package ru.otus.java.pro.hibernate.configurations;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AnnotationBased {
    public static SessionFactory prepare() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}
