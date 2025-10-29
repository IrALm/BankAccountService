package com.example.BanqueApp.model.createDTO;

import com.example.BanqueApp.entity.TypeCompte;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateCountDTO(
       @NotBlank(message = " le numéro de compte ne doit pas être vide")
       String numeroCompte,
       @NotBlank(message = " le type du compte ne doit pas être vide")
       @Enumerated(EnumType.STRING) TypeCompte typeCompte,
       @NotNull BigDecimal solde
) {}
