package com.rayan.notification.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for sending notifications using Amazon SNS.
 */
@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final AmazonSNS amazonSNS;

    public NotificationService(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public void sendNotification(String phoneNumber, String message) {
        PublishRequest publishRequest = new PublishRequest()
            .withPhoneNumber(phoneNumber)
            .withMessage(message);

        try {
            PublishResult publishResult = amazonSNS.publish(publishRequest);
            logger.info("Notification sent successfully. Message ID: {}", publishResult.getMessageId());
        } catch (Exception e) {
            logger.error("Failed to send notification to phone number: {}", phoneNumber, e);
        }
    }
}
