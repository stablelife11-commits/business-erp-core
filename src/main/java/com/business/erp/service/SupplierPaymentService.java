package com.business.erp.service;

import com.business.erp.entity.Purchase;
import com.business.erp.entity.Supplier;
import com.business.erp.entity.SupplierPayment;
import com.business.erp.repository.PurchaseRepository;
import com.business.erp.repository.SupplierPaymentRepository;
import com.business.erp.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierPaymentService {

    @Autowired
    private SupplierPaymentRepository supplierPaymentRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;


    // ==========================================
    // ADD SUPPLIER PAYMENT
    // ==========================================

    public SupplierPayment addPayment(
            Long supplierId,
            SupplierPayment payment) {

        // Find Supplier
        Supplier supplier =
                supplierRepository.findById(supplierId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"
                                )
                        );

        // Set Supplier
        payment.setSupplier(supplier);


        // ==========================================
        // OPTIONAL PURCHASE
        // ==========================================

        if (payment.getPurchase() != null
                && payment.getPurchase().getId() != null) {

            Long purchaseId =
                    payment.getPurchase().getId();

            Purchase purchase =
                    purchaseRepository.findById(purchaseId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Purchase not found"
                                    )
                            );

            payment.setPurchase(purchase);
        }


        // ==========================================
        // SAVE PAYMENT
        // ==========================================

        return supplierPaymentRepository.save(payment);
    }


    // ==========================================
    // GET ALL PAYMENTS
    // ==========================================

    public List<SupplierPayment> getAllPayments() {

        return supplierPaymentRepository.findAll();
    }


    // ==========================================
    // GET PAYMENT BY ID
    // ==========================================

    public SupplierPayment getPaymentById(Long id) {

        return supplierPaymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier payment not found"
                        )
                );
    }


    // ==========================================
    // GET SUPPLIER PAYMENTS
    // ==========================================

    public List<SupplierPayment> getPaymentsBySupplier(
            Long supplierId) {

        // Verify supplier exists

        supplierRepository.findById(supplierId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier not found"
                        )
                );

        return supplierPaymentRepository
                .findBySupplierIdOrderByPaymentDateAsc(
                        supplierId
                );
    }


    // ==========================================
    // GET PURCHASE PAYMENTS
    // ==========================================

    public List<SupplierPayment> getPaymentsByPurchase(
            Long purchaseId) {

        // Verify purchase exists

        purchaseRepository.findById(purchaseId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase not found"
                        )
                );

        return supplierPaymentRepository
                .findByPurchaseIdOrderByPaymentDateAsc(
                        purchaseId
                );
    }


    // ==========================================
    // DELETE PAYMENT
    // ==========================================

    public void deletePayment(Long id) {

        if (!supplierPaymentRepository.existsById(id)) {

            throw new RuntimeException(
                    "Supplier payment not found"
            );
        }

        supplierPaymentRepository.deleteById(id);
    }
}