package com.example.financial_dashboard.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Container(containerName = "transactions")
public class Transaction {
    @Id
    private String id = UUID.randomUUID().toString();

    @PartitionKey
    private String userId;

    private String tipo;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private Double valor;

    private String categoria;

    private String descricao;

    @NotNull(message = "A data é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    private String comprovativoUrl;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getComprovativoUrl() {
        return comprovativoUrl;
    }

    public void setComprovativoUrl(String comprovativoUrl) {
        this.comprovativoUrl = comprovativoUrl;
    }
}
