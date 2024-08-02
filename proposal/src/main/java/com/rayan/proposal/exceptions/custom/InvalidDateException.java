package com.rayan.proposal.exceptions.custom;

import com.rayan.ms_proposal.exceptions.handler.ProposalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvalidDateException extends ProposalException {

          private final String detail;

          public InvalidDateException(String detail) {
                    this.detail = detail;
          }

          @Override
          public ProblemDetail toProblemDetail() {
                    var problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
                    problemDetail.setTitle("Invalid Date");
                    problemDetail.setDetail(detail);
                    return problemDetail;
          }

}
