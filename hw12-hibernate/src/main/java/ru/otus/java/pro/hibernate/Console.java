package ru.otus.java.pro.hibernate;

import ru.otus.java.pro.hibernate.daos.CustomerDao;
import ru.otus.java.pro.hibernate.daos.ProductDao;
import ru.otus.java.pro.hibernate.entities.Customer;
import ru.otus.java.pro.hibernate.entities.Product;

import java.util.Scanner;

public class Console {
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public Console(CustomerDao customerDao, ProductDao productDao) {
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public void start() {
        boolean active = true;
        Scanner scanner = new Scanner(System.in);
        String[] options;

        System.out.println("""
                Select option:
                /products — search for products that the customer has purchased.
                    Format: /products {customerId}
                    Example: /products 1
                /customers — search customers who have bought the product.
                    Format: /customers {productId}
                    Example: /customers 1
                /delete — entity deletion.
                    Format: /delete {type} {id}
                    Example: /delete customer 1
                /exit — close app
                """);

        while (active) {
            options = scanner.nextLine().split(" ");

            switch (options[0]) {
                case "/products":
                    if (options.length != 2) {
                        System.out.println("Incorrect params!");
                        break;
                    }
                    try {
                        customerDao.getProducts(Integer.parseInt(options[1]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/customers":
                    if (options.length != 2) {
                        System.out.println("Incorrect params!");
                        break;
                    }
                    try {
                        productDao.getCustomers(Integer.parseInt(options[1]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/delete":
                    if (options.length != 3) {
                        System.out.println("Incorrect params!");
                        break;
                    }
                    try {
                        String type = options[1];
                        int id = Integer.parseInt(options[2]);
                        if (type.equals("product")) {
                            productDao.delete(Product.class, id);
                        }
                        if (type.equals("customer")) {
                            customerDao.delete(Customer.class, id);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/exit":
                    active = false;
                    break;
                default:
                    System.out.println("Option not found");
            }
        }
    }
}
