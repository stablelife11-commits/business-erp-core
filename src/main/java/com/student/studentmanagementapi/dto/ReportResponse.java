package com.student.studentmanagementapi.dto;

import com.student.studentmanagementapi.entity.Sale;

import java.math.BigDecimal;
import java.util.List;

public class ReportResponse {

    private long totalBills;

    private int totalQuantity;

    private BigDecimal totalSales;

    private List<Sale> sales;

    public ReportResponse() {
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

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}