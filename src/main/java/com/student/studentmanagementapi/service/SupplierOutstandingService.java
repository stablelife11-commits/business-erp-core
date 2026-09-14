package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.dto.SupplierOutstandingResponse;
import com.student.studentmanagementapi.entity.Supplier;
import com.student.studentmanagementapi.repository.PurchaseRepository;
import com.student.studentmanagementapi.repository.SupplierPaymentRepository;
import com.student.studentmanagementapi.repository.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SupplierOutstandingService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SupplierPaymentRepository supplierPaymentRepository;


    // ==========================================
    // GET SUPPLIER OUTSTANDING
    // ==========================================

    public SupplierOutstandingResponse getSupplierOutstanding(
        Long supplierId) {

        // ==========================================
        // VERIFY SUPPLIER
        // ==========================================

        Supplier supplier =
                supplierRepository.findById(supplierId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"
                                )
                        );


        // ==========================================
        // TOTAL PURCHASES
        // ==========================================

        BigDecimal totalPurchases =
                purchaseRepository
                        .getTotalPurchaseAmountBySupplier(
                                supplier.getId()
                        );


        if (totalPurchases == null) {
            totalPurchases = BigDecimal.ZERO;
        }


        // ==========================================
        // TOTAL PAID
        // ==========================================

        BigDecimal totalPaid =
                supplierPaymentRepository
                        .getTotalPaymentBySupplier(
                                supplier.getId()
                        );


        if (totalPaid == null) {
            totalPaid = BigDecimal.ZERO;
        }


        // ==========================================
        // OUTSTANDING
        // ==========================================

        BigDecimal outstanding =
                totalPurchases.subtract(
                        totalPaid
                );


        // ==========================================
        // RESPONSE
        // ==========================================

        return new SupplierOutstandingResponse(
                supplier.getId(),
                totalPurchases,
                totalPaid,
                outstanding
        );
    }
}