package com.rayan.proposal.repositories;

import com.rayan.proposal.entities.Proposal;
import com.rayan.proposal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
          Optional<User> findByCpf(String cpf);
}