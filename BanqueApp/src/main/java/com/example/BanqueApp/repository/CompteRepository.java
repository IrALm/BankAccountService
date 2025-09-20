package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.ClientEntity;
import com.example.BanqueApp.entity.CompteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<CompteEntity , Long> {

    Optional<CompteEntity> findByNumeroCompte( String numeroCompte);
    Optional<CompteEntity> findByTypeCompte( String typeCompte);
    List<CompteEntity> findByClient(ClientEntity client);
}
