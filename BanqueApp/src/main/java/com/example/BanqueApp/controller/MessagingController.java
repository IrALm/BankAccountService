package com.example.BanqueApp.controller;

import com.example.BanqueApp.model.createDTO.SendMessageDTO;
import com.example.BanqueApp.model.readDTO.ConversationDTO;
import com.example.BanqueApp.model.readDTO.MessageDTO;
import com.example.BanqueApp.service.ConversationService;
import com.example.BanqueApp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messaging")
@RequiredArgsConstructor
public class MessagingController {

    private final MessageService messageService;
    private final ConversationService conversationService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/conversations/user/{userId}")
    public ResponseEntity<List<ConversationDTO>> getConversationsUtilisateur(@PathVariable Long userId) {
        return ResponseEntity.ok(conversationService.getConversationsUtilisateur(userId));
    }

    @GetMapping("/conversation/{conversationId}/messages")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long conversationId) {
        return ResponseEntity.ok(messageService.getMessagesConversation(conversationId));
    }

    @GetMapping("/conversation/{conversationId}/user/{userId}")
    public ResponseEntity<ConversationDTO> getConversation(@PathVariable Long conversationId, @PathVariable Long userId) {
        return ResponseEntity.ok(conversationService.getConversationDTO(conversationId, userId));
    }

    @PostMapping("/conversation/create")
    public ResponseEntity<ConversationDTO> createConversation(@RequestParam Long clientId, @RequestParam Long conseillerId) {
        var conversation = conversationService.getOrCreateConversation(clientId, conseillerId);
        return ResponseEntity.ok(conversationService.getConversationDTO(conversation.getId(), clientId));
    }

    @PostMapping("/message/mark-read/{messageId}")
    public ResponseEntity<Void> marquerCommeLu(@PathVariable Long messageId) {
        messageService.marquerCommeLu(messageId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/conversation/{conversationId}/mark-all-read/{userId}")
    public ResponseEntity<Void> marquerTousCommeLus(@PathVariable Long conversationId, @PathVariable Long userId) {
        messageService.marquerTousMessagesLus(conversationId, userId);
        return ResponseEntity.ok().build();
    }

    // WebSocket endpoint pour envoyer des messages en temps réel
    @MessageMapping("/chat")
    public void sendMessage(@Payload SendMessageDTO messageDTO) {
        MessageDTO sentMessage = messageService.envoyerMessage(messageDTO);
        
        // Envoyer le message au destinataire via WebSocket
        messagingTemplate.convertAndSend(
                "/queue/messages/" + messageDTO.destinataireId(), 
                sentMessage
        );
        
        // Confirmer l'envoi à l'expéditeur
        messagingTemplate.convertAndSend(
                "/queue/messages/" + messageDTO.expediteurId(), 
                sentMessage
        );
    }
}
