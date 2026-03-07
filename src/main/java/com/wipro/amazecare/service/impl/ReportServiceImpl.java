package com.wipro.amazecare.service.impl;

import java.time.YearMonth;

import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.ReportDto;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.repository.PatientRepository;
import com.wipro.amazecare.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;

    public ReportServiceImpl(ConsultationRepository consultationRepository,
                             PatientRepository patientRepository) {
        this.consultationRepository = consultationRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public ReportDto generateSystemReport() {
        ReportDto report = new ReportDto();

        report.setTotalConsultations(consultationRepository.count());
        report.setTotalPatients(patientRepository.count());

        // Set doctor consultations and month as defaults
        report.setDoctorConsultations(0L);
        report.setMonth(YearMonth.now().getMonth().name());

        return report;
    }

    @Override
    public ReportDto getMonthlyConsultationReport() {
        // Implement logic to get report for current month
        return generateSystemReport();
    }

    @Override
    public ReportDto getMonthlyConsultationReport(String month) {
        ReportDto report = generateSystemReport();
        report.setMonth(month.toUpperCase());
        return report;
    }

    @Override
    public ReportDto getDoctorSummary(Long doctorId) {
        ReportDto report = generateSystemReport();
        Long doctorConsultations = consultationRepository.countByDoctor_DoctorId(doctorId);
        report.setDoctorConsultations(doctorConsultations);
        report.setMonth("ALL");
        return report;
    }

    @Override
    public Long getTotalPatients() {
        return patientRepository.count();
    }

    @Override
    public Long getTotalConsultations() {
        return consultationRepository.count();
    }
}