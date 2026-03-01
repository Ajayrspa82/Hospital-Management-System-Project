package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

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

    private final ConsultationRepository repository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    // Constructor injection
    public ConsultationServiceImpl(ConsultationRepository repository,
                                   PatientRepository patientRepository,
                                   DoctorRepository doctorRepository) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public ConsultationDto createConsultation(ConsultationDto dto) {

        // Validate input
        if(dto.getSymptoms() == null || dto.getSymptoms().isEmpty()) {
            throw new BadRequestException("Symptoms cannot be empty");
        }

        // Fetch Patient and Doctor
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + dto.getPatientId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + dto.getDoctorId()));

        // Create Consultation
        Consultation consultation = new Consultation();
        consultation.setPatient(patient);  
        consultation.setDoctor(doctor);    
        consultation.setSymptoms(dto.getSymptoms());
        consultation.setPhysicalExamination(dto.getPhysicalExamination());
        consultation.setTreatmentPlan(dto.getTreatmentPlan());
        consultation.setConsultationDate(dto.getConsultationDate());

        repository.save(consultation);

        dto.setConsultationId(consultation.getConsultationId());
        return dto;
    }

    @Override
    public ConsultationDto getConsultationById(Long id) {

        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consultation not found with id: " + id));

        ConsultationDto dto = new ConsultationDto();
        dto.setConsultationId(consultation.getConsultationId());
        dto.setPatientId(consultation.getPatient().getPatientId());
        dto.setDoctorId(consultation.getDoctor().getDoctorId());
        dto.setSymptoms(consultation.getSymptoms());
        dto.setPhysicalExamination(consultation.getPhysicalExamination());
        dto.setTreatmentPlan(consultation.getTreatmentPlan());
        dto.setConsultationDate(consultation.getConsultationDate());

        return dto;
    }

    @Override
    public List<ConsultationDto> getAllConsultations() {
        return repository.findAll().stream()
                .map(c -> getConsultationById(c.getConsultationId()))
                .collect(Collectors.toList());
    }

    @Override
    public ConsultationDto updateConsultation(Long id, ConsultationDto dto) {

        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consultation not found with id: " + id));

        if(dto.getSymptoms() != null && !dto.getSymptoms().isEmpty()) {
            consultation.setSymptoms(dto.getSymptoms());
        }

        if(dto.getPhysicalExamination() != null) {
            consultation.setPhysicalExamination(dto.getPhysicalExamination());
        }

        if(dto.getTreatmentPlan() != null) {
            consultation.setTreatmentPlan(dto.getTreatmentPlan());
        }

        if(dto.getConsultationDate() != null) {
            consultation.setConsultationDate(dto.getConsultationDate());
        }

        repository.save(consultation);

        // Update DTO to reflect saved entity
        dto.setConsultationId(consultation.getConsultationId());
        dto.setPatientId(consultation.getPatient().getPatientId());
        dto.setDoctorId(consultation.getDoctor().getDoctorId());

        return dto;
    }

    @Override
    public void deleteConsultation(Long id) {

        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consultation not found with id: " + id));

        repository.delete(consultation);
    }
}