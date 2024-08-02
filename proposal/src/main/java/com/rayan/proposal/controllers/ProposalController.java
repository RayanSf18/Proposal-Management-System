package com.rayan.proposal.controllers;

import com.rayan.proposal.dtos.ProposalResponsePayload;
import com.rayan.proposal.dtos.RegisterProposalRequestPayload;
import com.rayan.proposal.services.ProposalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/proposals", produces = {"application/json"})
@Tag(name = "Proposal")
public class ProposalController {

    private static final Logger logger = LoggerFactory.getLogger(ProposalController.class);

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Operation(summary = "Register a new proposal", method = "POST", description = "This endpoint register a new proposal with the provided details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proposal created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProposalResponsePayload.class))),
        @ApiResponse(responseCode = "409", description = "Event already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "406", description = "Creation not available", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "422", description = "Invalid date", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProposalResponsePayload> registerProposal(@Valid @RequestBody RegisterProposalRequestPayload payload) {
        logger.info("Registering new proposal with payload: {}", payload);
        ProposalResponsePayload proposalResponse = proposalService.registerProposal(payload);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{proposalId}")
            .buildAndExpand(proposalResponse.id())
            .toUri();
        logger.info("Proposal registered successfully with ID: {}", proposalResponse.id());
        return ResponseEntity.created(location).body(proposalResponse);
    }

    @Operation(summary = "Search all proposals", method = "GET", description = "This endpoint is used to search for all proposals.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proposals found with successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProposalResponsePayload.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProposalResponsePayload>> getProposals() {
        logger.info("Fetching all proposals");
        List<ProposalResponsePayload> proposals = proposalService.getAllProposals();
        logger.info("Fetched {} proposals", proposals.size());
        return ResponseEntity.ok().body(proposals);
    }
}
