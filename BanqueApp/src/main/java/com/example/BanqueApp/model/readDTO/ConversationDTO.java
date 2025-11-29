package com.example.BanqueApp.model.readDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ConversationDTO(
        Long id,
        Long clientId,
        String clientNom,
        Long conseillerId,
        String conseillerNom,
        LocalDateTime dateCreation,
        LocalDateTime dernierMessageDate,
        long messagesNonLus
) {}
