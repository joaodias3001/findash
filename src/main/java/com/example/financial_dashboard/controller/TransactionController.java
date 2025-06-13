package com.example.financial_dashboard.controller;

import com.example.financial_dashboard.model.Transaction;
import com.example.financial_dashboard.model.User;
import com.example.financial_dashboard.service.BlobStorageService;
import com.example.financial_dashboard.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;
    private final BlobStorageService blobStorageService;

    @Autowired
    public TransactionController(TransactionService service, BlobStorageService blobStorageService) {
        this.service = service;
        this.blobStorageService = blobStorageService;
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Transaction> criarTransacao(
            @RequestParam String tipo,
            @RequestParam double valor,
            @RequestParam String categoria,
            @RequestParam String descricao,
            @RequestParam LocalDate data,
            @RequestParam String userId,
            @RequestParam(required = false) MultipartFile file
    ) {
        try {
            String comprovativoUrl = null;
            if (file != null && !file.isEmpty()) {
                comprovativoUrl = blobStorageService.uploadFile(file);
            }

            Transaction transaction = new Transaction();
            transaction.setId(UUID.randomUUID().toString());
            transaction.setTipo(tipo);
            transaction.setValor(valor);
            transaction.setCategoria(categoria);
            transaction.setDescricao(descricao);
            transaction.setData(data);
            transaction.setUserId(userId);
            transaction.setComprovativoUrl(comprovativoUrl);

            Transaction saved = service.create(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*
    @PostMapping
    public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction transaction ) {
        Transaction saved = service.create(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    */
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
