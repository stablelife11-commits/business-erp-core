package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.CustomerPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerPaymentRepository
        extends JpaRepository<CustomerPayment, Long> {

    // ==========================================
    // CUSTOMER PAYMENTS
    // ==========================================

    List<CustomerPayment> findByCustomerId(Long customerId);


    // ==========================================
    // TOTAL PAYMENT RECEIVED BY CUSTOMER
    // ==========================================

    @Query("""
            SELECT COALESCE(SUM(p.amount), 0)
            FROM CustomerPayment p
            WHERE p.customer.id = :customerId
            """)
    BigDecimal getTotalPaidByCustomer(
            @Param("customerId") Long customerId
    );
}