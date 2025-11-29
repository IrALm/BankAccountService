package com.example.BanqueApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BankAdvisor extends User {

    private String nom;

    private String prenom;

    private String telephone;

    private String adresse;

    private LocalDate dateNaissance;

    @Column(unique = true)
    private String matricule;

    /* Relation 1 conseiller -> plusieurs clients */
    @OneToMany(mappedBy = "advisor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Customer> clients = new ArrayList<>();
}
