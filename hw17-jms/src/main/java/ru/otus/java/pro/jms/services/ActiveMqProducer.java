package ru.otus.java.pro.jms.services;

import ru.otus.java.pro.jms.config.ActiveMqConfig;
import ru.otus.java.pro.jms.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ActiveMqProducer {

    private final JmsTemplate jmsTemplate;

    public ActiveMqProducer(@Qualifier(ActiveMqConfig.JMS_TEMPLATE) JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void convertAndSend(MessageDto messageDto) {
        if (messageDto.getUuid() == null) {
            messageDto.setUuid(UUID.randomUUID());
        }
        System.out.println("Produce [object] - [client] -> [active-mq] : " + messageDto);
        jmsTemplate.convertAndSend(ActiveMqConfig.DESTINATION_NAME, messageDto);
    }
}
