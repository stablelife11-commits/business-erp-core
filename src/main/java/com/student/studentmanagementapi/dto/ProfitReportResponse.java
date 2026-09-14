package com.student.studentmanagementapi.dto;

import java.math.BigDecimal;

public class ProfitReportResponse {

    private BigDecimal totalSales;
    private BigDecimal totalPurchaseCost;
    private BigDecimal grossProfit;
    private Double profitPercentage;

    public ProfitReportResponse() {
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalPurchaseCost() {
        return totalPurchaseCost;
    }

    public void setTotalPurchaseCost(BigDecimal totalPurchaseCost) {
        this.totalPurchaseCost = totalPurchaseCost;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(Double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }
}