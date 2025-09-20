package com.example.BanqueApp.model;

import com.example.BanqueApp.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/* En lecture seule */
public record CompteDTO(
        String numeroCompte,
        TypeCompte typeCompte,
        BigDecimal solde
) {}
