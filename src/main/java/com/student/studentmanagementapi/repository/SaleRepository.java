package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // =========================================================
    // SALE NUMBER
    // =========================================================

    Optional<Sale> findTopByOrderByIdDesc();

    Optional<Sale> findBySaleNumber(String saleNumber);

    





    // =========================================================
    // TOTAL SALES
    // =========================================================

    @Query("""
        SELECT COALESCE(SUM(s.totalAmount), 0)
        FROM Sale s
        """)
    BigDecimal getTotalSaleAmount();

    // =========================================================
// CUSTOMER TOTAL SALES
// =========================================================

@Query("""
    SELECT COALESCE(SUM(s.totalAmount), 0)
    FROM Sale s
    WHERE s.customerMobile = :mobile
    """)
BigDecimal getTotalSalesByCustomerMobile(
        String mobile
);
// =========================================================
// CUSTOMER SALES
// =========================================================

List<Sale> findByCustomerMobile(String customerMobile);

    // =========================================================
    // TODAY SALES COUNT
    // =========================================================

    long countBySaleDate(LocalDate saleDate);


    // =========================================================
    // SALES BETWEEN DATES
    // =========================================================

    List<Sale> findBySaleDateBetween(
            LocalDate fromDate,
            LocalDate toDate
    );


    // =========================================================
    // TODAY SALE AMOUNT
    // =========================================================

    @Query("""
        SELECT COALESCE(SUM(s.totalAmount), 0)
        FROM Sale s
        WHERE s.saleDate = :saleDate
        """)
    BigDecimal getTodaySaleAmount(
            LocalDate saleDate
    );


    // =========================================================
    // MONTHLY SALES
    // =========================================================

    @Query("""
        SELECT MONTH(s.saleDate),
               COALESCE(SUM(s.totalAmount), 0)
        FROM Sale s
        GROUP BY MONTH(s.saleDate)
        ORDER BY MONTH(s.saleDate)
        """)
    List<Object[]> getMonthlySales();


    // =========================================================
    // TOP SELLING PRODUCTS
    // MULTIPLE PRODUCT SALE SUPPORT
    // =========================================================

    @Query("""
        SELECT p.productName,
               SUM(i.quantity)
        FROM Sale s
        JOIN s.items i
        JOIN i.product p
        GROUP BY p.productName
        ORDER BY SUM(i.quantity) DESC
        """)
    List<Object[]> getTopSellingProducts();


    // =========================================================
    // TOP CUSTOMERS
    // =========================================================

    @Query("""
        SELECT s.customerName,
               COALESCE(SUM(s.totalAmount), 0)
        FROM Sale s
        GROUP BY s.customerName
        ORDER BY SUM(s.totalAmount) DESC
        """)
    List<Object[]> getTopCustomers();


    // =========================================================
    // CUSTOMER REPORT
    // MULTIPLE PRODUCT SALE SUPPORT
    // =========================================================

    @Query("""
        SELECT s.customerName,
               s.customerMobile,
               COUNT(DISTINCT s.id),
               COALESCE(SUM(i.quantity), 0),
               COALESCE(SUM(s.totalAmount), 0)
        FROM Sale s
        LEFT JOIN s.items i
        GROUP BY s.customerName, s.customerMobile
        ORDER BY SUM(s.totalAmount) DESC
        """)
    List<Object[]> getCustomerReport();

}