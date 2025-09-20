package com.example.BanqueApp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/* En lecture seule */
public record ClientDTO(
        String nom ,
        String prenom ,
        String email ,
        String telephone ,
        String adresse,
        LocalDate date_naissance,
        List<CompteDTO> sesComptes
) {}
