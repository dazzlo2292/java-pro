package ru.otus.java.pro.spring.data.jdbc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.spring.data.jdbc.dtos.GameDto;
import ru.otus.java.pro.spring.data.jdbc.dtos.GamesPageDto;
import ru.otus.java.pro.spring.data.jdbc.entities.Game;
import ru.otus.java.pro.spring.data.jdbc.services.GamesService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping("/games")
public class GamesController {
    private final GamesService gamesService;

    private static final Function<Game, GameDto> ENTITY_TO_DTO = i -> new GameDto(i.getUuid(), i.getTitle(), i.getGenre());

    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto createNewGame(@RequestBody GameDto gameDto) {
        Game newGame = gamesService.createNewGame(gameDto);
        return new GameDto(newGame.getUuid(), newGame.getTitle(), newGame.getGenre());
    }

    @GetMapping(value = "/{uuid}")
    public GameDto getByUuid(@PathVariable("uuid")UUID uuid) {
        Optional<Game> game = gamesService.getGameByUuid(uuid);
        return game.map(value -> new GameDto(value.getUuid(), value.getTitle(), value.getGenre())).orElse(null);
    }

    @GetMapping
    public GamesPageDto getAll() {
        List<GameDto> games = gamesService.getAllGames().stream().map(ENTITY_TO_DTO).toList();
        return new GamesPageDto(games);
    }

    @DeleteMapping(value = "/{uuid}")
    public void deleteGameByUuid (@PathVariable("uuid")UUID uuid) {
        gamesService.deleteGameByUuid(uuid);
    }
}



