package com.rayan.proposal.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload for response a proposal")
public record ProposalResponsePayload(

    @Schema(description = "Proposal ID", example = "5")
    Long id,

    @Schema(description = "Proposal user name", example = "Joe")
    String name,

    @Schema(description = "Last name of the proposal user", example = "Doe")
    String surname,

    @Schema(description = "Telephone number of the proposal user. ()", example = "+55 (99) 9 9999-9999")
    String phone,

    @Schema(description = "CPF of the proposal user", example = "999.999.999-99")
    String cpf,

    @Schema(description = "Income of the proposal user", example = "2500.00")
    Double income,

    @Schema(description = "Requested value of the proposal", example = "25000.00")
    String value,

    @Schema(description = "Proposal payment deadline", example = "36")
    int term,

    @Schema(description = "Given whether the proposal was approved.", example = "True")
    Boolean approved,

    @Schema(description = "Information about approval of the proposal", example = "Proposal denied due to something.")
    String observation,

    @Schema(description = "Date of making the proposal", example = "2024-12-25T13:30:00")
    String occursAt

) {
}
