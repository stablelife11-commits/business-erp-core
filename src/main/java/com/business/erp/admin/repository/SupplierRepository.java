package com.business.erp.admin.repository;

import com.business.erp.admin.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByMobile(String mobile);

    boolean existsByGstNumber(String gstNumber);

    Optional<Supplier> findByMobile(String mobile);

    List<Supplier> findBySupplierNameContainingIgnoreCase(String supplierName);

    List<Supplier> findByCompanyNameContainingIgnoreCase(String companyName);
}