package com.wipro.amazecare.dto;

public class ReportDto {

    private Long totalConsultations;
    private Long totalPrescriptions;
    private Long totalMedicalRecords;
    private Long totalTests;
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

    
}