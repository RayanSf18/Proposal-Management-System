package com.rayan.credit_analysis.service.strategy.impl;

import com.rayan.credit_analysis.domain.Proposal;
import com.rayan.credit_analysis.dto.messaging.ProposalMessagePayload;
import com.rayan.credit_analysis.service.strategy.PointCalculation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(value = 5)
@Component
public class OtherLoansImpl implements PointCalculation {

    @Override
    public int compute(Proposal payload) {
        return hasOtherLoansInProgress() ? 0 : 80;
    }

    public boolean hasOtherLoansInProgress() {
        return new Random().nextBoolean();
    }
}
