package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.dto.CustomerReportResponse;
import com.student.studentmanagementapi.dto.MonthlyPurchaseResponse;
import com.student.studentmanagementapi.dto.MonthlySalesResponse;
import com.student.studentmanagementapi.dto.ProfitReportResponse;
import com.student.studentmanagementapi.dto.PurchaseItemResponse;
import com.student.studentmanagementapi.dto.PurchaseReportResponse;
import com.student.studentmanagementapi.dto.PurchaseResponse;
import com.student.studentmanagementapi.dto.ReportResponse;
import com.student.studentmanagementapi.dto.StockReportResponse;
import com.student.studentmanagementapi.dto.SupplierReportResponse;
import com.student.studentmanagementapi.dto.TopCustomerResponse;
import com.student.studentmanagementapi.dto.TopSellingProductResponse;
import com.student.studentmanagementapi.dto.TopSupplierResponse;

import com.student.studentmanagementapi.entity.Purchase;
import com.student.studentmanagementapi.entity.PurchaseItem;
import com.student.studentmanagementapi.entity.Sale;

import com.student.studentmanagementapi.repository.ProductRepository;
import com.student.studentmanagementapi.repository.PurchaseRepository;
import com.student.studentmanagementapi.repository.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;


    // =========================================================
    // SALES REPORT
    // =========================================================

    public ReportResponse getSalesReport(
            LocalDate fromDate,
            LocalDate toDate) {

        List<Sale> sales =
                saleRepository.findBySaleDateBetween(
                        fromDate,
                        toDate
                );

        ReportResponse response = new ReportResponse();

        response.setSales(sales);

        response.setTotalBills(sales.size());

        int totalQuantity = 0;

        BigDecimal totalSales = BigDecimal.ZERO;

        for (Sale sale : sales) {

            // Multiple Sale Items
            if (sale.getItems() != null) {

                for (var item : sale.getItems()) {

                    if (item.getQuantity() != null) {

                        totalQuantity += item.getQuantity();
                    }
                }
            }

            if (sale.getTotalAmount() != null) {

                totalSales =
                        totalSales.add(
                                sale.getTotalAmount()
                        );
            }
        }

        response.setTotalQuantity(totalQuantity);

        response.setTotalSales(totalSales);

        return response;
    }


    // =========================================================
    // PURCHASE REPORT
    // =========================================================

    public PurchaseReportResponse getPurchaseReport(
            LocalDate fromDate,
            LocalDate toDate) {

        List<Purchase> purchases =
                purchaseRepository.findByPurchaseDateBetween(
                        fromDate,
                        toDate
                );

        PurchaseReportResponse response =
                new PurchaseReportResponse();

        List<PurchaseResponse> purchaseResponses =
                new ArrayList<>();


        for (Purchase purchase : purchases) {

            PurchaseResponse purchaseResponse =
                    new PurchaseResponse();

            purchaseResponse.setId(
                    purchase.getId()
            );

            purchaseResponse.setPurchaseNumber(
                    purchase.getPurchaseNumber()
            );

            purchaseResponse.setPurchaseDate(
                    purchase.getPurchaseDate()
            );

            purchaseResponse.setSupplierName(
                    purchase.getSupplier()
                            .getSupplierName()
            );

            purchaseResponse.setInvoiceNumber(
                    purchase.getInvoiceNumber()
            );

            purchaseResponse.setTotalAmount(
                    purchase.getTotalAmount()
            );

            purchaseResponse.setStatus(
                    purchase.getStatus()
            );


            List<PurchaseItemResponse> itemResponses =
                    new ArrayList<>();


            for (PurchaseItem item : purchase.getItems()) {

                PurchaseItemResponse itemResponse =
                        new PurchaseItemResponse();

                itemResponse.setProductName(
                        item.getProduct()
                                .getProductName()
                );

                itemResponse.setQuantity(
                        item.getQuantity()
                );

                itemResponse.setPurchasePrice(
                        item.getPurchasePrice()
                );

                itemResponse.setTotalPrice(
                        item.getTotalPrice()
                );

                itemResponses.add(itemResponse);
            }


            purchaseResponse.setItems(
                    itemResponses
            );

            purchaseResponses.add(
                    purchaseResponse
            );
        }


        response.setPurchases(
                purchaseResponses
        );

        response.setTotalBills(
                purchases.size()
        );


        int totalQuantity = 0;

        Double totalPurchase = 0.0;


        for (Purchase purchase : purchases) {

            if (purchase.getTotalAmount() != null) {

                totalPurchase +=
                        purchase.getTotalAmount();
            }


            if (purchase.getItems() != null) {

                for (PurchaseItem item :
                        purchase.getItems()) {

                    if (item.getQuantity() != null) {

                        totalQuantity +=
                                item.getQuantity();
                    }
                }
            }
        }


        response.setTotalQuantity(
                totalQuantity
        );

        response.setTotalPurchase(
                totalPurchase
        );

        return response;
    }


    // =========================================================
    // PROFIT REPORT
    // =========================================================

    public ProfitReportResponse getProfitReport(
            LocalDate fromDate,
            LocalDate toDate) {

        List<Sale> sales =
                saleRepository.findBySaleDateBetween(
                        fromDate,
                        toDate
                );

        List<Purchase> purchases =
                purchaseRepository.findByPurchaseDateBetween(
                        fromDate,
                        toDate
                );


        BigDecimal totalSales =
                BigDecimal.ZERO;

        BigDecimal totalPurchaseCost =
                BigDecimal.ZERO;


        // Sales
        for (Sale sale : sales) {

            if (sale.getTotalAmount() != null) {

                totalSales =
                        totalSales.add(
                                sale.getTotalAmount()
                        );
            }
        }


        // Purchases
        for (Purchase purchase : purchases) {

            if (purchase.getTotalAmount() != null) {

                totalPurchaseCost =
                        totalPurchaseCost.add(
                                BigDecimal.valueOf(
                                        purchase.getTotalAmount()
                                )
                        );
            }
        }


        BigDecimal grossProfit =
                totalSales.subtract(
                        totalPurchaseCost
                );


        Double profitPercentage = 0.0;


        if (totalPurchaseCost.compareTo(
                BigDecimal.ZERO) > 0) {

            profitPercentage =
                    grossProfit
                            .multiply(
                                    BigDecimal.valueOf(100)
                            )
                            .divide(
                                    totalPurchaseCost,
                                    2,
                                    RoundingMode.HALF_UP
                            )
                            .doubleValue();
        }


        ProfitReportResponse response =
                new ProfitReportResponse();

        response.setTotalSales(
                totalSales
        );

        response.setTotalPurchaseCost(
                totalPurchaseCost
        );

        response.setGrossProfit(
                grossProfit
        );

        response.setProfitPercentage(
                profitPercentage
        );

        return response;
    }


    // =========================================================
    // STOCK REPORT
    // =========================================================

    public StockReportResponse getStockReport() {

        StockReportResponse response =
                new StockReportResponse();


        response.setTotalProducts(
                productRepository.count()
        );


        response.setTotalStockQuantity(
                productRepository
                        .getTotalStockQuantity()
        );


        response.setTotalStockValue(
                productRepository
                        .getTotalStockValue()
        );


        return response;
    }


    // =========================================================
    // MONTHLY SALES
    // =========================================================

    public List<MonthlySalesResponse>
    getMonthlySalesReport() {

        List<Object[]> data =
                saleRepository.getMonthlySales();

        List<MonthlySalesResponse> response =
                new ArrayList<>();


        for (Object[] row : data) {

            MonthlySalesResponse item =
                    new MonthlySalesResponse();

            item.setMonth(
                    ((Number) row[0]).intValue()
            );

            item.setTotalSales(
                    (BigDecimal) row[1]
            );

            response.add(item);
        }


        return response;
    }


    // =========================================================
    // TOP SELLING PRODUCTS
    // =========================================================

    public List<TopSellingProductResponse>
    getTopSellingProducts() {

        List<Object[]> data =
                saleRepository.getTopSellingProducts();

        List<TopSellingProductResponse> response =
                new ArrayList<>();


        for (Object[] row : data) {

            TopSellingProductResponse item =
                    new TopSellingProductResponse();

            item.setProductName(
                    (String) row[0]
            );

            item.setTotalQuantity(
                    ((Number) row[1]).longValue()
            );

            response.add(item);
        }


        return response;
    }


    // =========================================================
    // TOP CUSTOMERS
    // =========================================================

    public List<TopCustomerResponse>
    getTopCustomers() {

        List<Object[]> data =
                saleRepository.getTopCustomers();

        List<TopCustomerResponse> response =
                new ArrayList<>();


        for (Object[] row : data) {

            TopCustomerResponse item =
                    new TopCustomerResponse();

            item.setCustomerName(
                    (String) row[0]
            );

            item.setTotalAmount(
                    (BigDecimal) row[1]
            );

            response.add(item);
        }


        return response;
    }


    // =========================================================
    // MONTHLY PURCHASE
    // =========================================================

    public List<MonthlyPurchaseResponse>
    getMonthlyPurchaseReport() {

        List<Object[]> data =
                purchaseRepository.getMonthlyPurchases();

        List<MonthlyPurchaseResponse> response =
                new ArrayList<>();


        for (Object[] row : data) {

            MonthlyPurchaseResponse item =
                    new MonthlyPurchaseResponse();

            item.setMonth(
                    ((Number) row[0]).intValue()
            );

            item.setTotalPurchase(
                    ((Number) row[1]).doubleValue()
            );

            response.add(item);
        }


        return response;
    }


    // =========================================================
    // TOP SUPPLIERS
    // =========================================================

    public List<TopSupplierResponse>
    getTopSuppliers() {

        List<Object[]> data =
                purchaseRepository.getTopSuppliers();

        List<TopSupplierResponse> response =
                new ArrayList<>();


        for (Object[] row : data) {

            TopSupplierResponse item =
                    new TopSupplierResponse();

            item.setSupplierName(
                    (String) row[0]
            );

            item.setTotalPurchase(
                    ((Number) row[1]).doubleValue()
            );

            response.add(item);
        }


        return response;
    }


    // =========================================================
    // CUSTOMER REPORT
    // =========================================================

    public List<CustomerReportResponse>
    getCustomerReport() {

        List<Object[]> data =
                saleRepository.getCustomerReport();

        List<CustomerReportResponse> response =
                new ArrayList<>();


        for (Object[] row : data) {

            CustomerReportResponse item =
                    new CustomerReportResponse();

            item.setCustomerName(
                    (String) row[0]
            );

            item.setCustomerMobile(
                    (String) row[1]
            );

            item.setTotalBills(
                    ((Number) row[2]).longValue()
            );

            item.setTotalQuantity(
                    ((Number) row[3]).longValue()
            );

            item.setTotalSales(
                    (BigDecimal) row[4]
            );

            response.add(item);
        }


        return response;
    }


    // =========================================================
    // SUPPLIER REPORT
    // =========================================================

    public List<SupplierReportResponse>
    getSupplierReport() {

        List<Object[]> data =
                purchaseRepository
                        .getSupplierPurchaseSummary();

        List<Object[]> quantityData =
                purchaseRepository
                        .getSupplierTotalQuantity();


        List<SupplierReportResponse> response =
                new ArrayList<>();


        for (Object[] row : data) {

            SupplierReportResponse item =
                    new SupplierReportResponse();


            String supplierName =
                    (String) row[0];


            item.setSupplierName(
                    supplierName
            );

            item.setTotalBills(
                    ((Number) row[1]).longValue()
            );

            item.setTotalPurchase(
                    ((Number) row[2]).doubleValue()
            );


            for (Object[] quantityRow :
                    quantityData) {

                String quantitySupplierName =
                        (String) quantityRow[0];


                if (supplierName.equals(
                        quantitySupplierName)) {

                    item.setTotalQuantity(
                            ((Number) quantityRow[1])
                                    .intValue()
                    );

                    break;
                }
            }


            response.add(item);
        }


        return response;
    }
}