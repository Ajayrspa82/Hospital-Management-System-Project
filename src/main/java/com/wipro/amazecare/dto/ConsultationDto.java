package com.wipro.amazecare.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ConsultationDto {

    private Long consultationId;

    private Long appointmentId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotBlank(message = "Symptoms cannot be empty")
    @Size(max = 1000, message = "Symptoms must be less than 1000 characters")
    private String symptoms;

    @Size(max = 1000, message = "Diagnosis must be less than 1000 characters")
    private String diagnosis;

    @Size(max = 1000, message = "Physical examination details must be less than 1000 characters")
    private String physicalExamination;

    @Size(max = 2000, message = "Treatment plan must be less than 2000 characters")
    private String treatmentPlan;

    @Size(max = 2000, message = "Doctor notes must be less than 2000 characters")
    private String doctorNotes;

    @NotNull(message = "Consultation date is required")
    private LocalDateTime consultationDate;

    private List<PrescriptionDto> prescriptions;

    private List<MedicalTestDto> recommendedTests;

    public ConsultationDto() {
    }


    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    public LocalDateTime getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(LocalDateTime consultationDate) {
        this.consultationDate = consultationDate;
    }

    public List<PrescriptionDto> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionDto> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<MedicalTestDto> getRecommendedTests() {
        return recommendedTests;
    }

    public void setRecommendedTests(List<MedicalTestDto> recommendedTests) {
        this.recommendedTests = recommendedTests;
    }
}