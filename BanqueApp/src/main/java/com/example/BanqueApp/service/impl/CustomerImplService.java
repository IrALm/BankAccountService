package com.example.BanqueApp.service.impl;

import com.example.BanqueApp.Mapper.CustomerMapper;
import com.example.BanqueApp.entity.*;
import com.example.BanqueApp.model.createDTO.CreateCustomerDTO;
import com.example.BanqueApp.model.readDTO.CustomerDTO;
import com.example.BanqueApp.repository.BankAdvisorRepository;
import com.example.BanqueApp.repository.CountRepository;
import com.example.BanqueApp.repository.CustomerRepository;
import com.example.BanqueApp.service.CustomerService;
import com.example.BanqueApp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerImplService implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BankAdvisorRepository bankAdvisorRepository;
    private final CountRepository countRepository;
    private final EmailService emailService;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public Customer createCustomer(CreateCustomerDTO customerDTO, String password) {
        // Créer le client initial (juste email et password)
        Customer customer = Customer.builder()
                .email(customerDTO.email())
                .password(password)
                .role(Role.CUSTOMER)
                .isTempPassword(true)
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        // Envoyer email avec mot de passe temporaire
        try {
            emailService.envoyerEmailMotDePasseTemporaire(
                    savedCustomer.getEmail(),
                    password
            );
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email: " + e.getMessage());
        }

        return savedCustomer;
    }

    // Nouvelle méthode pour finaliser le profil (à appeler depuis ProfileController)
    @Transactional
    public void finalizeCustomerProfile(Customer customer) {
        // Trouver le conseiller avec le moins de clients
        BankAdvisor advisor = bankAdvisorRepository.findAdvisorWithFewestClients()
                .orElseThrow(() -> new RuntimeException("Aucun conseiller disponible"));
        
        customer.setAdvisor(advisor);

        // Créer automatiquement un compte bancaire
        String numeroCompte = genererNumeroCompte();
        Count compte = Count.builder()
                .numeroCompte(numeroCompte)
                .typeCompte(TypeCompte.COURANT)
                .solde(BigDecimal.ZERO)
                .client(customer)
                .build();

        countRepository.save(compte);
        customerRepository.save(customer); // Sauvegarder l'association conseiller

        // Envoyer email de confirmation finale
        try {
            emailService.envoyerEmailNouveauClient(
                    customer.getEmail(),
                    customer.getNom(),
                    customer.getPrenom(),
                    customer.getEmail(),
                    "********", // On ne renvoie pas le mot de passe ici
                    numeroCompte
            );
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email final: " + e.getMessage());
        }
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return customerMapper.toDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    private String genererNumeroCompte() {
        // Génère un numéro de compte de 16 chiffres
        Random random = new Random();
        StringBuilder numeroCompte = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            numeroCompte.append(random.nextInt(10));
        }
        return numeroCompte.toString();
    }
}

