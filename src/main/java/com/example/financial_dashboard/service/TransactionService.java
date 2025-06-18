package com.example.financial_dashboard.service;



import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.spring.data.cosmos.core.CosmosTemplate;

import com.example.financial_dashboard.model.Transaction;
import com.example.financial_dashboard.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final ReportService reportService;
    private final CosmosContainer transactionContainer;

    @Autowired
    public TransactionService(CosmosClient cosmosClient, TransactionRepository repository, ReportService reportService, @Value("${spring.cloud.azure.cosmos.database}") String databaseName,
                              @Value("transactions") String containerName) {
        this.repository = repository;
        this.reportService = reportService;
        this.transactionContainer = cosmosClient.getDatabase(databaseName).getContainer(containerName);

    }

    public Transaction create(Transaction transaction) {
        Transaction newTransaction = repository.save(transaction);
        reportService.atualizarRelatorioParaTransacao(newTransaction,false);
        return repository.save(newTransaction);
    }

    public List<Transaction> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }



    public void delete(String id, String userId) {
        Transaction transaction = repository.findById(id).orElse(null);
        transactionContainer.deleteItem(id, new PartitionKey(userId), new CosmosItemRequestOptions());
        reportService.atualizarRelatorioParaTransacao(transaction,true);
    }
}
