package com.example.BanqueApp.service;

import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.entity.Message;
import com.example.BanqueApp.model.createDTO.SendMessageDTO;
import com.example.BanqueApp.model.readDTO.MessageDTO;

import java.util.List;

public interface MessageService {

    MessageDTO envoyerMessage(SendMessageDTO messageDTO);

    List<MessageDTO> getMessagesConversation(Long conversationId);

    void marquerCommeLu(Long messageId);

    void marquerTousMessagesLus(Long conversationId, Long userId);

    long compterMessagesNonLus(Long conversationId, Long userId);
}
