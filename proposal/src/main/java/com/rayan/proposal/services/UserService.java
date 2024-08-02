package com.rayan.proposal.services;

import com.rayan.proposal.entities.User;
import com.rayan.proposal.exceptions.custom.UserNotFoundException;
import com.rayan.proposal.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByCpf(String cpf) {
        logger.info("Searching for user with CPF: {}", cpf);
        return userRepository.findByCpf(cpf)
            .orElseThrow(() -> {
                logger.error("User not found with CPF: {}", cpf);
                return new UserNotFoundException("User not found with CPF: " + cpf);
            });
    }
}
