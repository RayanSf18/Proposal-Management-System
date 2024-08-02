package com.rayan.proposal.services;

import com.rayan.proposal.dtos.ProposalResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketNotificationService.class);

    private final SimpMessagingTemplate messagingTemplate;


    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(ProposalResponsePayload proposalResponsePayload) {
        logger.info("Sending WebSocket notification with payload: {}", proposalResponsePayload);
        try {
            messagingTemplate.convertAndSend("/proposals", proposalResponsePayload);
            logger.info("WebSocket notification sent successfully.");
        } catch (Exception e) {
            logger.error("Failed to send WebSocket notification: {}", e.getMessage());
        }
    }
}
