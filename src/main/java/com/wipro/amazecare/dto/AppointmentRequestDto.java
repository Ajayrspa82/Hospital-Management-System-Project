package com.wipro.amazecare.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;

public class AppointmentRequestDto {

    @NotNull(message = "Patient ID is required")
    @Positive(message = "Patient ID must be a positive number")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    @Positive(message = "Doctor ID must be a positive number")
    private Long doctorId;

    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;

    @Size(max = 250, message = "Reason must be less than 250 characters")
    private String reason;

    // Getters and Setters
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}