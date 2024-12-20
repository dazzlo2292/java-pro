package ru.otus.java.pro.spring.data.jdbc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public final class GameDto{
    private UUID uuid;
    private String title;
    private String genre;

    public GameDto() {
    }
}
