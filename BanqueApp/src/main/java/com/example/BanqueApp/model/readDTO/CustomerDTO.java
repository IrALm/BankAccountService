package com.example.BanqueApp.model.readDTO;

import java.time.LocalDate;
import java.util.List;


public record CustomerDTO(
        Long id,
        String nom,
        String prenom,
        String telephone,
        String adresse,
        LocalDate dateNaissance,
        String email,
        List<CountDTO> sesComptesDTO
) {}
