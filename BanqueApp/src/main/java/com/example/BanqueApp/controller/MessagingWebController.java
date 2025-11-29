package com.example.BanqueApp.controller;

import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.entity.User;
import com.example.BanqueApp.model.readDTO.ConversationDTO;
import com.example.BanqueApp.repository.BankAdvisorRepository;
import com.example.BanqueApp.repository.CustomerRepository;
import com.example.BanqueApp.repository.UserRepository;
import com.example.BanqueApp.service.ConversationService;
import com.example.BanqueApp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MessagingWebController {

    private final UserRepository userRepository;
    private final ConversationService conversationService;
    private final MessageService messageService;
    private final CustomerRepository customerRepository;
    private final BankAdvisorRepository bankAdvisorRepository;

    @GetMapping("/messaging")
    public String messaging(@RequestParam(required = false) Long conversationId, Authentication authentication, Model model) {
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userId", currentUser.getId());
        model.addAttribute("userRole", currentUser.getRole().name());

        // Charger les conversations
        List<ConversationDTO> conversations = conversationService.getConversationsUtilisateur(currentUser.getId());
        model.addAttribute("conversations", conversations);

        // Si une conversation est sélectionnée (ou par défaut la première)
        if (conversationId == null && !conversations.isEmpty()) {
            conversationId = conversations.get(0).id();
        }

        if (conversationId != null) {
            Conversation conversation = conversationService.getConversationById(conversationId);
            model.addAttribute("conversation", conversation);
            model.addAttribute("messages", messageService.getMessagesConversation(conversationId));
            
            // Déterminer l'autre utilisateur pour l'affichage
            User otherUser;
            if (currentUser.getId().equals(conversation.getClient().getId())) {
                otherUser = conversation.getConseiller();
            } else {
                otherUser = conversation.getClient();
            }
            model.addAttribute("otherUser", otherUser);
        }

        // Pour le conseiller, charger la liste de tous ses clients pour pouvoir créer une nouvelle conversation
        if (currentUser instanceof BankAdvisor advisor) {
            List<Customer> clients = customerRepository.findByAdvisorId(advisor.getId());
            model.addAttribute("clients", clients);
        }

        return "messaging";
    }

    @PostMapping("/messaging/create")
    public String createConversation(@RequestParam Long clientId, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (currentUser instanceof BankAdvisor advisor) {
             Conversation conversation = conversationService.getOrCreateConversation(clientId, advisor.getId());
             return "redirect:/messaging?conversationId=" + conversation.getId();
        }
        
        return "redirect:/messaging";
    }
}
