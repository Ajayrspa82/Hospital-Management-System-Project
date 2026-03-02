package com.wipro.amazecare.service;

import com.wipro.amazecare.dto.ReportDto;

public interface ReportService {

    ReportDto generateSystemReport();

    ReportDto getMonthlyConsultationReport();

    ReportDto getDoctorSummary(Long doctorId);

    Long getTotalPatients();

    Long getTotalConsultations();
}