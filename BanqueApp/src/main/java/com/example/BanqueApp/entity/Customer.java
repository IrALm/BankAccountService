package com.example.BanqueApp.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity //entité JPA
@Data // importer avec Lombock : pour les méthode toString , equals , getters et setters , etc
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "clients")
@Schema( description = " Répresente un client dans le système ")
@EqualsAndHashCode(callSuper = true) // lombok : génère equals() et hashCode()

public class Customer extends User {

    private String nom;

    private String prenom;

    private String telephone;

    private String adresse;

    private LocalDate dateNaissance;

    @ManyToOne
    @JoinColumn(name = "advisor_id")
    private BankAdvisor advisor;

    /* Rélation 1 client Plusieurs comptes*/

    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL , orphanRemoval = true)
    @Builder.Default // pour initialiser la liste avec le même builder
    private List<Count> sesComptes = new ArrayList<>();

}
