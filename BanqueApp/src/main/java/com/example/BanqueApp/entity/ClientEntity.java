package com.example.BanqueApp.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity //entité JPA
@Data // importer avec Lombock : pour les méthode toString , equals , getters et setters , etc
@NoArgsConstructor // Constructeur sans atribut en paramètre
@AllArgsConstructor // constructeur avec tous les attributs
@Builder // Lombok: pour la méthode build()
@Table( name = "clients")

public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message="Le prénom est obligatoire")
    private String prenom;

    @Email
    @NotBlank(message ="l'Email est obligatoire")
    private String email;
    @NotBlank(message=" Le numéro de télephone est obligatoire ")
    private String telephone;
    @NotBlank(message=" L'adresse est obligatoire ")
    private String adresse;
    @NotNull
    @Past
    private LocalDate date_naissance;

    @CreationTimestamp // indique la date doit être celle au moment de la création de l'entité dans la base des données
    private java.time.LocalTime dateCreation;


    /* Rélation 1 client Plusieurs comptes*/

    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL , orphanRemoval = true)
    @Builder .Default // pour initialiser la liste avec le même builder
    private List<CompteEntity> sesComptes = new ArrayList<>();
}
