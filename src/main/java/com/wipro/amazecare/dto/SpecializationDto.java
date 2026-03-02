package com.wipro.amazecare.dto;

import jakarta.validation.constraints.NotBlank;

public class SpecializationDto {

    private Long specializationId;

    @NotBlank(message = "Specialization name is required")
    private String specializationName;

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