package ru.otus.java.pro.spring.boot;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Product {
    private final int id;
    private final String title;
    private final int price;
}
