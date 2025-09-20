package com.example.BanqueApp.model;

import com.example.BanqueApp.entity.ClientEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateCompteDTO(
       @NotBlank(message = " le numéro de compte ne doit pas être vide") String numeroCompte,
       @NotBlank(message = " le type du compte ne doit pas être vide") @Enumerated(EnumType.STRING) TypeCompte typeCompte,
       @NotNull BigDecimal solde,
       @NotBlank( message = " Un compte doit avoir au moins un client qui lui est associé") String emailClient
) {}
