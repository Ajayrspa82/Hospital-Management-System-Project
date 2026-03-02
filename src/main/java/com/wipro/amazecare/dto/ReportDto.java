package com.wipro.amazecare.dto;

import java.time.Month;
import jakarta.validation.constraints.NotNull;

public class ReportDto {

    @NotNull(message = "Total consultations cannot be null")
    private Long totalConsultations;

    @NotNull(message = "Total prescriptions cannot be null")
    private Long totalPrescriptions;

    @NotNull(message = "Total medical records cannot be null")
    private Long totalMedicalRecords;

    @NotNull(message = "Total tests cannot be null")
    private Long totalTests;

    @NotNull(message = "Total patients cannot be null")
    private Long totalPatients;

    @NotNull(message = "Doctor consultations cannot be null")
    private Long doctorConsultations;

    private Month month; 

    public Long getTotalConsultations() {
        return totalConsultations;
    }

    public void setTotalConsultations(Long totalConsultations) {
        this.totalConsultations = totalConsultations;
    }

    public Long getTotalPrescriptions() {
        return totalPrescriptions;
    }

    public void setTotalPrescriptions(Long totalPrescriptions) {
        this.totalPrescriptions = totalPrescriptions;
    }

    public Long getTotalMedicalRecords() {
        return totalMedicalRecords;
    }

    public void setTotalMedicalRecords(Long totalMedicalRecords) {
        this.totalMedicalRecords = totalMedicalRecords;
    }

    public Long getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(Long totalTests) {
        this.totalTests = totalTests;
    }

    public Long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(Long totalPatients) {
        this.totalPatients = totalPatients;
    }

    public Long getDoctorConsultations() {
        return doctorConsultations;
    }

    public void setDoctorConsultations(Long doctorConsultations) {
        this.doctorConsultations = doctorConsultations;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}