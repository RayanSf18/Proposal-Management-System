package com.rayan.credit_analysis.service.strategy.impl;

import com.rayan.credit_analysis.domain.Proposal;
import com.rayan.credit_analysis.dto.messaging.ProposalMessagePayload;
import com.rayan.credit_analysis.service.strategy.PointCalculation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 3)
@Component
public class IncomeBiggerRequestedAmount implements PointCalculation {

    @Override
    public int compute(Proposal proposal) {
        return isIncomeBiggerRequestAmount(proposal) ? 100 : 0;
    }

    private boolean isIncomeBiggerRequestAmount(Proposal proposal) {
        return proposal.getIncome() > proposal.getValue();
    }
}
