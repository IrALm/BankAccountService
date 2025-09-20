package com.example.BanqueApp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        TypeTransaction typetransaction,
        BigDecimal montant,
        CompteDTO compteSource,
        CompteDTO compteDest
) {}
