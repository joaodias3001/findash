package com.example.financial_dashboard.controller;

import com.example.financial_dashboard.model.Report;
import com.example.financial_dashboard.model.Transaction;
import com.example.financial_dashboard.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /*
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Report>> getReportsByUser(@PathVariable String userId) {
        if(reportService.getReportsByUserId(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        List<Report> reports = reportService.getReportsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }*/

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Report>> getReportsByFilters(
            @PathVariable String userId,
            @RequestParam(required = false) String mes,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String categoria) {

        if(reportService.getReportsByUserId(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        List<Report> reports = reportService.getFilteredReports(userId, mes, ano, categoria);

        if (reports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/gerar")
    public ResponseEntity<Report> createReport(@RequestParam String userId,
                                               @RequestParam Integer ano,
                                               @RequestParam Integer mes){

        Report newReport = reportService.gerarRelatorio(userId, ano, mes);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReport);
    }


}
