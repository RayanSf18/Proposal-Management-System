package com.rayan.credit_analysis.domain;

import java.time.LocalDateTime;

public class Proposal {

    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String cpf;
    private Double income;
    private Double value;
    private int term;
    private Boolean approved;
    private String observation;
    private LocalDateTime occursAt;

    public Proposal(Long id, String name, String surname, String phone, String cpf, Double income, Double value, int term, Boolean approved, String observation, LocalDateTime occursAt) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.cpf = cpf;
        this.income = income;
        this.value = value;
        this.term = term;
        this.approved = approved;
        this.observation = observation;
        this.occursAt = occursAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
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
}
