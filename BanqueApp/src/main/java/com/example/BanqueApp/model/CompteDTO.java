package com.example.BanqueApp.model;

import com.example.BanqueApp.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CompteDTO(
        Long id,
        String numeroCompte,
        TypeCompte typeCompte,
        BigDecimal solde,
        ClientDTO client,
        LocalDateTime dateCreation,
        List<TransactionDTO> transactionSource,
        List<TransactionDTO> transactionDest
) {}
