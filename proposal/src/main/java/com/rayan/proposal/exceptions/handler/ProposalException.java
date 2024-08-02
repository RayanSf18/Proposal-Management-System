package com.rayan.ms_proposal.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ProposalException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Proposal internal server error.");
        return problemDetail;
    }
}
