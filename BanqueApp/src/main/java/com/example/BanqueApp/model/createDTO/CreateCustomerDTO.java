package com.example.BanqueApp.model.createDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

/* En écriture seule */
public record CreateCustomerDTO(
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
        String email,
        
        @NotEmpty(message = "Un client doit avoir au moins un compte qui lui est associé")
        @Valid List<CreateCountDTO> sesComptesDTO
) {}
