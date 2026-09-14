package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.entity.SupplierPayment;
import com.student.studentmanagementapi.service.SupplierPaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier-payments")
public class SupplierPaymentController {

    @Autowired
    private SupplierPaymentService supplierPaymentService;


    // ==========================================
    // ADD SUPPLIER PAYMENT
    // ==========================================

    @PostMapping("/supplier/{supplierId}")
public ResponseEntity<SupplierPayment> addPayment(
        @PathVariable Long supplierId,
        @RequestBody SupplierPayment payment) {

    return ResponseEntity.ok(
            supplierPaymentService.addPayment(
                    supplierId,
                    payment
            )
    );
}


    // ==========================================
    // GET ALL PAYMENTS
    // ==========================================

    @GetMapping
    public ResponseEntity<List<SupplierPayment>> getAllPayments() {

        return ResponseEntity.ok(
                supplierPaymentService.getAllPayments()
        );
    }


    // ==========================================
    // GET PAYMENT BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<SupplierPayment> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                supplierPaymentService.getPaymentById(id)
        );
    }


    // ==========================================
    // GET PAYMENTS BY SUPPLIER
    // ==========================================

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<SupplierPayment>> getPaymentsBySupplier(
            @PathVariable Long supplierId) {

        return ResponseEntity.ok(
                supplierPaymentService
                        .getPaymentsBySupplier(supplierId)
        );
    }


    // ==========================================
    // GET PAYMENTS BY PURCHASE
    // ==========================================

    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<List<SupplierPayment>> getPaymentsByPurchase(
            @PathVariable Long purchaseId) {

        return ResponseEntity.ok(
                supplierPaymentService
                        .getPaymentsByPurchase(purchaseId)
        );
    }


    // ==========================================
    // DELETE PAYMENT
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(
            @PathVariable Long id) {

        supplierPaymentService.deletePayment(id);

        return ResponseEntity.ok(
                "Supplier payment deleted successfully"
        );
    }
}