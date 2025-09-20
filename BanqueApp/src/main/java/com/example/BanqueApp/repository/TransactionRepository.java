package com.example.BanqueApp.repository;


import com.example.BanqueApp.entity.Transaction;
import com.example.BanqueApp.model.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction , Long> {

    List<Transaction> findByTypetransaction(TypeTransaction typetransaction);
}
