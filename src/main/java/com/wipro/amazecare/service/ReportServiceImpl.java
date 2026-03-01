package com.wipro.amazecare.service;

import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.ReportDto;
import com.wipro.amazecare.service.ReportService;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.repository.MedicalRecordRepository;
import com.wipro.amazecare.repository.MedicalTestRepository;
import com.wipro.amazecare.repository.PrescriptionRepository;

@Service
public class ReportServiceImpl implements ReportService {

    private final ConsultationRepository consultationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalTestRepository medicalTestRepository;

    public ReportServiceImpl(ConsultationRepository consultationRepository,
                             PrescriptionRepository prescriptionRepository,
                             MedicalRecordRepository medicalRecordRepository,
                             MedicalTestRepository medicalTestRepository) {

        this.consultationRepository = consultationRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicalTestRepository = medicalTestRepository;
    }

    @Override
    public ReportDto generateSystemReport() {

        ReportDto report = new ReportDto();

        report.setTotalConsultations(consultationRepository.count());
        report.setTotalPrescriptions(prescriptionRepository.count());
        report.setTotalMedicalRecords(medicalRecordRepository.count());
        report.setTotalTests(medicalTestRepository.count());

        return report;
    }
}