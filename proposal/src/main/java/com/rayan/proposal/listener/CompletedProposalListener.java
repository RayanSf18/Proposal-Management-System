package com.rayan.proposal.listener;

import com.rayan.proposal.dtos.ProposalMessagePayload;
import com.rayan.proposal.dtos.ProposalResponsePayload;
import com.rayan.proposal.entities.Proposal;
import com.rayan.proposal.mappers.ProposalMapper;
import com.rayan.proposal.repositories.ProposalRepository;
import com.rayan.proposal.services.WebSocketNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listener for handling completed proposal messages from RabbitMQ.
 */
@Component
public class CompletedProposalListener {

    private static final Logger logger = LoggerFactory.getLogger(CompletedProposalListener.class);

    private final ProposalRepository proposalRepository;
    private final WebSocketNotificationService webSocketNotificationService;

    public CompletedProposalListener(ProposalRepository proposalRepository, WebSocketNotificationService webSocketNotificationService) {
        this.proposalRepository = proposalRepository;
        this.webSocketNotificationService = webSocketNotificationService;
    }

    /**
     * Handles messages from the completed proposal queue.
     *
     * @param payload the proposal message payload
     */
    @RabbitListener(queues = "${rabbitmq.queue.completed.proposal}")
    public void handleCompletedProposal(ProposalMessagePayload payload) {
        logger.info("Received completed proposal payload: {}", payload);

        Proposal proposal = ProposalMapper.mapper.toProposal(payload);
        updateProposal(proposal);

        ProposalResponsePayload proposalResponse = ProposalMapper.mapper.toProposalResponse(proposal);
        webSocketNotificationService.sendNotification(proposalResponse);

        logger.info("Completed proposal processed and notified: {}", proposalResponse);
    }

    private void updateProposal(Proposal proposal) {
        logger.debug("Updating proposal with ID: {}", proposal.getId());
        proposalRepository.updateProposal(proposal.getId(), proposal.getApproved(), proposal.getObservation());
        logger.debug("Proposal updated successfully with ID: {}", proposal.getId());
    }

}
