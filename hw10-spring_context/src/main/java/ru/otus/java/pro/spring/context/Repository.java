package ru.otus.java.pro.spring.context;

import java.util.List;

public interface Repository<T> {
    T getById(int id);
    List<T> getAll();
}
