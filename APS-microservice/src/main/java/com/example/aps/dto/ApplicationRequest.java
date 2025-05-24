package com.example.aps.dto;

import jakarta.validation.constraints.NotNull;

public class ApplicationRequest {

    @NotNull(message = "Applicant name is required")
    private String applicantName;

    @NotNull(message = "Monthly income is required")
    private Double monthlyIncome;

    @NotNull(message = "Monthly expense is required")
    private Double monthlyExpense;


	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Double getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(Double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public Double getMonthlyExpense() {
		return monthlyExpense;
	}

	public void setMonthlyExpense(Double monthlyExpense) {
		this.monthlyExpense = monthlyExpense;
	}

    
}
