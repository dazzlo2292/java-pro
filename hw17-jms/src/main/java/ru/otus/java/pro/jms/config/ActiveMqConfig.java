package ru.otus.java.pro.jms.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class ActiveMqConfig {
    private static final String CONNECTION_FACTORY = "activeMqConnectionFactory";

    public static final String JMS_TEMPLATE = "activeMqJmsTemplate";
    public static final String JMS_LISTENER_CONTAINER_FACTORY = "activeMqJmsListenerContainerFactory";

    public static final String CLASS_NAME = "className";

    public static final String DESTINATION_NAME = "local.otus-java.messages.queue-1";


    @Bean(CONNECTION_FACTORY)
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        connectionFactory.setUserName("jmx");
        connectionFactory.setPassword("password");
        connectionFactory.setTrustedPackages(List.of("ru.otus.java.pro.jms.dto", "java.util"));
        return connectionFactory;
    }

    @Bean(JMS_TEMPLATE)
    public JmsTemplate jmsTemplate(@Qualifier(CONNECTION_FACTORY) ConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setReceiveTimeout(TimeUnit.SECONDS.toMillis(10));
        return jmsTemplate;
    }

    @Bean(JMS_LISTENER_CONTAINER_FACTORY)
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            @Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

}
