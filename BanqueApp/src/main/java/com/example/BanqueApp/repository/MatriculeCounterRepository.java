package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.MatriculeCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculeCounterRepository extends JpaRepository<MatriculeCounter , Long> {
}
