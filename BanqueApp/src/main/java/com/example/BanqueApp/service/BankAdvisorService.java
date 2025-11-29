package com.example.BanqueApp.service;

import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.model.createDTO.CreateBankAdvisorDTO;
import com.example.BanqueApp.model.readDTO.BankAdvisorDTO;

import java.util.List;
import java.util.Optional;

public interface BankAdvisorService {

    /**
     * Créer un utilisateur avec le rôle Conseiller Bancaire
     * @param advisorDTO
     * @param password
     * @return utilisateur créer
     */
    BankAdvisor createBankAdvisor(CreateBankAdvisorDTO advisorDTO, String password);

    /**
     * Finalise son inscription
     * @param advisor
     */
    void finalizeAdvisorProfile(BankAdvisor advisor);
    
    BankAdvisorDTO getBankAdvisorById(Long id);
    
    List<BankAdvisorDTO> getAllBankAdvisors();

}
