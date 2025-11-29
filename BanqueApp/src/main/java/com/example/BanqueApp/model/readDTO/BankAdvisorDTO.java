package com.example.BanqueApp.model.readDTO;

import java.time.LocalDate;

public record BankAdvisorDTO(
        Long id,
        String nom,
        String prenom,
        String telephone,
        String adresse,
        LocalDate dateNaissance,
        String email,
        String matricule
) {}
