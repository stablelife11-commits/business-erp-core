package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Supplier;
import com.student.studentmanagementapi.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    // Add Supplier
    public Supplier addSupplier(Supplier supplier) {

        if (supplierRepository.existsByMobile(supplier.getMobile())) {
            throw new RuntimeException("Mobile number already exists");
        }

        if (supplier.getGstNumber() != null
                && !supplier.getGstNumber().trim().isEmpty()
                && supplierRepository.existsByGstNumber(supplier.getGstNumber())) {

            throw new RuntimeException("GST Number already exists");
        }

        return supplierRepository.save(supplier);
    }

    // Get All Suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    // Get Supplier By ID
    public Supplier getSupplierById(Long id) {

        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    // Update Supplier
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        if (!supplier.getMobile().equals(updatedSupplier.getMobile())
                && supplierRepository.existsByMobile(updatedSupplier.getMobile())) {

            throw new RuntimeException("Mobile number already exists");
        }

        if (updatedSupplier.getGstNumber() != null
                && !updatedSupplier.getGstNumber().trim().isEmpty()
                && !updatedSupplier.getGstNumber().equals(supplier.getGstNumber())
                && supplierRepository.existsByGstNumber(updatedSupplier.getGstNumber())) {

            throw new RuntimeException("GST Number already exists");
        }

        supplier.setSupplierName(updatedSupplier.getSupplierName());
        supplier.setCompanyName(updatedSupplier.getCompanyName());
        supplier.setMobile(updatedSupplier.getMobile());
        supplier.setEmail(updatedSupplier.getEmail());
        supplier.setGstNumber(updatedSupplier.getGstNumber());
        supplier.setAddress(updatedSupplier.getAddress());
        supplier.setCity(updatedSupplier.getCity());
        supplier.setState(updatedSupplier.getState());
        supplier.setPincode(updatedSupplier.getPincode());
        supplier.setStatus(updatedSupplier.getStatus());

        return supplierRepository.save(supplier);
    }

    // Delete Supplier
    public void deleteSupplier(Long id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplierRepository.delete(supplier);
    }

    // Search By Mobile
    public Supplier getSupplierByMobile(String mobile) {

        return supplierRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    // Search By Supplier Name
    public List<Supplier> searchBySupplierName(String supplierName) {
        return supplierRepository.findBySupplierNameContainingIgnoreCase(supplierName);
    }

    // Search By Company Name
    public List<Supplier> searchByCompanyName(String companyName) {
        return supplierRepository.findByCompanyNameContainingIgnoreCase(companyName);
    }
}