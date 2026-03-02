package com.wipro.amazecare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MedicalRecordDto {

    private Long recordId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Consultation ID is required")
    private Long consultationId;

    @NotBlank(message = "Notes cannot be empty")
    @Size(max = 2000, message = "Notes must be less than 2000 characters")
    private String notes;


    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}