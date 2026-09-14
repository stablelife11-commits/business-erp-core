package com.student.studentmanagementapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "customer_payments")
public class CustomerPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ==========================================
    // CUSTOMER
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    @NotNull(message = "Customer is required")
    private Customer customer;


    // ==========================================
    // SALE - OPTIONAL
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;


    // ==========================================
    // PAYMENT DATE
    // ==========================================

    @Column(nullable = false)
    private LocalDate paymentDate;


    // ==========================================
    // PAYMENT AMOUNT
    // ==========================================

    @NotNull(message = "Payment amount is required")
    @DecimalMin(
            value = "0.01",
            message = "Payment amount must be greater than zero"
    )
    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal amount;


    // ==========================================
    // PAYMENT MODE
    // ==========================================

    @Column(length = 30)
    private String paymentMode;


    // ==========================================
    // REFERENCE NUMBER
    // ==========================================

    @Column(length = 100)
    private String referenceNumber;


    // ==========================================
    // REMARKS
    // ==========================================

    @Column(length = 255)
    private String remarks;


    // ==========================================
    // CREATED AT
    // ==========================================

    @Column(nullable = false)
    private LocalDateTime createdAt;


    // ==========================================
    // PRE PERSIST
    // ==========================================

    @PrePersist
    public void prePersist() {

        if (paymentDate == null) {
            paymentDate = LocalDate.now();
        }

        createdAt = LocalDateTime.now();
    }
}