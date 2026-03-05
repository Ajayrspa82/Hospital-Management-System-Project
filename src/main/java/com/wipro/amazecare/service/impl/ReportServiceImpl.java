package com.wipro.amazecare.service.impl;

import java.time.Month;

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

        this.consultationRepository = consultationRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public ReportDto getMonthlyConsultationReport(String month) {

        ReportDto report = new ReportDto();


        int monthNumber = Month.valueOf(month.toUpperCase()).getValue();


        Long monthlyCount =
                consultationRepository.countByConsultationDateBetween(startDate, endDate);

        Long doctorConsultations =
                consultationRepository.countByDoctorAndMonth(1L, monthNumber);


        report.setDoctorConsultations(doctorConsultations);
        report.setMonth(month.toUpperCase());

        return report;
    }

    @Override
    public ReportDto getDoctorSummary(Long doctorId) {

        ReportDto report = new ReportDto();

        Long doctorConsultations =
                consultationRepository.countByDoctor_DoctorId(doctorId);

        report.setDoctorConsultations(doctorConsultations);

        report.setMonth(YearMonth.now().getMonth());

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