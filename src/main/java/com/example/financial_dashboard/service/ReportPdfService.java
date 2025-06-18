package com.example.financial_dashboard.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Service
public class ReportPdfService {
    @Value("${function.reports.url}")
    private String baseUrl;

    @Value("${function.reports.key}")
    private String functionKey;


    private final WebClient webClient = WebClient.create();

    public byte[] gerarPdf(String userId, String mes, int ano) {
        // Montar a URL com os parâmetros e a chave da Function
        String fullUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("userId", userId)
                .queryParam("mes", mes)
                .queryParam("ano", ano)
                .queryParam("code", functionKey) // chave da Function para autorização
                .build()
                .toString();

        try {
            // Chamada GET e retorno como byte[]
            return webClient.get()
                    .uri(fullUrl)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao comunicar com a Function App: " + e.getMessage(), e);
        }
    }

}
