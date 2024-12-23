package ru.otus.java.pro.spring.context;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final ProductRepository productRepository;
    private final List<Product> productsInCart;

    public Cart(ProductRepository productRepository) {
        this.productRepository = productRepository;
        productsInCart = new ArrayList<>();
    }

    public List<Product> getAllProducts() {
        return productsInCart;
    }

    private Product getProductById(int id) {
        for (Product p : productsInCart) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void addProductById(int id) {
        Product product = productRepository.getById(id);
        if (product == null) {
            System.out.println("Product not found in repository");
            return;
        }
        productsInCart.add(product);
    }

    public void deleteProductById(int id) {
        Product product = getProductById(id);
        if (product == null) {
            System.out.println("Product not found in cart");
            return;
        }
        productsInCart.remove(product);
    }
}
