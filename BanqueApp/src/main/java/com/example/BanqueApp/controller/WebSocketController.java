package com.example.BanqueApp.controller;

import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.entity.User;
import com.example.BanqueApp.model.createDTO.ChatMessageDTO;
import com.example.BanqueApp.model.createDTO.SendMessageDTO;
import com.example.BanqueApp.model.readDTO.MessageDTO;
import com.example.BanqueApp.repository.UserRepository;
import com.example.BanqueApp.service.ConversationService;
import com.example.BanqueApp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final MessageService messageService;
    private final ConversationService conversationService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        // Récupérer la conversation
        Conversation conversation = conversationService.getConversationById(chatMessageDTO.getConversationId());
        
        // Identifier le destinataire
        Long destinataireId;
        if (conversation.getClient().getId().equals(chatMessageDTO.getSenderId())) {
            destinataireId = conversation.getConseiller().getId();
        } else {
            destinataireId = conversation.getClient().getId();
        }

        // Créer le DTO pour le service existant
        SendMessageDTO sendMessageDTO = new SendMessageDTO(
                chatMessageDTO.getConversationId(),
                chatMessageDTO.getContent(),
                chatMessageDTO.getSenderId(),
                destinataireId
        );

        // Sauvegarder le message via le service existant
        MessageDTO savedMessage = messageService.envoyerMessage(sendMessageDTO);

        // Préparer la réponse pour le WebSocket
        ChatMessageDTO responseDTO = new ChatMessageDTO();
        responseDTO.setMessageId(savedMessage.id());
        responseDTO.setConversationId(conversation.getId());
        responseDTO.setSenderId(chatMessageDTO.getSenderId());
        responseDTO.setSenderName(chatMessageDTO.getSenderName());
        responseDTO.setContent(savedMessage.contenu());
        responseDTO.setTimestamp(savedMessage.dateEnvoi().format(formatter));

        // Envoyer à la topic de la conversation
        messagingTemplate.convertAndSend(
                "/topic/conversation/" + conversation.getId(),
                responseDTO
        );

        // Envoyer une notification au destinataire
        long unreadCount = messageService.compterMessagesNonLus(conversation.getId(), destinataireId);
        long totalUnread = messageService.compterTousMessagesNonLus(destinataireId);
        
        var notification = new java.util.HashMap<String, Object>();
        notification.put("conversationId", conversation.getId());
        notification.put("unreadCount", unreadCount);
        notification.put("totalUnread", totalUnread);
        
        messagingTemplate.convertAndSend(
                "/user/" + destinataireId + "/queue/notifications",
                notification
        );
    }
}
