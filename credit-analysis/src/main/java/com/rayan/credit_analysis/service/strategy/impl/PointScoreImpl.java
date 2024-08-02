package com.rayan.credit_analysis.service.strategy.impl;

import com.rayan.credit_analysis.constants.Message;
import com.rayan.credit_analysis.domain.Proposal;
import com.rayan.credit_analysis.dto.messaging.ProposalMessagePayload;
import com.rayan.credit_analysis.exceptions.StrategyException;
import com.rayan.credit_analysis.service.strategy.PointCalculation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(value = 2)
@Component
public class PointScoreImpl implements PointCalculation {


    @Override
    public int compute(Proposal proposal) {

        int score = score();

        if (score < 200) throw new StrategyException(String.format(Message.LOW_SERASA_SCORE, proposal.getName()));

        if (score <= 400) return 150;

        if (score <= 600) return 180;

        return 220;
    }

    private int score() {
        return new Random().nextInt(0, 1000);
    }
}
