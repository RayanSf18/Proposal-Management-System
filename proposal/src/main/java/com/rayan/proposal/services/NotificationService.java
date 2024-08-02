package com.rayan.proposal.services;

import com.rayan.proposal.dtos.ProposalMessagePayload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final RabbitTemplate rabbitTemplate;

    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(ProposalMessagePayload proposal, String exchange, MessagePostProcessor messagePostProcessor) {
        try {
            rabbitTemplate.convertAndSend(exchange, "", proposal, messagePostProcessor);
            logger.info("Notification sent for proposal ID {} to exchange {}", proposal.id(), exchange);
        } catch (Exception e) {
            logger.error("Failed to send notification for proposal ID {}: {}", proposal.id(), e.getMessage());

        }
    }

}
