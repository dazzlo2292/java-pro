package ru.otus.java.pro.spring.data.jdbc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GamesPageDto {
    private List<GameDto> games;
}
