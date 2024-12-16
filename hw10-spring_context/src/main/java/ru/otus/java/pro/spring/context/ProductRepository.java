package ru.otus.java.pro.spring.context;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository implements Repository<Product>{
    private final List<Product> products;

    public ProductRepository() {
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

    @Override
    public Product getById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        return products;
    }
}
