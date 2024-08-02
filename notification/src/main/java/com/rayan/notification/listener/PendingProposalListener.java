package com.rayan.notification.listener;

import com.rayan.notification.constante.Message;
import com.rayan.notification.domain.ProposalMessagePayload;
import com.rayan.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PendingProposalListener {

    private static final Logger logger = LoggerFactory.getLogger(PendingProposalListener.class);

    private final NotificationService notificationService;

    public PendingProposalListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.pending.proposal}")
    public void listenerPendingProposal(ProposalMessagePayload payload) {
        String message = String.format(Message.PROPOSAL_UNDER_ANALYSIS, payload.name());

        notificationService.sendNotification(payload.phone(), message);
    }

    @RabbitListener(queues = "${rabbitmq.queue.pending.proposal}")
    public void handlePendingProposal(ProposalMessagePayload payload) {
        String message = String.format(Message.PROPOSAL_UNDER_ANALYSIS, payload.name());

        try {
            notificationService.sendNotification(payload.phone(), message);
            logger.info("Sent pending proposal notification for: {}", payload.name());
        } catch (Exception e) {
            logger.error("Failed to send notification for pending proposal: {}", payload.name(), e);
        }
    }
}
