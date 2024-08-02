package com.rayan.notification.listener;

import com.rayan.notification.constante.Message;
import com.rayan.notification.domain.ProposalMessagePayload;
import com.rayan.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CompletedProposalListener {

    private static final Logger logger = LoggerFactory.getLogger(CompletedProposalListener.class);

    private final NotificationService notificationService;

    public CompletedProposalListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.completed.proposal}")
    public void onProposalCompleted(ProposalMessagePayload payload) {
        String notificationMessage = createNotificationMessage(payload);
        try {
            notificationService.sendNotification(payload.phone(), notificationMessage);
            logger.info("Notification sent for proposal ID: {}", payload.id());
        } catch (Exception e) {
            logger.error("Failed to send notification for proposal ID: {}", payload.id(), e);
        }
    }

    private String createNotificationMessage(ProposalMessagePayload payload) {
        if (payload.approved()) {
            return String.format(Message.PROPOSAL_APPROVED, payload.name());
        } else {
            return String.format(Message.PROPOSAL_NOT_APPROVED, payload.name(), payload.observation());
        }
    }
}
