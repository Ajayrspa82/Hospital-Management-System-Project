package com.wipro.amazecare.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DoctorDto {

    private Long doctorId;

    @NotBlank(message = "Doctor name is required")
    private String name;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @Min(value = 0, message = "Experience cannot be negative")
    private int experience;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotNull(message = "Specialization ID is required")
    private Long specializationId;

    private String specializationName;

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Long getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(Long specializationId) {
		this.specializationId = specializationId;
	}

	public String getSpecializationName() {
		return specializationName;
	}

	public void setSpecializationName(String specializationName) {
		this.specializationName = specializationName;
	}

 
}