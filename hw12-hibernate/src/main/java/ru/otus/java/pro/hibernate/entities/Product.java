package ru.otus.java.pro.hibernate.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products_tab")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @ManyToMany
    @JoinTable (
            name = "customers_products_tab",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customers;

    public Product() {
    }

    public Product(String title, int price) {
        this.title = title;
        this.price = price;
        this.customers = new ArrayList<>();
    }

    public Product(int id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.customers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price + "}";
    }
}
