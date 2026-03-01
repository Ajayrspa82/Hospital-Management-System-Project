package com.wipro.amazecare.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @OneToOne
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @Column(length = 2000)
    private String notes;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

    // Getters and Setters
    
    
}