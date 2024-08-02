package com.rayan.credit_analysis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConfiguration.class);

    private final String exchange;

    public RabbitMQConfiguration(@Value(value = "${rabbitmq.completed.proposal.exchange}") String exchange) {
        this.exchange = exchange;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        LOGGER.debug("Creating RabbitAdmin with ConnectionFactory.");
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> {
            LOGGER.info("Initializing RabbitAdmin.");
            rabbitAdmin.initialize();
        };
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        LOGGER.debug("Creating Jackson2JsonMessageConverter.");
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        LOGGER.debug("Creating RabbitTemplate with ConnectionFactory.");
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue proposalQueue() {
        String queueName = "completed-proposal.ms-proposal";
        LOGGER.debug("Creating Queue: {}", queueName);
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Queue notificationQueue() {
        String queueName = "completed-proposal.ms-notification";
        LOGGER.debug("Creating Queue: {}", queueName);
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        LOGGER.debug("Creating FanoutExchange: {}", exchange);
        return new FanoutExchange(exchange, true, false);
    }

    @Bean
    public Binding bindingProposalQueueToExchange() {
        LOGGER.debug("Binding Queue to FanoutExchange.");
        return BindingBuilder.bind(proposalQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingNotificationQueueToExchange() {
        LOGGER.debug("Binding Queue to FanoutExchange.");
        return BindingBuilder.bind(notificationQueue()).to(fanoutExchange());
    }
}

