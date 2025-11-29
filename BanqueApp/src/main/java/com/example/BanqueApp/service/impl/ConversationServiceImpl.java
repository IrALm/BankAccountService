package com.example.BanqueApp.service.impl;

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

    @Override
    @Transactional
    public Conversation getOrCreateConversation(Long clientId, Long conseillerId) {
        return conversationRepository.findByClientIdAndConseillerId(clientId, conseillerId)
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
        long messagesNonLus = messageRepository.countByConversationIdAndDestinataire_IdAndLuFalse(conversationId, userId);
        return toDTO(conversation, messagesNonLus);
    }

    @Override
    public List<ConversationDTO> getConversationsUtilisateur(Long userId) {
        List<Conversation> conversations = conversationRepository.findAllByUserIdOrderByDernierMessageDateDesc(userId);
        return conversations.stream()
                .map(conv -> {
                    long messagesNonLus = messageRepository.countByConversationIdAndDestinataire_IdAndLuFalse(conv.getId(), userId);
                    return toDTO(conv, messagesNonLus);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Conversation getConversationById(Long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation non trouvée"));
    }

    private ConversationDTO toDTO(Conversation conversation, long messagesNonLus) {
        return new ConversationDTO(
                conversation.getId(),
                conversation.getClient().getId(),
                conversation.getClient().getNom() + " " + conversation.getClient().getPrenom(),
                conversation.getConseiller().getId(),
                conversation.getConseiller().getNom() + " " + conversation.getConseiller().getPrenom(),
                conversation.getDateCreation(),
                conversation.getDernierMessageDate(),
                messagesNonLus
        );
    }
}
