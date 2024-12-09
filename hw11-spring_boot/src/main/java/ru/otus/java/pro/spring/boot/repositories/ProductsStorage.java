package ru.otus.java.pro.spring.boot.repositories;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.spring.boot.Product;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class ProductsStorage {
    private final List<Product> products;

    public ProductsStorage() {
        products = new ArrayList<>();
        products.add(new Product(1,"Product 1", 1));
        products.add(new Product(2,"Product 2", 2));
        products.add(new Product(3,"Product 3", 3));
        products.add(new Product(4,"Product 4", 4));
        products.add(new Product(5,"Product 5", 5));
        products.add(new Product(6,"Product 6", 6));
        products.add(new Product(7,"Product 7", 7));
        products.add(new Product(8,"Product 8", 8));
        products.add(new Product(9,"Product 9", 9));
        products.add(new Product(10,"Product 10", 10));
    }
}
