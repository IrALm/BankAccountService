package com.example.BanqueApp.service.impl;

import com.example.BanqueApp.entity.Role;
import com.example.BanqueApp.entity.User;
import com.example.BanqueApp.repository.UserRepository;
import com.example.BanqueApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final com.example.BanqueApp.service.EmailService emailService;

    @Override
    public User createUser(String email , String password , String roleStr){

        // Vérifie si l'email existe déjà
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà : " + email);
        }

        Role role = Role.valueOf(roleStr);
        User user;

        if (role == Role.CUSTOMER) {
            user = com.example.BanqueApp.entity.Customer.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .isTempPassword(true)
                    .build();
        } else if (role == Role.BANK_ADVISOR) {
            user = com.example.BanqueApp.entity.BankAdvisor.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .isTempPassword(true)
                    .build();
        } else {
            // Fallback pour ADMIN ou autre
            user = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .isTempPassword(true)
                    .build();
        }

        userRepository.save(user);

        try {
            emailService.envoyerEmailMotDePasseTemporaire(email, password);
        } catch (Exception e) {
            System.err.println("Erreur envoi email: " + e.getMessage());
        }
        
        return user;
    }

    private String generateTempPassword(){

        return UUID.randomUUID().toString().substring(0 , 8);
    }

}
