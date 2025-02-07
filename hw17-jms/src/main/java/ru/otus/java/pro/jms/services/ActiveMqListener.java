package ru.otus.java.pro.jms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import ru.otus.java.pro.jms.config.ActiveMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveMqListener implements MessageListener {

    private final ObjectMapper objectMapper;

    @JmsListener(destination = ActiveMqConfig.DESTINATION_NAME
            , containerFactory = ActiveMqConfig.JMS_LISTENER_CONTAINER_FACTORY)
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                onObjectMessage((ObjectMessage) message);
            }
            else
                throw new IllegalArgumentException("Message Error");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void onObjectMessage(ObjectMessage message)
            throws JMSException, ClassNotFoundException, JsonProcessingException {
        String className = message.getStringProperty(ActiveMqConfig.CLASS_NAME);

        if (className == null)
            onSerializableObjectMessage(message);
        else
            onCustomObjectMessage(Class.forName(className), message);
    }

    private static void onSerializableObjectMessage(ObjectMessage message) throws JMSException {
        Serializable obj = message.getObject();
        log.info("Consume [serializable] - [client] <- [active-mq] : {}", obj);
    }

    private void onCustomObjectMessage(Class<?> cls, ObjectMessage message)
            throws JMSException, JsonProcessingException {
        String json = String.valueOf(message.getObject());
        Object obj = objectMapper.readValue(json, cls);
        log.info("Consume [{}] - [client] <- [active-mq] : {}", cls.getSimpleName(), obj);
    }
}
