package com.rayan.proposal.config;

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


/**
 * Configuration class for RabbitMQ message broker integration.
 */
@Configuration
public class RabbitMQConfig {


    @Value("${rabbitmq.pending.proposal.exchange}")
    private final String proposalExchangeName;


    public RabbitMQConfig(
        @Value(value = "${rabbitmq.pending.proposal.exchange}") String proposalExchangeName
    ) {
        this.proposalExchangeName = proposalExchangeName;
    }

    /**
     * Creates a RabbitAdmin bean for managing RabbitMQ exchanges, queues, and bindings.
     *
     * @param connectionFactory Connection factory for interacting with RabbitMQ.
     * @return RabbitAdmin bean instance.
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * Application listener to initialize RabbitMQ admin after application startup.
     *
     * @param rabbitAdmin RabbitAdmin bean instance.
     * @return ApplicationListener bean for automatic initialization.
     */
    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    /**
     * Creates a Jackson2JsonMessageConverter bean for converting messages to JSON format.
     *
     * @return Jackson2JsonMessageConverter bean instance.
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Creates a RabbitTemplate bean for sending messages to RabbitMQ.
     *
     * @param connectionFactory Connection factory for interacting with RabbitMQ.
     * @return RabbitTemplate bean instance configured with JSON message converter.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * Creates a durable queue named "pending-proposal.ms-credit-analysis" for credit analysis service.
     *
     * @return Queue bean instance for credit analysis messages.
     */
    @Bean
    public Queue creditAnalysisQueue() {
        return QueueBuilder.durable("pending-proposal.ms-credit-analysis")
            .deadLetterExchange("pending-proposal-dlx.ex")
            .maxPriority(10)
            .build();
    }


    /**
     * Creates a durable queue named "pending-proposal.ms-notification" for notification service.
     *
     * @return Queue bean instance for notification messages.
     */
    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable("pending-proposal.ms-notification").build();
    }

    @Bean
    public Queue pendingProposalDLQ() {
        return QueueBuilder.durable("pending-proposal.dlq").build();
    }

    /**
     * Creates a FanoutExchange bean for routing proposal messages to multiple queues.
     *
     * @return FanoutExchange bean instance with configured name, durability, and auto-delete behavior.
     */
    @Bean
    public FanoutExchange proposalExchange() {
        return new FanoutExchange(proposalExchangeName, true, false);
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder.fanoutExchange("pending-proposal-dlx.ex").build();
    }
    /**
     * Binds the "pending-proposal.ms-credit-analysis" queue to the "proposalExchange".
     *
     * @return Binding bean instance for routing messages to credit analysis service.
     */
    @Bean
    public Binding creditAnalysisBinding() {
        return BindingBuilder.bind(creditAnalysisQueue()).to(proposalExchange());
    }

    /**
     * Binds the "pending-proposal.ms-notification" queue to the "proposalExchange".
     *
     * @return Binding bean instance for routing messages to notification service.
     */
    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(proposalExchange());
    }

    @Bean
    public Binding pendingProposalDlqBinding() {
        return BindingBuilder.bind(pendingProposalDLQ()).to(deadLetterExchange());
    }
}
