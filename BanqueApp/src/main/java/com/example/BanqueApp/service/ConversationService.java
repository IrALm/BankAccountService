package com.example.BanqueApp.service;

import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.model.readDTO.ConversationDTO;

import java.util.List;

public interface ConversationService {

    Conversation getOrCreateConversation(Long clientId, Long conseillerId);

    ConversationDTO getConversationDTO(Long conversationId, Long userId);

    List<ConversationDTO> getConversationsUtilisateur(Long userId);

    Conversation getConversationById(Long conversationId);
}
