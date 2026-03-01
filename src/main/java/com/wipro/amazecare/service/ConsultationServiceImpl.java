 package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.ConsultationDto;
import com.wipro.amazecare.entity.Consultation;
import com.wipro.amazecare.entity.Doctor;
import com.wipro.amazecare.entity.Patient;
import com.wipro.amazecare.exception.BadRequestException;
import com.wipro.amazecare.exception.ResourceNotFoundException;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.repository.DoctorRepository;
import com.wipro.amazecare.repository.PatientRepository;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private ConsultationRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public ConsultationDto createConsultation(ConsultationDto dto) {

        if (dto.getSymptoms() == null || dto.getSymptoms().isBlank()) {
            throw new BadRequestException("Symptoms cannot be empty");
        }

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + dto.getPatientId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + dto.getDoctorId()));

        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setSymptoms(dto.getSymptoms());
        consultation.setPhysicalExamination(dto.getPhysicalExamination());
        consultation.setTreatmentPlan(dto.getTreatmentPlan());
        consultation.setConsultationDate(dto.getConsultationDate());

        repository.save(consultation);

        return mapToDto(consultation);
    }

    @Override
    public ConsultationDto getConsultationById(Long id) {

        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consultation not found with id: " + id));

        return mapToDto(consultation);
    }

    @Override
    public List<ConsultationDto> getAllConsultations() {

        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultationDto updateConsultation(Long id, ConsultationDto dto) {

        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consultation not found with id: " + id));

        if (dto.getSymptoms() != null && !dto.getSymptoms().isBlank()) {
            consultation.setSymptoms(dto.getSymptoms());
        }

        if (dto.getPhysicalExamination() != null) {
            consultation.setPhysicalExamination(dto.getPhysicalExamination());
        }

        if (dto.getTreatmentPlan() != null) {
            consultation.setTreatmentPlan(dto.getTreatmentPlan());
        }

        if (dto.getConsultationDate() != null) {
            consultation.setConsultationDate(dto.getConsultationDate());
        }

        repository.save(consultation);

        return mapToDto(consultation);
    }

    @Override
    public void deleteConsultation(Long id) {

        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consultation not found with id: " + id));

        repository.delete(consultation);
    }

    private ConsultationDto mapToDto(Consultation c) {

        ConsultationDto dto = new ConsultationDto();
        dto.setConsultationId(c.getConsultationId());
        dto.setPatientId(c.getPatient().getPatientId());
        dto.setDoctorId(c.getDoctor().getDoctorId());
        dto.setSymptoms(c.getSymptoms());
        dto.setPhysicalExamination(c.getPhysicalExamination());
        dto.setTreatmentPlan(c.getTreatmentPlan());
        dto.setConsultationDate(c.getConsultationDate());

        return dto;
    }
}