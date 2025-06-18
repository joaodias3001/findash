package com.example.financial_dashboard.service;

import com.example.financial_dashboard.model.Report;
import com.example.financial_dashboard.model.Transaction;
import com.example.financial_dashboard.repository.ReportRepository;
import com.example.financial_dashboard.repository.TransactionRepository;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@Service
public class ReportService {

    private final TransactionRepository transactionRepository;
    private final ReportRepository repository;

    @Autowired
    public ReportService(ReportRepository repository, TransactionRepository transactionRepository) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
    }

    public Report createReport(Report report) {
        return repository.save(report);
    }
    public Report gerarRelatorio(String userId, Integer mes, Integer ano) {
        List<Transaction> transacoes = transactionRepository.findByUserId(userId);

        // Filtrar transações do mês e ano
        List<Transaction> filtradas = transacoes.stream()
                .filter(t -> t.getData().getMonthValue() == mes && t.getData().getYear() == ano)
                .toList();

        double totalReceitas = 0.0;
        double totalDespesas = 0.0;
        Map<String, Double> gastosPorCategoria = new HashMap<>();

        for (Transaction t : filtradas) {
            if ("Receita".equalsIgnoreCase(t.getTipo())) {
                totalReceitas += t.getValor();
            } else if ("Despesa".equalsIgnoreCase(t.getTipo())) {
                totalDespesas += t.getValor();

                gastosPorCategoria.merge(
                        t.getCategoria(),
                        t.getValor(),
                        Double::sum
                );
            }
        }

        double saldoFinal = totalReceitas - totalDespesas;

        Report relatorio = new Report(
                UUID.randomUUID().toString(),
                userId,
                nomeDoMes(mes),
                ano,
                totalReceitas,
                totalDespesas,
                saldoFinal,
                gastosPorCategoria
        );

        return repository.save(relatorio);
    }

    public void atualizarRelatorioParaTransacao(Transaction t,boolean remover) {
        String userId = t.getUserId();
        int mes = t.getData().getMonthValue();
        int ano = t.getData().getYear();
        String nomeMes = nomeDoMes(mes);

        Optional<Report> optRelatorio = repository.findByUserIdAndMesAndAno(userId, nomeMes, ano);

        Report relatorio;

        if (optRelatorio.isPresent()) {
            relatorio = optRelatorio.get();
        } else {
            // O relatório desse mês não existe
            if (!remover) {
                gerarRelatorio(userId, mes, ano);
            }
            return;
        }

        double valor = t.getValor();
        if (remover) {
            valor = -valor; // Inverte o sinal para subtrair os valores
        }

        // Atualiza os valores
        if ("Receita".equalsIgnoreCase(t.getTipo())) {
            relatorio.setTotalReceitas(relatorio.getTotalReceitas() + valor);
        } else if ("Despesa".equalsIgnoreCase(t.getTipo())) {
            relatorio.setTotalDespesas(relatorio.getTotalDespesas() + valor);

            Map<String, Double> gastos = relatorio.getGastosPorCategoria();
            String categoria = t.getCategoria();
            gastos.put(t.getCategoria(), gastos.getOrDefault(t.getCategoria(), 0.0) + valor);

            // Remove a categoria se o valor for zero ou negativo
            if (gastos.get(categoria) <= 0) {
                gastos.remove(categoria);
            }
            relatorio.setGastosPorCategoria(gastos);
        }

        relatorio.setSaldoFinal(relatorio.getTotalReceitas() - relatorio.getTotalDespesas());

        repository.save(relatorio);
    }


    private String nomeDoMes(Integer mes) {
        return Month.of(mes).getDisplayName(java.time.format.TextStyle.FULL, Locale.forLanguageTag("pt"));
    }

    public List<Report> getReportsByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public List<Report> getFilteredReports(String userId, String mes, Integer ano, String categoria){
        List<Report> allReports = getReportsByUserId(userId);

        return allReports.stream()
                .filter(r -> mes == null || r.getMes().equalsIgnoreCase(mes))
                .filter(r-> ano == null || r.getAno()==ano)
                .filter(r-> categoria == null || r.getGastosPorCategoria().containsKey(categoria))
                .toList();
    }
}
