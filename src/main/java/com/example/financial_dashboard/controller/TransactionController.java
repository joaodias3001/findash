package com.example.financial_dashboard.controller;

import com.example.financial_dashboard.model.Transaction;
import com.example.financial_dashboard.model.User;
import com.example.financial_dashboard.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction transaction) {
        Transaction saved = service.create(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> listByUser(@PathVariable String userId) {
        if(service.findByUserId(userId).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Transaction>transactions=service.findByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
