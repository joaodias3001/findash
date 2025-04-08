package com.example.financial_dashboard.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.example.financial_dashboard.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CosmosRepository<User, String> {
    Optional<User> findByEmail(String email);
}
