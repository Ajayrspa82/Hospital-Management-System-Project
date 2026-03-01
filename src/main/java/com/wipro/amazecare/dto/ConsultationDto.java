package com.wipro.amazecare.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ConsultationDto {

    private Long consultationId;

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;

    private String symptoms;
    private String diagnosis;
    private String physicalExamination;
    private String treatmentPlan;
    private String doctorNotes;

    private LocalDateTime consultationDate;

    // List of prescriptions (if multiple medicines are prescribed)
    private List<PrescriptionDto> prescriptions;

    // List of recommended medical tests
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