package com.example.BanqueApp.controller;

import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.entity.Role;
import com.example.BanqueApp.entity.User;
import com.example.BanqueApp.model.createDTO.CreateBankAdvisorDTO;
import com.example.BanqueApp.model.createDTO.CreateCustomerDTO;
import com.example.BanqueApp.repository.UserRepository;
import com.example.BanqueApp.service.BankAdvisorService;
import com.example.BanqueApp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final BankAdvisorService bankAdvisorService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si le profil est complet
        if (user.getRole() == Role.CUSTOMER) {
            Customer customer = (Customer) user;
            if (customer.getNom() == null || customer.getNom().isEmpty()) {
                return "redirect:/complete-profile-client";
            }
        } else if (user.getRole() == Role.BANK_ADVISOR) {
            BankAdvisor advisor = (BankAdvisor) user;
            if (advisor.getNom() == null || advisor.getNom().isEmpty()) {
                return "redirect:/complete-profile-advisor";
            }
        }

        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si le profil est complet
        if (user.getRole() == Role.CUSTOMER) {
            Customer customer = (Customer) user;
            if (customer.getNom() == null || customer.getNom().isEmpty()) {
                return "redirect:/complete-profile-client";
            }
        } else if (user.getRole() == Role.BANK_ADVISOR) {
            BankAdvisor advisor = (BankAdvisor) user;
            if (advisor.getNom() == null || advisor.getNom().isEmpty()) {
                return "redirect:/complete-profile-advisor";
            }
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/complete-profile-client")
    public String completeProfileClientForm(Model model) {
        model.addAttribute("customerDTO", new CreateCustomerDTO(null, null, null, null, null, null, null));
        return "complete-profile-client";
    }

    @PostMapping("/complete-profile-client")
    public String completeProfileClient(@ModelAttribute CreateCustomerDTO customerDTO,
                                        Authentication authentication,
                                        RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Mettre à jour les informations du client
        Customer customer = (Customer) user;
        customer.setNom(customerDTO.nom());
        customer.setPrenom(customerDTO.prenom());
        customer.setTelephone(customerDTO.telephone());
        customer.setAdresse(customerDTO.adresse());
        customer.setDateNaissance(customerDTO.dateNaissance());
        
        // Finaliser le profil (compte bancaire, conseiller, email)
        customerService.finalizeCustomerProfile(customer);

        redirectAttributes.addFlashAttribute("message", "Votre profil a été complété avec succès ! Un email avec vos informations de compte a été envoyé.");
        return "redirect:/dashboard";
    }

    @GetMapping("/complete-profile-advisor")
    public String completeProfileAdvisorForm(Model model) {
        model.addAttribute("advisorDTO", new CreateBankAdvisorDTO(null, null, null, null, null, null));
        return "complete-profile-advisor";
    }

    @PostMapping("/complete-profile-advisor")
    public String completeProfileAdvisor(@ModelAttribute CreateBankAdvisorDTO advisorDTO,
                                         Authentication authentication,
                                         RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Mettre à jour les informations du conseiller
        BankAdvisor advisor = (BankAdvisor) user;
        advisor.setNom(advisorDTO.nom());
        advisor.setPrenom(advisorDTO.prenom());
        advisor.setTelephone(advisorDTO.telephone());
        advisor.setAdresse(advisorDTO.adresse());
        advisor.setDateNaissance(advisorDTO.dateNaissance());
        
        // Finaliser le profil (matricule, email)
        bankAdvisorService.finalizeAdvisorProfile(advisor);

        redirectAttributes.addFlashAttribute("message", "Votre profil a été complété avec succès ! Un email avec votre matricule a été envoyé.");
        return "redirect:/dashboard";
    }
}
