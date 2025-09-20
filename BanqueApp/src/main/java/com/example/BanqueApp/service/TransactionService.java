package com.example.BanqueApp.service;

import com.example.BanqueApp.repository.TransactionRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class TransactionService {

    private final TransactionRepository transactionRepository;
}
