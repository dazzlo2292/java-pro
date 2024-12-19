package ru.otus.java.pro.spring.data.jdbc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.spring.data.jdbc.entities.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GamesController {
    private final GamesService gamesService;

    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game createNewGame(@RequestBody Game newGame) {
        return gamesService.createNewGame(newGame);
    }

    @GetMapping(value = "/{uuid}")
    public Optional<Game> getByUuid(@PathVariable("uuid")UUID uuid) {
        return gamesService.getGameByUuid(uuid);
    }

    @GetMapping
    public List<Game> getAll() {
        return new ArrayList<>(gamesService.getAllGames());
    }

    @DeleteMapping(value = "/{uuid}")
    public void deleteGameByUuid (@PathVariable("uuid")UUID uuid) {
        gamesService.deleteGameByUuid(uuid);
    }
}



