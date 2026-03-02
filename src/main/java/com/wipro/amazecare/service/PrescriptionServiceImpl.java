package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.PrescriptionDto;
import com.wipro.amazecare.entity.Prescription;
import com.wipro.amazecare.entity.Consultation;
import com.wipro.amazecare.exception.ResourceNotFoundException;
import com.wipro.amazecare.repository.PrescriptionRepository;
import com.wipro.amazecare.repository.ConsultationRepository;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository repository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public PrescriptionDto addPrescription(PrescriptionDto dto) {

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found"));

        Prescription prescription = new Prescription();
        prescription.setConsultation(consultation);
        prescription.setMedicineName(dto.getMedicineName());
        prescription.setDosage(dto.getDosage());
        prescription.setDurationDays(dto.getDurationDays());

        repository.save(prescription);

        dto.setPrescriptionId(prescription.getPrescriptionId());
        return dto;
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsByConsultation(Long consultationId) {

        return repository.findByConsultation_ConsultationId(consultationId)
                .stream()
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
}