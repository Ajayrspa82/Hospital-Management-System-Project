package com.wipro.amazecare.dto;


import java.time.LocalDate;

public class AppointmentUpdateDto {

    private LocalDate newDate;
    private String status;

    public LocalDate getNewDate() { return newDate; }
    public void setNewDate(LocalDate newDate) { this.newDate = newDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}