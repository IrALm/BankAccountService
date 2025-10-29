package com.example.BanqueApp.model.readDTO;

import java.time.LocalDate;

public record PersonDTO(
        String nom,
        String prenom,
        String email,
        String telephone,
        String adresse,
        LocalDate date_naissance
) {
}
