package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.PrescriptionDto;
import com.wipro.amazecare.entity.Consultation;
import com.wipro.amazecare.entity.Prescription;
import com.wipro.amazecare.exception.ResourceNotFoundException;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public PrescriptionDto addPrescription(PrescriptionDto dto) {

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Consultation not found with id: " + dto.getConsultationId()));

        Prescription prescription = new Prescription();
        prescription.setConsultation(consultation);
        prescription.setMedicineName(dto.getMedicineName());
        prescription.setDosage(dto.getDosage());
        prescription.setDuration(dto.getDuration());
        prescription.setDurationDays(dto.getDurationDays());

        prescriptionRepository.save(prescription);

        return mapToDto(prescription);
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsByConsultation(Long consultationId) {

        if (!consultationRepository.existsById(consultationId)) {
            throw new ResourceNotFoundException(
                    "Consultation not found with id: " + consultationId);
        }

        return prescriptionRepository
                .findByConsultation_ConsultationId(consultationId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PrescriptionDto getPrescriptionById(Long id) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found with id: " + id));

        return mapToDto(prescription);
    }

    @Override
    public PrescriptionDto updatePrescription(Long id, PrescriptionDto dto) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found with id: " + id));

        if (dto.getMedicineName() != null && !dto.getMedicineName().isBlank())
            prescription.setMedicineName(dto.getMedicineName());

        if (dto.getDosage() != null && !dto.getDosage().isBlank())
            prescription.setDosage(dto.getDosage());

        if (dto.getDuration() != null)
            prescription.setDuration(dto.getDuration());

        if (dto.getDurationDays() > 0)
            prescription.setDurationDays(dto.getDurationDays());

        prescriptionRepository.save(prescription);

        return mapToDto(prescription);
    }

    @Override
    public void deletePrescription(Long id) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found with id: " + id));

        prescriptionRepository.delete(prescription);
    }

    private PrescriptionDto mapToDto(Prescription p) {

        PrescriptionDto dto = new PrescriptionDto();
        dto.setPrescriptionId(p.getPrescriptionId());
        dto.setConsultationId(p.getConsultation().getConsultationId());
        dto.setMedicineName(p.getMedicineName());
        dto.setDosage(p.getDosage());
        dto.setDuration(p.getDuration());
        dto.setDurationDays(p.getDurationDays());

        return dto;
    }
}