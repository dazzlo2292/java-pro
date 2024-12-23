package ru.otus.java.pro.spring.data.jdbc.services;

import org.springframework.stereotype.Service;
import ru.otus.java.pro.spring.data.jdbc.dtos.GameDto;
import ru.otus.java.pro.spring.data.jdbc.entities.Game;
import ru.otus.java.pro.spring.data.jdbc.repositories.GamesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GamesService {
    private final GamesRepository gamesRepository;

    public GamesService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public Game createNewGame(GameDto gameDto) {
        if (gameDto.getUuid() == null) {
            gameDto.setUuid(UUID.randomUUID());
        }

        Game newGame = new Game(gameDto.getUuid(), gameDto.getTitle(), gameDto.getGenre());
        if (!gamesRepository.existsById(newGame.getUuid())) {
            newGame.setNew(true);
        }
        return gamesRepository.save(newGame);
    }

    public Optional<Game> getGameByUuid(UUID uuid) {
        return gamesRepository.findById(uuid);
    }

    public List<Game> getAllGames() {
        return gamesRepository.findAll();
    }

    public void deleteGameByUuid(UUID uuid) {
        gamesRepository.deleteByUuid(uuid);
    }
}
