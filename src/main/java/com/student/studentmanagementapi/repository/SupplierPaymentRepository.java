package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.SupplierPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SupplierPaymentRepository
        extends JpaRepository<SupplierPayment, Long> {

    List<SupplierPayment> findBySupplierIdOrderByPaymentDateAsc(
            Long supplierId
    );

    List<SupplierPayment> findByPurchaseIdOrderByPaymentDateAsc(
            Long purchaseId
    );

    // ==========================================
    // TOTAL PAYMENT BY SUPPLIER
    // ==========================================

    @Query("""
        SELECT COALESCE(SUM(p.amount), 0)
        FROM SupplierPayment p
        WHERE p.supplier.id = :supplierId
    """)
    BigDecimal getTotalPaymentBySupplier(
            @Param("supplierId") Long supplierId
    );
}