package com.rayan.proposal.services;

import com.rayan.proposal.dtos.ProposalResponsePayload;
import com.rayan.proposal.dtos.RegisterProposalRequestPayload;
import com.rayan.proposal.entities.Proposal;
import com.rayan.proposal.entities.User;
import com.rayan.proposal.exceptions.custom.InvalidDateException;
import com.rayan.proposal.exceptions.custom.UserNotFoundException;
import com.rayan.proposal.exceptions.custom.WaitTimeException;
import com.rayan.proposal.mappers.ProposalMapper;
import com.rayan.proposal.repositories.ProposalRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProposalService {

    private static final Logger logger = LoggerFactory.getLogger(ProposalService.class);

    private final ProposalRepository proposalRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final String exchange;

    public ProposalService(ProposalRepository proposalRepository, UserService userService, NotificationService notificationService, @Value(value = "${rabbitmq.pending.proposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.userService = userService;
        this.notificationService = notificationService;
        this.exchange = exchange;
    }

    @Transactional
    public ProposalResponsePayload registerProposal(RegisterProposalRequestPayload requestPayload) {
        Proposal proposal = ProposalMapper.mapper.toProposal(requestPayload);
        validateProposalDate(proposal);

        try {
            User user = userService.findUserByCpf(proposal.getUser().getCpf());
            if (!canCreateNewProposal(user.getId(), proposal)) {
                throw new WaitTimeException("You need to wait 3 months to make a new proposal.");
            }
            proposal.setUser(user);
            proposalRepository.save(proposal);

            int priority = proposal.getUser().getIncome() > 10000 ? 10 : 5;
            
            MessagePostProcessor messagePostProcessor = message -> {
                message.getMessageProperties().setPriority(priority);
                return message;
            };

            sendNotification(proposal, messagePostProcessor);
            return ProposalMapper.mapper.toProposalResponse(proposal);

        } catch (UserNotFoundException e) {
            proposalRepository.save(proposal);
            int priority = proposal.getUser().getIncome() > 10000 ? 10 : 5;

            MessagePostProcessor messagePostProcessor = message -> {
                message.getMessageProperties().setPriority(priority);
                return message;
            };
            sendNotification(proposal, messagePostProcessor);
            return ProposalMapper.mapper.toProposalResponse(proposal);
        }


    }

    public List<ProposalResponsePayload> getAllProposals() {
        return ProposalMapper.mapper.toProposalResponseList(proposalRepository.findAll());
    }

    private void validateProposalDate(Proposal proposal) {
        if (proposal.getOccursAt().isBefore(LocalDateTime.now())) {
            throw new InvalidDateException("The date of the proposal must be at least current.");
        }
    }

    private boolean canCreateNewProposal(Long userId, Proposal proposal) {
        Optional<Proposal> lastProposalOpt = proposalRepository.findFirstByUserIdOrderByOccursAtDesc(userId);
        if (lastProposalOpt.isPresent()) {
            Proposal lastProposal = lastProposalOpt.get();
            LocalDateTime allowedDateForNewProposal = lastProposal.getOccursAt().plusMonths(3);
            return !proposal.getOccursAt().isBefore(allowedDateForNewProposal);
        }
        return true;
    }

    private void sendNotification(Proposal proposal, MessagePostProcessor messagePostProcessor) {
        try {
            notificationService.sendNotification(ProposalMapper.mapper.toProposalMessage(proposal), exchange, messagePostProcessor);
            logger.info("Notification sent for proposal ID {}", proposal.getId());
        } catch (RuntimeException exception) {
            proposal.setIntegrated(false);
            proposalRepository.save(proposal);
            logger.error("Failed to send notification for proposal ID {}: {}", proposal.getId(), exception.getMessage());
        }
    }
}
