package com.rayan.notification.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Bean
    public MessageConverter jsonMessageConverter() {
        logger.info("Creating Jackson JSON message converter bean");
        return new Jackson2JsonMessageConverter();
    }
}
