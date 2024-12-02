package ru.otus.java.pro.spring.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Hw10App {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Hw10App.class);
		Cart cart = context.getBean(Cart.class);

		cart.addProductById(1);
		cart.addProductById(3);
		cart.addProductById(5);
		cart.deleteProductById(3);

		for (Product p : cart.getAllProducts()) {
			System.out.println(p);
		}
	}
}
