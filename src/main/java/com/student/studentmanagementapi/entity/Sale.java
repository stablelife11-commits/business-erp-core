package com.student.studentmanagementapi.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String saleNumber;

    private LocalDate saleDate;

    private String customerName;

    @Column(length = 10)
    private String customerMobile;

    private BigDecimal totalAmount;

    private String paymentMode;

    private String remarks;

    private LocalDateTime createdAt;


    // ==========================================
    // MULTIPLE SALE ITEMS
    // ==========================================

    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SaleItem> items = new ArrayList<>();


    // ==========================================
    // PRE PERSIST
    // ==========================================

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if (saleDate == null) {
            saleDate = LocalDate.now();
        }
    }


    // ==========================================
    // ADD ITEM
    // ==========================================

    public void addItem(SaleItem item) {

        items.add(item);
        item.setSale(this);
    }


    // ==========================================
    // GETTERS & SETTERS
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(String saleNumber) {
        this.saleNumber = saleNumber;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    // ==========================================
    // SALE ITEMS GETTER / SETTER
    // ==========================================

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {

        this.items = items;

        if (items != null) {

            for (SaleItem item : items) {
                item.setSale(this);
            }
        }
    }
}