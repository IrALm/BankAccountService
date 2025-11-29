package com.example.BanqueApp.service.impl;

import com.example.BanqueApp.Mapper.BankAdvisorMapper;
import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.entity.Role;
import com.example.BanqueApp.model.createDTO.CreateBankAdvisorDTO;
import com.example.BanqueApp.model.readDTO.BankAdvisorDTO;
import com.example.BanqueApp.repository.BankAdvisorRepository;
import com.example.BanqueApp.service.BankAdvisorService;
import com.example.BanqueApp.service.EmailService;
import com.example.BanqueApp.service.MatriculeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAdvisorServiceImpl implements BankAdvisorService {

    private final BankAdvisorRepository bankAdvisorRepository;
    private final MatriculeService matriculeService;
    private final EmailService emailService;

    @Override
    @Transactional
    public BankAdvisor createBankAdvisor(CreateBankAdvisorDTO advisorDTO, String password) {
        // Créer le conseiller initial (juste email et password)
        BankAdvisor advisor = BankAdvisor.builder()
                .email(advisorDTO.email())
                .password(password)
                .role(Role.BANK_ADVISOR)
                .isTempPassword(true)
                .build();

        BankAdvisor savedAdvisor = bankAdvisorRepository.save(advisor);

        // Envoyer email avec mot de passe temporaire
        try {
            emailService.envoyerEmailMotDePasseTemporaire(
                    savedAdvisor.getEmail(),
                    password
            );
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email: " + e.getMessage());
        }

        return savedAdvisor;
    }

    // Nouvelle méthode pour finaliser le profil
    @Override
    @Transactional
    public void finalizeAdvisorProfile(BankAdvisor advisor) {
        // Générer un matricule unique
        String matricule = matriculeService.generateMatricule();
        advisor.setMatricule(matricule);
        
        bankAdvisorRepository.save(advisor);

        // Envoyer email de confirmation finale
        try {
            emailService.envoyerEmailNouveauConseiller(
                    advisor.getEmail(),
                    advisor.getNom(),
                    advisor.getPrenom(),
                    advisor.getEmail(),
                    "********",
                    matricule
            );
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email final: " + e.getMessage());
        }
    }

    @Override
    public BankAdvisorDTO getBankAdvisorById(Long id) {
        BankAdvisor advisor = bankAdvisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conseiller non trouvé"));
        return BankAdvisorMapper.INSTANCE.toDTO(advisor);
    }

    @Override
    public List<BankAdvisorDTO> getAllBankAdvisors() {
        return BankAdvisorMapper.INSTANCE.toDTOList(bankAdvisorRepository.findAll());
    }

}
