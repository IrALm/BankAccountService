package com.example.BanqueApp.model.readDTO;

import com.example.BanqueApp.entity.TypeTransaction;

import java.math.BigDecimal;

public record TransactionDTO(
        TypeTransaction typetransaction,
        BigDecimal montant,
        CountDTO compteSource,
        CountDTO compteDest
) {}
