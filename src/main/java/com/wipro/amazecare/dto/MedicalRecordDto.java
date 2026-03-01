package com.wipro.amazecare.dto;

public class MedicalRecordDto {

    private Long recordId;
    private Long patientId;
    private Long consultationId;
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
