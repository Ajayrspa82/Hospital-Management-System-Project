package com.wipro.amazecare.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.ReportDto;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.repository.MedicalRecordRepository;
import com.wipro.amazecare.repository.MedicalTestRepository;
import com.wipro.amazecare.repository.PrescriptionRepository;
import com.wipro.amazecare.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalTestRepository medicalTestRepository;

    @Override
    public ReportDto generateSystemReport() {

        ReportDto report = new ReportDto();

        report.setTotalConsultations(consultationRepository.count());
        report.setTotalPrescriptions(prescriptionRepository.count());
        report.setTotalMedicalRecords(medicalRecordRepository.count());
        report.setTotalTests(medicalTestRepository.count());

        // Fix for null values
        report.setTotalPatients(consultationRepository.countDistinctPatients());
        report.setDoctorConsultations(0L);
        report.setMonth(YearMonth.now().getMonth());

        return report;
    }

    @Override
    public ReportDto getMonthlyConsultationReport() {

        ReportDto report = new ReportDto();

        YearMonth currentMonth = YearMonth.now();

        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();

        Long monthlyCount =
                consultationRepository.countByConsultationDateBetween(startDate, endDate);

        report.setTotalConsultations(monthlyCount);
        report.setMonth(currentMonth.getMonth());

        return report;
    }

    @Override
    public ReportDto getDoctorSummary(Long doctorId) {

        ReportDto report = new ReportDto();

        Long doctorConsultations =
                consultationRepository.countByDoctor_DoctorId(doctorId);

        report.setDoctorConsultations(doctorConsultations);
        report.setMonth(YearMonth.now().getMonth());

        return report;
    }

    @Override
    public Long getTotalPatients() {
        return consultationRepository.countDistinctPatients();
    }

    @Override
    public Long getTotalConsultations() {
        return consultationRepository.count();
    }
}