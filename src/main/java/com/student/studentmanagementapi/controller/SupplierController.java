package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.entity.Supplier;
import com.student.studentmanagementapi.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // Add Supplier
    @PostMapping
    public Supplier addSupplier(@Valid @RequestBody Supplier supplier) {
        return supplierService.addSupplier(supplier);
    }

    // Get All Suppliers
    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    // Get Supplier By ID
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    // Update Supplier
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody Supplier supplier) {

        return ResponseEntity.ok(supplierService.updateSupplier(id, supplier));
    }

    // Delete Supplier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {

        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();
    }

    // Search By Mobile
    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<Supplier> getSupplierByMobile(@PathVariable String mobile) {

        return ResponseEntity.ok(supplierService.getSupplierByMobile(mobile));
    }

    // Search By Supplier Name
    @GetMapping("/search")
    public ResponseEntity<List<Supplier>> searchBySupplierName(
            @RequestParam String supplierName) {

        return ResponseEntity.ok(supplierService.searchBySupplierName(supplierName));
    }

    // Search By Company Name
    @GetMapping("/company")
    public ResponseEntity<List<Supplier>> searchByCompanyName(
            @RequestParam String companyName) {

        return ResponseEntity.ok(supplierService.searchByCompanyName(companyName));
    }
}