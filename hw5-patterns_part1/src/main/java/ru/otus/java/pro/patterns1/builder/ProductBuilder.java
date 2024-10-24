package ru.otus.java.pro.patterns1.builder;

public interface ProductBuilder {
    ProductBuilder id(int id);
    ProductBuilder title(String title);
    ProductBuilder description(String description);
}
