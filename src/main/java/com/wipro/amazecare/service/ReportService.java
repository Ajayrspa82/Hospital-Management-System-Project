package com.wipro.amazecare.service;

import com.wipro.amazecare.dto.ReportDto;

public interface ReportService {

    // Doctor consultations month wise
    ReportDto getMonthlyConsultationReport(String month);

    // Doctor summary (total consultations for a doctor)
    ReportDto getDoctorSummary(Long doctorId);

    // Total patients
    Long getTotalPatients();

    // Total consultations
    Long getTotalConsultations();
}