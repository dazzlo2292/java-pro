package ru.otus.java.pro.spring.boot.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.spring.boot.Product;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductsRepository {
    private final ProductsStorage productStorage;

    public void save(Product product) {
        productStorage.getProducts().add(product);
    }

    public List<Product> getProducts() {
        return productStorage.getProducts();
    }
}
