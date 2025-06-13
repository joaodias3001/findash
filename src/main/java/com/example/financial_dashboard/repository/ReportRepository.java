package com.example.financial_dashboard.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.example.financial_dashboard.model.Report;
import com.example.financial_dashboard.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends CosmosRepository<Report, String> {
    List<Report> findByUserId(String userId);

    Optional<Report> findByUserIdAndMesAndAno(String userId, String mes, Integer ano);

}
