package com.rayan.credit_analysis.dto.messaging;

import com.rayan.credit_analysis.domain.Proposal;

import java.time.LocalDateTime;

public record ProposalMessagePayload(

    Long id,
    String name,
    String surname,
    String phone,
    String cpf,
    Double income,
    Double value,
    int term,
    Boolean approved,
    String observation,
    String occursAt

) {

    public static ProposalMessagePayload toProposalMessage(Proposal proposal) {
        return new ProposalMessagePayload(
            proposal.getId(),
            proposal.getName(),
            proposal.getSurname(),
            proposal.getPhone(),
            proposal.getCpf(),
            proposal.getIncome(),
            proposal.getValue(),
            proposal.getTerm(),
            proposal.getApproved(),
            proposal.getObservation(),
            proposal.getOccursAt().toString()
        );
    }

    public static Proposal toProposal(ProposalMessagePayload payload) {
        return new Proposal(
            payload.id(),
            payload.name(),
            payload.surname(),
            payload.phone(),
            payload.cpf(),
            payload.income(),
            payload.value(),
            payload.term(),
            payload.approved(),
            payload.observation(),
            LocalDateTime.parse(payload.occursAt())
        );
    }
}
