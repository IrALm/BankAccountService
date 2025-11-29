package com.example.BanqueApp.model.readDTO;

import java.time.LocalDateTime;

public record MessageDTO(
        Long id,
        String contenu,
        LocalDateTime dateEnvoi,
        boolean lu,
        Long expediteurId,
        String expediteurNom,
        Long destinataireId,
        String destinataireNom,
        Long conversationId
) {}
