package com.example.BanqueApp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ClientDTO(
        Long id,
        String nom ,
        String prenom ,
        String email ,
        String telephone ,
        String adresse,
        LocalDate date_naissance,
        LocalDateTime dateCreation,
        List<CompteDTO> sesComptes
) {}
