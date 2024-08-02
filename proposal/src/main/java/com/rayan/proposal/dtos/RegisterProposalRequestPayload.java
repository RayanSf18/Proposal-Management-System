package com.rayan.proposal.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Payload for creating a proposal")
public record RegisterProposalRequestPayload(

    @Schema(description = "Proposal user name", example = "Joe")
    @NotBlank
    String name,

    @Schema(description = "Last name of the proposal user", example = "Doe")
    @NotBlank
    String surname,

    @Schema(description = "Telephone number of the proposal user. ()", example = "+55 (99) 9 9999-9999")
    @NotBlank
    String phone,

    @Schema(description = "CPF of the proposal user", example = "999.999.999-99")
    @NotBlank
    String cpf,

    @Schema(description = "Income of the proposal user", example = "2500.00")
    @NotNull
    Double income,

    @Schema(description = "Requested value of the proposal", example = "25000.00")
    @NotNull
    Double value,

    @Schema(description = "Proposal payment deadline", example = "36")
    @Min(5)
    int term,

    @Schema(description = "Date of making the proposal", example = "2024-12-25T13:30:00")
    @NotBlank
    String occursAt
) {
}
