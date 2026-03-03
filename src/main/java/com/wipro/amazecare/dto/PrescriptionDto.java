package com.wipro.amazecare.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PrescriptionDto {

    private Long prescriptionId;

    @NotNull(message = "Consultation ID is required")
    private Long consultationId;

    @NotBlank(message = "Medicine name is required")
    @Size(max = 200, message = "Medicine name must be less than 200 characters")
    private String medicineName;

    @NotBlank(message = "Dosage is required")
    @Size(max = 100, message = "Dosage must be less than 100 characters")
    private String dosage;

    @Min(value = 1, message = "Duration must be at least 1 day")
    private int durationDays;
    
    private String duration;

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }
}