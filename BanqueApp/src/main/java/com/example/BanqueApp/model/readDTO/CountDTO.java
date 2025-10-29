package com.example.BanqueApp.model.readDTO;

import com.example.BanqueApp.entity.TypeCompte;

import java.math.BigDecimal;

/* En lecture seule */
public record CountDTO(
        String numeroCompte,
        TypeCompte typeCompte,
        BigDecimal solde
) {}
