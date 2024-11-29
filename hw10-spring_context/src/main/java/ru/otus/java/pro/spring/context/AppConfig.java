package ru.otus.java.pro.spring.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Scope("prototype")
    @Bean
    public Cart cart(ProductRepository repository) {
        return new Cart(repository);
    }
}
