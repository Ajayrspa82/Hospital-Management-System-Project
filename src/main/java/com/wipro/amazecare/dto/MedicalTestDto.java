package com.wipro.amazecare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MedicalTestDto {

    private Long testId;

    @NotNull(message = "Consultation ID is required")
    private Long consultationId;

    @NotBlank(message = "Test name cannot be empty")
    @Size(max = 200, message = "Test name must be less than 200 characters")
    private String testName;

    @NotBlank(message = "Test status is required")
    @Size(max = 50, message = "Test status must be less than 50 characters")
    private String testStatus;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @Size(max = 1000, message = "Result must be less than 1000 characters")
    private String result;

    // -------------------- GETTERS & SETTERS --------------------
    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}