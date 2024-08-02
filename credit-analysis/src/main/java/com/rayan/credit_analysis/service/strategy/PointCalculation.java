package com.rayan.credit_analysis.service.strategy;

import com.rayan.credit_analysis.domain.Proposal;

public interface PointCalculation {

    int compute(Proposal payload);

}
