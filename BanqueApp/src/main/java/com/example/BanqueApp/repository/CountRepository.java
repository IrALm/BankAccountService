package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.Count;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountRepository extends JpaRepository<Count, Long> {

    Optional<Count> findByNumeroCompte( String numeroCompte);
    Optional<Count> findByTypeCompte( String typeCompte);
    List<Count> findByClient(Count client);
}
