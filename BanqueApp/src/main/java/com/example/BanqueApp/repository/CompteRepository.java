package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.CompteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<CompteEntity , Long> {
}
