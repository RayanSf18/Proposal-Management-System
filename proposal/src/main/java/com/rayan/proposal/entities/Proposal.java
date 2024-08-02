package com.rayan.proposal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_proposals")
public class Proposal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "term", nullable = false)
    private int term;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "integrated", nullable = false)
    private boolean integrated;

    @Column(name = "observation")
    private String observation;

    @Column(name = "occurs_at", nullable = false, updatable = false)
    private LocalDateTime occursAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Proposal(Long id, Double value, int term, Boolean approved, boolean integrated, String observation, LocalDateTime occursAt, User user) {
        this.id = id;
        this.value = value;
        this.term = term;
        this.approved = approved;
        this.integrated = integrated;
        this.observation = observation;
        this.occursAt = occursAt;
        this.user = user;
    }

    public Proposal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public boolean isIntegrated() {
        return integrated;
    }

    public void setIntegrated(boolean integrated) {
        this.integrated = integrated;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDateTime getOccursAt() {
        return occursAt;
    }

    public void setOccursAt(LocalDateTime occursAt) {
        this.occursAt = occursAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
