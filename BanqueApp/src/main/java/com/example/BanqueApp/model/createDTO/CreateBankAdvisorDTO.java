package com.example.BanqueApp.model.createDTO;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreateBankAdvisorDTO(
        @NotBlank(message = "Le nom est obligatoire")
        String nom,
        
        @NotBlank(message = "Le prénom est obligatoire")
        String prenom,
        
        @NotBlank(message = "Le numéro de télephone est obligatoire")
        String telephone,
        
        @NotBlank(message = "L'adresse est obligatoire")
        String adresse,
        
        @NotNull @Past
        LocalDate dateNaissance,
        
        @Email @NotBlank
        String email
) {}
