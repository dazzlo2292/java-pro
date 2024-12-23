package ru.otus.java.pro.spring.data.jdbc.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.spring.data.jdbc.entities.Game;

import java.util.UUID;

@Repository
public interface GamesRepository extends ListCrudRepository<Game, UUID> {
    @Modifying
    @Query("delete from games_tab where uuid = :uuid")
    void deleteByUuid(@Param("uuid") UUID uuid);
}
