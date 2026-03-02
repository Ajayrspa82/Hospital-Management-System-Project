package com.wipro.amazecare.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;

public class AppointmentUpdateDto {

    @NotNull(message = "Appointment ID is required")
    @Positive(message = "Appointment ID must be a positive number")
    private Long appointmentId;

    @Future(message = "New appointment date must be in the future")
    private LocalDateTime newDate;

    @Size(max = 250, message = "Reason must be less than 250 characters")
    private String reason;

    private String status; // optional: "RESCHEDULED", "CANCELLED", etc.

    // Getters and Setters
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }

    public LocalDateTime getNewDate() { return newDate; }
    public void setNewDate(LocalDateTime newDate) { this.newDate = newDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}