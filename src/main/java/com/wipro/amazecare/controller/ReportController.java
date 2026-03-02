package com.wipro.amazecare.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.amazecare.dto.ReportDto;
import com.wipro.amazecare.service.ReportService;

@RestController
@RequestMapping("/api/reports")
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/consultations/monthly")
    public ResponseEntity<ReportDto> getMonthlyConsultations() {
        return ResponseEntity.ok(reportService.getMonthlyConsultationReport());
    }

    @GetMapping("/doctor/{doctorId}/summary")
    public ResponseEntity<ReportDto> getDoctorSummary(@PathVariable Long doctorId) {
        return ResponseEntity.ok(reportService.getDoctorSummary(doctorId));
    }

    @GetMapping("/patients/total")
    public ResponseEntity<Long> getTotalPatients() {
        return ResponseEntity.ok(reportService.getTotalPatients());
    }

    @GetMapping("/consultations/total")
    public ResponseEntity<Long> getTotalConsultations() {
        return ResponseEntity.ok(reportService.getTotalConsultations());
    }

    @PostMapping("/create")
    public ResponseEntity<ReportDto> createReport(@Valid @RequestBody ReportDto reportDto) {
        // The service method should handle saving/processing the report
        ReportDto savedReport = reportService.generateSystemReport(); // example; replace with real save
        return ResponseEntity.ok(savedReport);
    }
}