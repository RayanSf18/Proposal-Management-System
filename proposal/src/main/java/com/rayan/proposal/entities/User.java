package com.rayan.proposal.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "income", nullable = false)
    private Double income;

    public User(Long id, String name, String surname, String cpf, String phone, Double income) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cpf = cpf;
        this.phone = phone;
        this.income = income;
    }

    public User() {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
}
