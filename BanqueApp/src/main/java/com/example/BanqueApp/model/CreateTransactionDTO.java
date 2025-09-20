package com.example.BanqueApp.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionDTO(
        @NotBlank @Enumerated(EnumType.STRING) TypeTransaction typetransaction,
        @NotNull BigDecimal montant,
        @NotBlank(message="Une transaction doit avoir un compte source")  String numeroCompteSource,
        @NotBlank(message="Une transaction doit avoir un compte destinataire ")  String numeroCompteDest
) {}
