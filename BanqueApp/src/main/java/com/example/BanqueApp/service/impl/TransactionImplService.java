package com.example.BanqueApp.service.impl;

import com.example.BanqueApp.repository.TransactionRepository;
import com.example.BanqueApp.service.TransactionService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class TransactionImplService implements TransactionService {

    private final TransactionRepository transactionRepository;
}
