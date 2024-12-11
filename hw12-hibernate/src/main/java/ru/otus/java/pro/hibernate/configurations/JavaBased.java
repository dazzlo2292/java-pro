package ru.otus.java.pro.hibernate.configurations;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.java.pro.hibernate.entities.Customer;
import ru.otus.java.pro.hibernate.entities.Product;

import java.util.Properties;

public class JavaBased {
    public static SessionFactory prepare() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/otus_db");
        properties.put("hibernate.connection.username", "otus");
        properties.put("hibernate.connection.password", "otus");
        properties.put("hibernate.current_session_context_class", "thread");
        properties.put("hibernate.hbm2ddl.auto", "create");
//        properties.put("hibernate.show_sql", "true");

        configuration.setProperties(properties);

        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Product.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}
