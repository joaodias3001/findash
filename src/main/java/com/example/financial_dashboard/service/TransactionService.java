package com.example.financial_dashboard.service;

import com.example.financial_dashboard.model.Transaction;
import com.example.financial_dashboard.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    public List<Transaction> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
