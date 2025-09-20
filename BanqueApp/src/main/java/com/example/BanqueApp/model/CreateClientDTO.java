package com.example.BanqueApp.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

/* En écriture seule */
public record CreateClientDTO(
        @NotBlank(message=" Le nom est obligatoire ") String nom,
        @NotBlank(message=" Le prénom est obligatoire ")String prenom,
        @Email(message=" l'email doit être valide")String email,
        @NotBlank(message=" Le numero de télephone  est obligatoire ")String telephone,
        @NotBlank(message=" L'adresse est obligatoire ")String adresse,
        @NotNull @Past LocalDate date_naissance,
        @NotEmpty(message = "Un client doit avoir au moins un compte qui lui est associé") @Valid List<CreateCompteDTO> sesComptes
) {}
