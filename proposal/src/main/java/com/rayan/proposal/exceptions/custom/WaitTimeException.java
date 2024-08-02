package com.rayan.proposal.exceptions.custom;

import com.rayan.ms_proposal.exceptions.handler.ProposalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WaitTimeException extends ProposalException {

          private final String detail;

          public WaitTimeException(String detail) {
                    this.detail = detail;
          }

          @Override
          public ProblemDetail toProblemDetail() {
                    var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE);
                    problemDetail.setTitle("Creation not available");
                    problemDetail.setDetail(detail);
                    return problemDetail;
          }

}
