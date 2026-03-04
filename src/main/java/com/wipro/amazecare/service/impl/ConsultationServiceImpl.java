package com.wipro.amazecare.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.ConsultationDto;
import com.wipro.amazecare.dto.MedicalTestDto;
import com.wipro.amazecare.dto.PrescriptionDto;
import com.wipro.amazecare.entity.Appointment;
import com.wipro.amazecare.entity.Consultation;
import com.wipro.amazecare.entity.Doctor;
import com.wipro.amazecare.entity.MedicalTest;
import com.wipro.amazecare.entity.Patient;
import com.wipro.amazecare.entity.Prescription;
import com.wipro.amazecare.exception.BadRequestException;
import com.wipro.amazecare.exception.ResourceNotFoundException;
import com.wipro.amazecare.repository.AppointmentRepository;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.repository.DoctorRepository;
import com.wipro.amazecare.repository.PatientRepository;
import com.wipro.amazecare.service.ConsultationService;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private ConsultationRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // ================= CREATE =================
    @Override
    public ConsultationDto createConsultation(ConsultationDto dto) {

        if (dto.getSymptoms() == null || dto.getSymptoms().isBlank()) {
            throw new BadRequestException("Symptoms cannot be empty");
        }

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));

        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setSymptoms(dto.getSymptoms());
        consultation.setDiagnosis(dto.getDiagnosis());
        consultation.setPhysicalExamination(dto.getPhysicalExamination());
        consultation.setTreatmentPlan(dto.getTreatmentPlan());
        consultation.setDoctorNotes(dto.getDoctorNotes());
        consultation.setConsultationDate(dto.getConsultationDate());

        // Appointment
        if (dto.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Appointment not found with id: " + dto.getAppointmentId()));
            consultation.setAppointment(appointment);
        }

        // ================= PRESCRIPTIONS =================
        if (dto.getPrescriptions() != null && !dto.getPrescriptions().isEmpty()) {

            List<Prescription> prescriptionList = dto.getPrescriptions().stream().map(pDto -> {
                Prescription prescription = new Prescription();
                prescription.setMedicineName(pDto.getMedicineName());
                prescription.setDosage(pDto.getDosage());
                prescription.setDuration(pDto.getDuration());
                prescription.setInstructions(pDto.getInstructions());
                prescription.setConsultation(consultation);
                return prescription;
            }).collect(Collectors.toList());

            consultation.setPrescriptions(prescriptionList);
        }

        // ================= MEDICAL TESTS =================
        if (dto.getRecommendedTests() != null && !dto.getRecommendedTests().isEmpty()) {

            List<MedicalTest> testList = dto.getRecommendedTests().stream().map(tDto -> {

                if (tDto.getTestStatus() == null || tDto.getTestStatus().isBlank()) {
                    throw new BadRequestException("Test status cannot be null or blank");
                }

                MedicalTest test = new MedicalTest();
                test.setTestName(tDto.getTestName());
                test.setDescription(tDto.getDescription());
                test.setResult(tDto.getResult());
                test.setTestStatus(tDto.getTestStatus());
                test.setConsultation(consultation);

                return test;

            }).collect(Collectors.toList());

            consultation.setRecommendedTests(testList);
        }

        repository.save(consultation);

        return mapToDto(consultation);
    }

    // ================= GET BY ID =================
    @Override
    public ConsultationDto getConsultationById(Long id) {

        Consultation consultation = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Consultation not found with id: " + id));

        return mapToDto(consultation);
    }

    // ================= GET ALL =================
    @Override
    public List<ConsultationDto> getAllConsultations() {

        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ================= GET BY PATIENT =================
    @Override
    public List<ConsultationDto> getByPatient(Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }

        return repository.findByPatient_PatientId(patientId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ================= GET BY DOCTOR =================
    @Override
    public List<ConsultationDto> getByDoctor(Long doctorId) {

        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }

        return repository.findByDoctor_DoctorId(doctorId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ================= UPDATE =================
    @Override
    public ConsultationDto updateConsultation(Long id, ConsultationDto dto) {

        Consultation consultation = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Consultation not found with id: " + id));

        if (dto.getSymptoms() != null && !dto.getSymptoms().isBlank())
            consultation.setSymptoms(dto.getSymptoms());

        if (dto.getDiagnosis() != null)
            consultation.setDiagnosis(dto.getDiagnosis());

        if (dto.getPhysicalExamination() != null)
            consultation.setPhysicalExamination(dto.getPhysicalExamination());

        if (dto.getTreatmentPlan() != null)
            consultation.setTreatmentPlan(dto.getTreatmentPlan());

        if (dto.getDoctorNotes() != null)
            consultation.setDoctorNotes(dto.getDoctorNotes());

        if (dto.getConsultationDate() != null)
            consultation.setConsultationDate(dto.getConsultationDate());

        repository.save(consultation);

        return mapToDto(consultation);
    }

    // ================= DELETE =================
    @Override
    public void deleteConsultation(Long id) {

        Consultation consultation = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Consultation not found with id: " + id));

        repository.delete(consultation);
    }

    // ================= DTO MAPPING =================
    private ConsultationDto mapToDto(Consultation c) {

        ConsultationDto dto = new ConsultationDto();

        dto.setConsultationId(c.getConsultationId());
        dto.setPatientId(c.getPatient().getPatientId());
        dto.setDoctorId(c.getDoctor().getDoctorId());
        dto.setSymptoms(c.getSymptoms());
        dto.setDiagnosis(c.getDiagnosis());
        dto.setPhysicalExamination(c.getPhysicalExamination());
        dto.setTreatmentPlan(c.getTreatmentPlan());
        dto.setDoctorNotes(c.getDoctorNotes());
        dto.setConsultationDate(c.getConsultationDate());

        if (c.getAppointment() != null)
            dto.setAppointmentId(c.getAppointment().getAppointmentId());

        // ===== PRESCRIPTION DTO WITH IDs =====
        if (c.getPrescriptions() != null) {
            List<PrescriptionDto> prescriptionDtos = c.getPrescriptions().stream().map(p -> {
                PrescriptionDto pDto = new PrescriptionDto();
                pDto.setPrescriptionId(p.getPrescriptionId());
                pDto.setConsultationId(c.getConsultationId());
                pDto.setMedicineName(p.getMedicineName());
                pDto.setDosage(p.getDosage());
                pDto.setDuration(p.getDuration());
                pDto.setInstructions(p.getInstructions());
                return pDto;
            }).collect(Collectors.toList());

            dto.setPrescriptions(prescriptionDtos);
        }

        // ===== MEDICAL TEST DTO WITH IDs =====
        if (c.getRecommendedTests() != null) {
            List<MedicalTestDto> testDtos = c.getRecommendedTests().stream().map(t -> {
                MedicalTestDto tDto = new MedicalTestDto();
                tDto.setTestId(t.getTestId());
                tDto.setConsultationId(c.getConsultationId());
                tDto.setTestName(t.getTestName());
                tDto.setTestStatus(t.getTestStatus());
                tDto.setDescription(t.getDescription());
                tDto.setResult(t.getResult());
                return tDto;
            }).collect(Collectors.toList());

            dto.setRecommendedTests(testDtos);
        }

        return dto;
    }
}