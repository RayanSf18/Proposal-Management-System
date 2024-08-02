package com.rayan.proposal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class for WebSocket setup, including STOMP endpoints and message broker configuration.
 */
@Configuration
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfiguration.class);

    private static final String PROPOSAL_TOPIC_PREFIX = "/proposals";
    private static final String ENDPOINT = "/ws";
    private static final String ALLOWED_ORIGIN = "http://localhost";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        logger.debug("Configuring message broker with prefix: {}", PROPOSAL_TOPIC_PREFIX);
        registry.enableSimpleBroker(PROPOSAL_TOPIC_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        logger.debug("Registering STOMP endpoint: {}", ENDPOINT);
        registry.addEndpoint(ENDPOINT)
            .setAllowedOrigins(ALLOWED_ORIGIN)
            .withSockJS();
    }

}
