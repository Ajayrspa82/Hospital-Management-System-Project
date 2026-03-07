package com.wipro.amazecare.controller;

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

    // Doctor consultations month wise
    @GetMapping("/consultations/monthly")
    public ResponseEntity<ReportDto> getMonthlyConsultations(@RequestParam String month) {
        return ResponseEntity.ok(reportService.getMonthlyConsultationReport(month));
    }

    // Doctor summary
    @GetMapping("/doctor/{doctorId}/summary")
    public ResponseEntity<ReportDto> getDoctorSummary(@PathVariable Long doctorId) {
        return ResponseEntity.ok(reportService.getDoctorSummary(doctorId));
    }

    // Total patients
    @GetMapping("/patients/total")
    public ResponseEntity<Long> getTotalPatients() {
        return ResponseEntity.ok(reportService.getTotalPatients());
    }

    // Total consultations
    @GetMapping("/consultations/total")
    public ResponseEntity<Long> getTotalConsultations() {
        return ResponseEntity.ok(reportService.getTotalConsultations());
    }
}