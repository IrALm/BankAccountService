package com.example.BanqueApp.model.createDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendMessageDTO(
        @NotNull Long conversationId,
        @NotBlank String contenu,
        @NotNull Long expediteurId,
        @NotNull Long destinataireId
) {}
