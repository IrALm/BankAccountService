package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.BankAdvisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BankAdvisorRepository extends JpaRepository<BankAdvisor , Long> {
    
    @Query("SELECT b FROM BankAdvisor b ORDER BY SIZE(b.clients) ASC LIMIT 1")
    Optional<BankAdvisor> findAdvisorWithFewestClients();
}
