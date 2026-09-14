package com.student.studentmanagementapi.dto;

import com.student.studentmanagementapi.entity.Purchase;

import java.util.List;
import com.student.studentmanagementapi.dto.PurchaseResponse;

public class PurchaseReportResponse {

    private long totalBills;
    private int totalQuantity;
    private Double totalPurchase;
    private List<PurchaseResponse> purchases;

    public PurchaseReportResponse() {
    }

    public long getTotalBills() {
        return totalBills;
    }

    public void setTotalBills(long totalBills) {
        this.totalBills = totalBills;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(Double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public List<PurchaseResponse> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseResponse> purchases) {
        this.purchases = purchases;
    }
}