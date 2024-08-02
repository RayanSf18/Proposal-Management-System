package com.rayan.proposal.scheduler;

import com.rayan.proposal.entities.Proposal;
import com.rayan.proposal.mappers.ProposalMapper;
import com.rayan.proposal.repositories.ProposalRepository;
import com.rayan.proposal.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * Scheduler for handling proposals that have not been integrated.
 */
@Component
public class ProposalWithoutIntegrationScheduler {

    private final Logger logger = LoggerFactory.getLogger(ProposalWithoutIntegrationScheduler.class);

    private final ProposalRepository proposalRepository;
    private final NotificationService notificationService;
    private final String pendingProposalExchange;

    public ProposalWithoutIntegrationScheduler(ProposalRepository proposalRepository, NotificationService notificationService, @Value("${rabbitmq.pending.proposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.notificationService = notificationService;
        this.pendingProposalExchange = exchange;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void processProposalsWithoutIntegration() {
        proposalRepository.findAllByIntegratedIsFalse().forEach(this::processProposal);
    }

    private void processProposal(Proposal proposal) {
        try {
            int priority = proposal.getUser().getIncome() > 10000 ? 10 : 5;

            MessagePostProcessor messagePostProcessor = message -> {
                message.getMessageProperties().setPriority(priority);
                return message;
            };
            notificationService.sendNotification(ProposalMapper.mapper.toProposalMessage(proposal), pendingProposalExchange, messagePostProcessor);
            updateProposalIntegrationStatus(proposal);
        } catch (RuntimeException exception) {
            logger.error("Error processing proposal with ID {}: {}", proposal.getId(), exception.getMessage());
        }
    }

    private void updateProposalIntegrationStatus(Proposal proposal) {
        proposal.setIntegrated(true);
        proposalRepository.save(proposal);
        logger.info("Proposal with ID {} marked as integrated", proposal.getId());
    }

}
