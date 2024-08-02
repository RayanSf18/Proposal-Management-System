package com.rayan.credit_analysis.service.strategy.impl;

import com.rayan.credit_analysis.domain.Proposal;
import com.rayan.credit_analysis.dto.messaging.ProposalMessagePayload;
import com.rayan.credit_analysis.service.strategy.PointCalculation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 4)
@Component
public class PaymentTermLessThanTenYears implements PointCalculation {

    @Override
    public int compute(Proposal payload) {
        return payload.getTerm() < 120 ? 80 : 0;
    }

}
