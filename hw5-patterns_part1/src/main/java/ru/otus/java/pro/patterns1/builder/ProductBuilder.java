package ru.otus.java.pro.patterns1.builder;

public interface ProductBuilder {
    ProductBuilder id(int id);
    ProductBuilder title(String title);
    ProductBuilder description(String description);
    ProductBuilder cost(int cost);
    ProductBuilder weight(int weight);
    ProductBuilder width(int width);
    ProductBuilder length(int length);
    ProductBuilder height(int length);
}
