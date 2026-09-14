package com.student.studentmanagementapi.dto;

import java.math.BigDecimal;

public class DashboardResponse {

    private long totalCustomers;
    private long totalSuppliers;
    private long totalProducts;
    private long totalPurchases;
    private long totalSales;


    private BigDecimal totalPurchaseAmount;
    private BigDecimal totalSaleAmount;

    private Integer totalStockQuantity;
    private BigDecimal totalStockValue;

    private BigDecimal todaySaleAmount;
    private BigDecimal todayPurchaseAmount;

    private long todaySalesCount;
    private long todayPurchasesCount;

    private long lowStockProducts;

    public DashboardResponse() {
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalSuppliers() {
        return totalSuppliers;
    }

    public void setTotalSuppliers(long totalSuppliers) {
        this.totalSuppliers = totalSuppliers;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(long totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(long totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public void setTotalPurchaseAmount(BigDecimal totalPurchaseAmount) {
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    public BigDecimal getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(BigDecimal totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }

    public BigDecimal getTodaySaleAmount() {
        return todaySaleAmount;
    }

    public void setTodaySaleAmount(BigDecimal todaySaleAmount) {
        this.todaySaleAmount = todaySaleAmount;
    }

    public BigDecimal getTodayPurchaseAmount() {
        return todayPurchaseAmount;
    }

    public void setTodayPurchaseAmount(BigDecimal todayPurchaseAmount) {
        this.todayPurchaseAmount = todayPurchaseAmount;
    }

    public long getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(long lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }

    public long getTodaySalesCount() {
        return todaySalesCount;
    }

    public void setTodaySalesCount(long todaySalesCount) {
        this.todaySalesCount = todaySalesCount;
    }

    public long getTodayPurchasesCount() {
        return todayPurchasesCount;
    }

    public void setTodayPurchasesCount(long todayPurchasesCount) {
        this.todayPurchasesCount = todayPurchasesCount;
    }
    public Integer getTotalStockQuantity() {
    return totalStockQuantity;
}

public void setTotalStockQuantity(Integer totalStockQuantity) {
    this.totalStockQuantity = totalStockQuantity;
}

public BigDecimal getTotalStockValue() {
    return totalStockValue;
}

public void setTotalStockValue(BigDecimal totalStockValue) {
    this.totalStockValue = totalStockValue;
}
}