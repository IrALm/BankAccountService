package com.example.BanqueApp.model.createDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreatePersonDTO(
        @NotBlank(message = "Le nom est obligatoire")
        String nom,
        @NotBlank(message = "Le prénom est obligatoire")
        String prenom,
        @NotBlank(message = "Email obligatoire")
        @Email(message = "Email invalide")
        String email,
        @NotBlank(message=" Le numéro de télephone est obligatoire ")
        String telephone,
        @NotBlank(message=" L'adresse est obligatoire ")
        String adresse,
        @NotNull(message = " Date de naissance Obligatoire")
        @Past LocalDate date_naissance
) {
}
