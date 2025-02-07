package ru.otus.java.pro.jms.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.jms.dto.MessageDto;
import ru.otus.java.pro.jms.services.ActiveMqProducer;


@RestController
@RequestMapping("/messages")
public class MessageController {
    private final ActiveMqProducer activeMqProducer;

    public MessageController(ActiveMqProducer activeMqProducer) {
        this.activeMqProducer = activeMqProducer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMessage(@RequestBody MessageDto messageDto) {
        activeMqProducer.convertAndSend(messageDto);
    }
}
