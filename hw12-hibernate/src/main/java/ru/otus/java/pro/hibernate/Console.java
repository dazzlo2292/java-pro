package ru.otus.java.pro.hibernate;

import java.util.Scanner;

public class Console {
    private final CommandHandler commandHandler;

    public Console(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
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
                        commandHandler.getProducts(Integer.parseInt(options[1]));
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
                        commandHandler.getCustomers(Integer.parseInt(options[1]));
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
                        commandHandler.deleteEntity(type, Integer.parseInt(options[2]));
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
