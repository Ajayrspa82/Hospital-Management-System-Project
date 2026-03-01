package com.wipro.amazecare.dto;

public class MedicalTestDto {

    private Long testId;
    private Long consultationId;
    private String testName;
    private String description;
    private String result;
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