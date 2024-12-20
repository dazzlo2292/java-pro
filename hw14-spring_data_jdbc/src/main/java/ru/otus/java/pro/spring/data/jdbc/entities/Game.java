package ru.otus.java.pro.spring.data.jdbc.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "games_tab")
@Data
public class Game implements Persistable<UUID> {
    @Id
    @Column(value = "uuid")
    private UUID uuid;

    @Column(value = "title")
    private String title;

    @Column(value = "genre")
    private String genre;

    @Transient
    private boolean isNew;

    public Game(UUID uuid, String title, String genre) {
        this.uuid = uuid;
        this.title = title;
        this.genre = genre;
    }

    @Override
    public UUID getId() {
        return uuid;
    }
}
