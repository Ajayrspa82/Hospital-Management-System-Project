package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.PrescriptionDto;
import com.wipro.amazecare.entity.Prescription;
import com.wipro.amazecare.exception.ResourceNotFoundException;
import com.wipro.amazecare.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public PrescriptionDto addPrescription(PrescriptionDto dto) {
        Prescription prescription = new Prescription();
        // map DTO to entity
        prescription.setMedicineName(dto.getMedicineName());
        prescription.setDosage(dto.getDosage());
        prescription.setDurationDays(dto.getDurationDays());
        // here, make sure consultation is set in controller/service layer
        // prescription.setConsultation(...);

        prescriptionRepository.save(prescription);
        dto.setPrescriptionId(prescription.getPrescriptionId());
        return dto;
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsByConsultation(Long consultationId) {
        List<Prescription> prescriptions = prescriptionRepository
                .findByConsultation_ConsultationId(consultationId);

        return prescriptions.stream()
                .map(p -> {
                    PrescriptionDto dto = new PrescriptionDto();
                    dto.setPrescriptionId(p.getPrescriptionId());
                    dto.setConsultationId(p.getConsultation().getConsultationId());
                    dto.setMedicineName(p.getMedicineName());
                    dto.setDosage(p.getDosage());
                    dto.setDurationDays(p.getDurationDays());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deletePrescription(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription not found with id: " + id));
        prescriptionRepository.delete(prescription);
    }

    @Override
    public PrescriptionDto updatePrescription(Long id, PrescriptionDto dto) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription not found with id: " + id));

        if (dto.getMedicineName() != null) prescription.setMedicineName(dto.getMedicineName());
        if (dto.getDosage() != null) prescription.setDosage(dto.getDosage());
        if (dto.getDurationDays() > 0) prescription.setDurationDays(dto.getDurationDays());

        prescriptionRepository.save(prescription);

        dto.setPrescriptionId(prescription.getPrescriptionId());
        return dto;
    }

    @Override
    public PrescriptionDto getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription not found with id: " + id));
        PrescriptionDto dto = new PrescriptionDto();
        dto.setPrescriptionId(prescription.getPrescriptionId());
        dto.setConsultationId(prescription.getConsultation().getConsultationId());
        dto.setMedicineName(prescription.getMedicineName());
        dto.setDosage(prescription.getDosage());
        dto.setDurationDays(prescription.getDurationDays());
        return dto;
    }
}