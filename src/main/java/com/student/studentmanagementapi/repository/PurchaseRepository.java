package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // Find Purchase By Purchase Number
    Optional<Purchase> findByPurchaseNumber(String purchaseNumber);

    // Check Purchase Number Exists
    boolean existsByPurchaseNumber(String purchaseNumber);

    // Dashboard - Today's Purchase Count
    long countByPurchaseDate(LocalDate purchaseDate);

    // Purchase Report - Date Range
    List<Purchase> findByPurchaseDateBetween(LocalDate fromDate, LocalDate toDate);

    // Dashboard - Total Purchase Amount
    @Query("SELECT COALESCE(SUM(p.totalAmount), 0) FROM Purchase p")
    Double getTotalPurchaseAmount();

    // Dashboard - Today's Purchase Amount
    @Query("SELECT COALESCE(SUM(p.totalAmount), 0) FROM Purchase p WHERE p.purchaseDate = :purchaseDate")
    Double getTodayPurchaseAmount(LocalDate purchaseDate);

    @Query("""
SELECT MONTH(p.purchaseDate), COALESCE(SUM(p.totalAmount), 0)
FROM Purchase p
GROUP BY MONTH(p.purchaseDate)
ORDER BY MONTH(p.purchaseDate)
""")
    List<Object[]> getMonthlyPurchases();

    @Query("""
SELECT p.supplier.supplierName, COALESCE(SUM(p.totalAmount), 0)
FROM Purchase p
GROUP BY p.supplier.supplierName
ORDER BY SUM(p.totalAmount) DESC
""")
    List<Object[]> getTopSuppliers();
    @Query("""
SELECT p.supplier.supplierName,
       COUNT(p),
       COALESCE(SUM(p.totalAmount), 0)
FROM Purchase p
GROUP BY p.supplier.supplierName
ORDER BY SUM(p.totalAmount) DESC
""")
    List<Object[]> getSupplierPurchaseSummary();

    @Query("""
SELECT p.supplier.supplierName,
       COALESCE(SUM(i.quantity), 0)
FROM Purchase p
JOIN p.items i
GROUP BY p.supplier.supplierName
""")
    List<Object[]> getSupplierTotalQuantity();

    @Query("""
SELECT COALESCE(SUM(p.totalAmount), 0)
FROM Purchase p
WHERE p.supplier.id = :supplierId
""")
BigDecimal getTotalPurchaseAmountBySupplier(Long supplierId);
}