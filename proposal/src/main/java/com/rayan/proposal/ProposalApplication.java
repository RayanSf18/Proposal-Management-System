package com.rayan.proposal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@EnableWebSocketMessageBroker
@EnableScheduling
@SpringBootApplication
public class ProposalApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProposalApplication.class, args);
	}
}
