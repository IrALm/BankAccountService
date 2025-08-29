package com.example.BanqueApp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        Long id,
        TypeTransaction typetransaction,
        BigDecimal montant,
        LocalDateTime dateTansaction,
        CompteDTO compteSource,
        CompteDTO compteDest
) {}
