package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.PrescriptionDto;
import com.wipro.amazecare.entity.Prescription;
import com.wipro.amazecare.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;

    public PrescriptionServiceImpl(PrescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public PrescriptionDto addPrescription(PrescriptionDto dto) {
        Prescription prescription = new Prescription();
        prescription.setConsultationId(dto.getConsultationId());
        prescription.setMedicineName(dto.getMedicineName());
        prescription.setDosage(dto.getDosage());
        prescription.setDurationDays(dto.getDurationDays());

        repository.save(prescription);
        dto.setPrescriptionId(prescription.getPrescriptionId());
        return dto;
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsByConsultation(Long consultationId) {
        return repository.findByConsultationId(consultationId).stream().map(p -> {
            PrescriptionDto dto = new PrescriptionDto();
            dto.setPrescriptionId(p.getPrescriptionId());
            dto.setConsultationId(p.getConsultationId());
            dto.setMedicineName(p.getMedicineName());
            dto.setDosage(p.getDosage());
            dto.setDurationDays(p.getDurationDays());
            return dto;
        }).collect(Collectors.toList());
    }
}