package com.wipro.amazecare.dto;




import java.time.LocalDateTime;

public class AppointmentUpdateDto {
    private LocalDateTime newDate;

    public LocalDateTime getNewDate() { return newDate; }
    public void setNewDate(LocalDateTime newDate) { this.newDate = newDate; }
}