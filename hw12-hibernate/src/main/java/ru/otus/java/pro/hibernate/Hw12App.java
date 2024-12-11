package ru.otus.java.pro.hibernate;

import org.hibernate.SessionFactory;
import ru.otus.java.pro.hibernate.configurations.JavaBased;


public class Hw12App {
    public static void main(String[] args) {
        try (SessionFactory factory = JavaBased.prepare()) {
            DataFiller dataFiller = new DataFiller(factory);
            dataFiller.start();

            CommandHandler commandHandler = new CommandHandler(factory);
            Console console = new Console(commandHandler);
            console.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
