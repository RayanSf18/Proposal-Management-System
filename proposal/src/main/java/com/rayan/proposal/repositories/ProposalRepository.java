package com.rayan.proposal.repositories;

import com.rayan.proposal.entities.Proposal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    /**
     * Finds the most recent proposal for a user by user ID.
     *
     * @param userId the user ID
     * @return an Optional containing the most recent proposal if found
     */
    Optional<Proposal> findFirstByUserIdOrderByOccursAtDesc(Long userId);

    /**
     * Finds all proposals that have not been integrated.
     *
     * @return a list of non-integrated proposals
     */
    List<Proposal> findAllByIntegratedIsFalse();

    /**
     * Updates the approval status and observation of a proposal by ID.
     *
     * @param id the proposal ID
     * @param approved the approval status
     * @param observation the observation text
     */
    @Transactional
    @Modifying
    @Query("UPDATE Proposal p SET p.approved = :approved, p.observation = :observation WHERE p.id = :id")
    void updateProposal(Long id, Boolean approved, String observation);
}
