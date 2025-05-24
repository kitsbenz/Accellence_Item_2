package com.example.aps.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "applications")
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicantName;
    private Double monthlyIncome;
    private Double monthlyExpense;

    private Double repaymentCapacity;

    private String financialStatus; // SUFFICIENT / INSUFFICIENT
    private String creditCheckStatus; // VERIFIED / REJECTED / PENDING

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
