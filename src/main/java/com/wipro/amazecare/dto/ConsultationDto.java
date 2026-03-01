package com.wipro.amazecare.dto;

import java.time.LocalDate;

public class ConsultationDto {

    private Long consultationId;
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private String symptoms;
    private String physicalExamination;
    private String treatmentPlan;
    private LocalDate consultationDate;
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
	public LocalDate getConsultationDate() {
		return consultationDate;
	}
	public void setConsultationDate(LocalDate consultationDate) {
		this.consultationDate = consultationDate;
	}

    
    
}