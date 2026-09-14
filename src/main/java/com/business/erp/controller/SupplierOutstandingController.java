package com.business.erp.controller;

import com.business.erp.dto.SupplierOutstandingResponse;
import com.business.erp.service.SupplierOutstandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier-outstanding")
public class SupplierOutstandingController {

    @Autowired
    private SupplierOutstandingService supplierOutstandingService;

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<SupplierOutstandingResponse> getSupplierOutstanding(
            @PathVariable Long supplierId) {

        return ResponseEntity.ok(
                supplierOutstandingService
                        .getSupplierOutstanding(supplierId)
        );
    }
}