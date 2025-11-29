package com.example.BanqueApp.service.impl;

import com.example.BanqueApp.Mapper.ConversationMapper;
import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.model.readDTO.ConversationDTO;
import com.example.BanqueApp.repository.BankAdvisorRepository;
import com.example.BanqueApp.repository.ConversationRepository;
import com.example.BanqueApp.repository.CustomerRepository;
import com.example.BanqueApp.repository.MessageRepository;
import com.example.BanqueApp.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final CustomerRepository customerRepository;
    private final BankAdvisorRepository bankAdvisorRepository;
    private final MessageRepository messageRepository;
    private final ConversationMapper conversationMapper;

    @Override
    @Transactional
    public Conversation getOrCreateConversation(Long clientId, Long conseillerId) {
        return conversationRepository.findConversationByClientAndConseiller(clientId, conseillerId)
                .orElseGet(() -> {
                    Customer client = customerRepository.findById(clientId)
                            .orElseThrow(() -> new RuntimeException("Client non trouvé"));
                    BankAdvisor conseiller = bankAdvisorRepository.findById(conseillerId)
                            .orElseThrow(() -> new RuntimeException("Conseiller non trouvé"));

                    Conversation conversation = Conversation.builder()
                            .client(client)
                            .conseiller(conseiller)
                            .build();

                    return conversationRepository.save(conversation);
                });
    }

    @Override
    public ConversationDTO getConversationDTO(Long conversationId, Long userId) {
        Conversation conversation = getConversationById(conversationId);
        long messagesNonLus = messageRepository.countUnreadMessages(conversationId, userId);

        // Mapper l'entité en DTO
        ConversationDTO dto = conversationMapper.toDTO(conversation);

        // Créer un nouveau DTO avec messagesNonLus
        return new ConversationDTO(
                dto.id(),
                dto.clientId(),
                dto.clientNom(),
                dto.conseillerId(),
                dto.conseillerNom(),
                dto.dateCreation(),
                dto.dernierMessageDate(),
                messagesNonLus // On injecte le nombre ici
        );
    }


    @Override
    public List<ConversationDTO> getConversationsUtilisateur(Long userId) {
        List<Conversation> conversations = conversationRepository.findConversationsByUserId(userId);

        return conversations.stream()
                .map(conversation -> {
                    // Mapper l'entité en DTO
                    ConversationDTO dto = conversationMapper.toDTO(conversation);

                    // Calculer le nombre de messages non lus pour cet utilisateur
                    long messagesNonLus = messageRepository.countUnreadMessages(conversation.getId(), userId);

                    // Créer un nouveau DTO avec messagesNonLus
                    return new ConversationDTO(
                            dto.id(),
                            dto.clientId(),
                            dto.clientNom(),
                            dto.conseillerId(),
                            dto.conseillerNom(),
                            dto.dateCreation(),
                            dto.dernierMessageDate(),
                            messagesNonLus
                    );
                })
                .collect(Collectors.toList());
    }


    @Override
    public Conversation getConversationById(Long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation non trouvée"));
    }
}
