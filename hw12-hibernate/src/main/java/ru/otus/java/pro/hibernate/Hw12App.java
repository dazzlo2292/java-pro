package ru.otus.java.pro.hibernate;

import org.hibernate.SessionFactory;
import ru.otus.java.pro.hibernate.configurations.JavaBased;
import ru.otus.java.pro.hibernate.daos.CustomerDao;
import ru.otus.java.pro.hibernate.daos.ProductDao;


public class Hw12App {
    public static void main(String[] args) {
        try (SessionFactory factory = JavaBased.prepare()) {
            DataFiller dataFiller = new DataFiller(factory);
            dataFiller.start();

            CustomerDao customerDao = new CustomerDao(factory);
            ProductDao productDao = new ProductDao(factory);

            Console console = new Console(customerDao, productDao);
            console.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
