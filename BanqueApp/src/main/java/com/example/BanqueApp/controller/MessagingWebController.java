package com.example.BanqueApp.controller;

import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.entity.User;
import com.example.BanqueApp.entity.Role;
import com.example.BanqueApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MessagingWebController {

    private final UserRepository userRepository;

    @GetMapping("/messaging")
    public String messaging(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        model.addAttribute("userId", user.getId());
        model.addAttribute("userRole", user.getRole().name());

        return "messaging";
    }
}
