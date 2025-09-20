package com.example.BanqueApp.service;

import com.example.BanqueApp.repository.CompteRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class CompteService {

    private final CompteRepository compteRepository;
}
