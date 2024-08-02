package com.rayan.proposal.mappers;

import com.rayan.proposal.dtos.ProposalMessagePayload;
import com.rayan.proposal.dtos.ProposalResponsePayload;
import com.rayan.proposal.dtos.RegisterProposalRequestPayload;
import com.rayan.proposal.entities.Proposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.NumberFormat;
import java.util.List;

@Mapper
public interface ProposalMapper {

    ProposalMapper mapper = Mappers.getMapper(ProposalMapper.class);

    @Mapping(target = "user.name", source = "name")
    @Mapping(target = "user.surname", source = "surname")
    @Mapping(target = "user.cpf", source = "cpf")
    @Mapping(target = "user.phone", source = "phone")
    @Mapping(target = "user.income", source = "income")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approved", ignore = true)
    @Mapping(target = "integrated", constant = "true")
    @Mapping(target = "observation", ignore = true)
    Proposal toProposal(RegisterProposalRequestPayload proposalRequest);

    @Mapping(target = "user.name", source = "name")
    @Mapping(target = "user.surname", source = "surname")
    @Mapping(target = "user.cpf", source = "cpf")
    @Mapping(target = "user.phone", source = "phone")
    @Mapping(target = "user.income", source = "income")
    @Mapping(target = "integrated", constant = "true")
    Proposal toProposal(ProposalMessagePayload proposalMessage);


    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "surname", source = "user.surname")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "cpf", source = "user.cpf")
    @Mapping(target = "income", source = "user.income")
    @Mapping(target = "occursAt", source = "occursAt")
    @Mapping(target = "value", expression = "java(setValue(proposal))")
    ProposalResponsePayload toProposalResponse(Proposal proposal);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "surname", source = "user.surname")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "cpf", source = "user.cpf")
    @Mapping(target = "income", source = "user.income")
    @Mapping(target = "occursAt", source = "occursAt")
    ProposalMessagePayload toProposalMessage(Proposal proposal);

    List<ProposalResponsePayload> toProposalResponseList(Iterable<Proposal> proposals);

    default String setValue(Proposal proposal) {
        return NumberFormat.getCurrencyInstance().format(proposal.getValue());
    }
}
