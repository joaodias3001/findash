package com.example.financial_dashboard;

import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FinancialDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialDashboardApplication.class, args);
	}

}
