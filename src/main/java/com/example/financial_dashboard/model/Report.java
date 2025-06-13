package com.example.financial_dashboard.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Container(containerName = "reports")
public class Report {

    @Id
    private String id;

    @PartitionKey
    private String userId;

    private Integer ano;
    private String mes;
    private double totalReceitas;
    private double totalDespesas;
    private double saldoFinal;

    @JsonProperty("gastosPorCategoria")
    private Map<String, Double> gastosPorCategoria;

    // Construtor vazio (necessário para o Cosmos DB)
    public Report() {}

    // Construtor completo (opcional)
    public Report(String id, String userId, String mes, Integer ano, double totalReceitas, double totalDespesas, double saldoFinal, Map<String, Double> gastosPorCategoria) {
        this.id = id;
        this.userId = userId;
        this.ano = ano;
        this.mes = mes;
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.saldoFinal = saldoFinal;
        this.gastosPorCategoria = gastosPorCategoria;
    }

    // Getters e setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public double getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(double totalReceitas) {
        this.totalReceitas = totalReceitas;
    }

    public double getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(double totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public Map<String, Double> getGastosPorCategoria() {
        return gastosPorCategoria;
    }

    public void setGastosPorCategoria(Map<String, Double> gastosPorCategoria) {
        this.gastosPorCategoria = gastosPorCategoria;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
