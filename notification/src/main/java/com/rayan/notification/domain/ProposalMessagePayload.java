package com.rayan.notification.domain;

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
}
