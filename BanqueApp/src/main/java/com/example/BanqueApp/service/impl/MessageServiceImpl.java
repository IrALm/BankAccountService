package com.example.BanqueApp.service.impl;

import com.example.BanqueApp.Mapper.MessageMapper;
import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.entity.Message;
import com.example.BanqueApp.entity.User;
import com.example.BanqueApp.model.createDTO.SendMessageDTO;
import com.example.BanqueApp.model.readDTO.MessageDTO;
import com.example.BanqueApp.repository.MessageRepository;
import com.example.BanqueApp.repository.UserRepository;
import com.example.BanqueApp.service.ConversationService;
import com.example.BanqueApp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConversationService conversationService;

    @Override
    @Transactional
    public MessageDTO envoyerMessage(SendMessageDTO messageDTO) {
        User expediteur = userRepository.findById(messageDTO.expediteurId())
                .orElseThrow(() -> new RuntimeException("Expéditeur non trouvé"));
        User destinataire = userRepository.findById(messageDTO.destinataireId())
                .orElseThrow(() -> new RuntimeException("Destinataire non trouvé"));
        
        Conversation conversation = conversationService.getConversationById(messageDTO.conversationId());

        Message message = Message.builder()
                .contenu(messageDTO.contenu())
                .expediteur(expediteur)
                .destinataire(destinataire)
                .conversation(conversation)
                .lu(false)
                .build();

        Message savedMessage = messageRepository.save(message);

        // Mettre à jour la date du dernier message
        conversation.setDernierMessageDate(savedMessage.getDateEnvoi());
        
        return MessageMapper.INSTANCE.toDTO(savedMessage);
    }

    @Override
    public List<MessageDTO> getMessagesConversation(Long conversationId) {
        return MessageMapper.INSTANCE.toDTOList(messageRepository.findMessagesByConversationId(conversationId));
    }

    @Override
    @Transactional
    public void marquerCommeLu(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message non trouvé"));
        message.setLu(true);
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public void marquerTousMessagesLus(Long conversationId, Long userId) {
        List<Message> messages = messageRepository.findUnreadMessagesByConversationAndUser(conversationId, userId);
        messages.forEach(m -> m.setLu(true));
        messageRepository.saveAll(messages);
    }

    @Override
    public long compterMessagesNonLus(Long conversationId, Long userId) {
        return messageRepository.countUnreadMessages(conversationId, userId);
    }

    @Override
    public long compterTousMessagesNonLus(Long userId) {
        return messageRepository.countUnreadMessagesByDestinataire(userId);
    }

    private String getDisplayName(User user) {
        // Cette méthode devra être adaptée selon la structure
        // Pour l'instant, on retourne l'email
        return user.getEmail();
    }
}
