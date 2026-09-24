package com.business.erp.admin.service;

import com.business.erp.admin.model.Supplier;
import com.business.erp.admin.repository.SupplierPaymentRepository;
import com.business.erp.admin.repository.SupplierRepository;
import com.business.erp.common.model.SupplierOutstandingResponse;
import com.business.erp.sale.repository.PurchaseRepository;

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