package com.example.aps.dto;

public class ApplicationResponse {
    private Long id;
    private String creditCheckStatus;
    private Double repaymentCapacity;
    private String repaymentCapacityStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditCheckStatus() {
        return creditCheckStatus;
    }

    public void setCreditCheckStatus(String creditCheckStatus) {
        this.creditCheckStatus = creditCheckStatus;
    }

    public Double getRepaymentCapacity() {
        return repaymentCapacity;
    }

    public void setRepaymentCapacity(Double repaymentCapacity) {
        this.repaymentCapacity = repaymentCapacity;
    }

    public String getRepaymentCapacityStatus() {
        return repaymentCapacityStatus;
    }

    public void setRepaymentCapacityStatus(String repaymentCapacityStatus) {
        this.repaymentCapacityStatus = repaymentCapacityStatus;
    }
}
