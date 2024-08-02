package com.rayan.credit_analysis.service;

import com.rayan.credit_analysis.dto.messaging.ProposalMessagePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private final RabbitTemplate rabbitTemplate;

    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(String exchange, ProposalMessagePayload payload) {
        try {
            rabbitTemplate.convertAndSend(exchange, "", payload);
            LOGGER.info("Notification sent to exchange '{}': {}", exchange, payload);
        } catch (Exception e) {
            LOGGER.error("Failed to send notification to exchange '{}': {}", exchange, payload, e);
        }
    }
}
