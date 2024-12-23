package ru.otus.java.pro.spring.boot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.spring.boot.Product;
import ru.otus.java.pro.spring.boot.dtos.ProductDto;
import ru.otus.java.pro.spring.boot.repositories.ProductsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductsRepository productsRepository;

    public Product getProductById(int id) {
        return productsRepository.getProducts().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Product> getAllProducts() {
        return productsRepository.getProducts();
    }

    public Product createNewProduct(ProductDto productDto) {
        Product product = new Product(nextId(), productDto.title(), productDto.price());
        productsRepository.save(product);
        return product;
    }

    private int nextId() {
        return productsRepository.getProducts().stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0) + 1;
    }
}
