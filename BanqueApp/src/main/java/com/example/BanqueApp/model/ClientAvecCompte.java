package com.example.BanqueApp.model;

public record ClientAvecCompte(
        CreateClientDTO client,
        CreateCompteDTO compte
) {}
