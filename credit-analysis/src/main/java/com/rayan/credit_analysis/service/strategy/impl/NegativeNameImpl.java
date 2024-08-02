package com.rayan.credit_analysis.service.strategy.impl;

import com.rayan.credit_analysis.constants.Message;
import com.rayan.credit_analysis.domain.Proposal;
import com.rayan.credit_analysis.dto.messaging.ProposalMessagePayload;
import com.rayan.credit_analysis.exceptions.StrategyException;
import com.rayan.credit_analysis.service.strategy.PointCalculation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(value = 1)
@Component
public class NegativeNameImpl implements PointCalculation {

    @Override
    public int compute(Proposal proposal) {
        if (isNegativeName()) {
            throw new StrategyException(String.format(Message.NEGATIVE_CUSTOMER, proposal.getName()));
        }
        return 100;
    }

    private boolean isNegativeName() {
        return new Random().nextBoolean();
    }
}
