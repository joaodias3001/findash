package com.example.financial_dashboard.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.example.financial_dashboard.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CosmosRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);

}
