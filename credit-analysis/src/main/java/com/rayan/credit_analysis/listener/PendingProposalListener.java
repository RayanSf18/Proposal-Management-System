package com.rayan.credit_analysis.listener;

import com.rayan.credit_analysis.dto.messaging.ProposalMessagePayload;
import com.rayan.credit_analysis.service.CreditAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PendingProposalListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PendingProposalListener.class);

    private final CreditAnalysisService creditAnalysisService;

    public PendingProposalListener(CreditAnalysisService creditAnalysisService) {
        this.creditAnalysisService = creditAnalysisService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.pending.proposal}")
    public void listenerPendingProposal(ProposalMessagePayload payload) {
        creditAnalysisService.analyze(ProposalMessagePayload.toProposal(payload));
    }

    @RabbitListener(queues = "${rabbitmq.queue.pending.proposal}")
    public void onPendingProposalReceived(ProposalMessagePayload payload) {
        try {
            LOGGER.debug("Received proposal message: {}", payload);
            creditAnalysisService.analyze(ProposalMessagePayload.toProposal(payload));
            LOGGER.info("Successfully processed proposal with ID: {}", payload.id());
        } catch (Exception e) {
            LOGGER.error("Failed to process proposal message: {}", payload, e);
        }
    }
}
