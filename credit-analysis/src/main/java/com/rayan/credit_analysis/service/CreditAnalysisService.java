package com.rayan.credit_analysis.service;

import com.rayan.credit_analysis.domain.Proposal;
import com.rayan.credit_analysis.dto.messaging.ProposalMessagePayload;
import com.rayan.credit_analysis.exceptions.StrategyException;
import com.rayan.credit_analysis.service.strategy.PointCalculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditAnalysisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditAnalysisService.class);

    private final List<PointCalculation> pointCalculationList;

    private final NotificationService notificationService;

    private final String completedProposalExchange;

    public CreditAnalysisService(List<PointCalculation> pointCalculationList, NotificationService notificationService, @Value("${rabbitmq.completed.proposal.exchange}") String completedProposalExchange) {
        this.pointCalculationList = pointCalculationList;
        this.notificationService = notificationService;
        this.completedProposalExchange = completedProposalExchange;
    }

    public void analyze(Proposal proposal) {
        LOGGER.debug("Analyzing proposal ID: {}", proposal.getId());

        try {
            int totalPoints = pointCalculationList.stream()
                .mapToInt(strategy -> strategy.compute(proposal))
                .sum();
            boolean isApproved = totalPoints > 350;

            if (!isApproved) {
                proposal.setObservation("After analysis, we verify that your data or history did not meet the criteria necessary for approval.");
            }

            proposal.setApproved(isApproved);

            LOGGER.info("Proposal ID: {} analysis result: {}", proposal.getId(), isApproved ? "Approved" : "Not Approved");

        } catch (StrategyException e) {
            LOGGER.error("StrategyException while analyzing proposal ID: {}", proposal.getId(), e);
            proposal.setApproved(false);
            proposal.setObservation(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Unexpected error while analyzing proposal ID: {}", proposal.getId(), e);
            proposal.setApproved(false);
            proposal.setObservation("An unexpected error occurred during analysis.");
        }

        try {
            notificationService.sendNotification(completedProposalExchange, ProposalMessagePayload.toProposalMessage(proposal));
            LOGGER.info("Notification sent for proposal ID: {}", proposal.getId());
        } catch (Exception e) {
            LOGGER.error("Failed to send notification for proposal ID: {}", proposal.getId(), e);
        }
    }
}
