package com.example.BanqueApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity //entité JPA
@Data // importer avec Lombock : pour les méthode toString , equals , getters et setters , etc
@NoArgsConstructor // Constructeur sans atribut en paramètre
@AllArgsConstructor // constructeur avec tous les attributs
@Builder // Lombok: pour la méthode build()
@Table( name = "personnes")
public class Person {

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
}
