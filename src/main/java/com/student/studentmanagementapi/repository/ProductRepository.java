package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // =========================
    // Seller-wise Product Code
    // =========================

    boolean existsBySellerIdAndProductCode(
            Long sellerId,
            String productCode
    );

    Optional<Product> findBySellerIdAndProductCode(
            Long sellerId,
            String productCode
    );

    // =========================
    // Seller Products
    // =========================

    List<Product> findBySellerId(Long sellerId);

    List<Product> findBySellerIdAndStatusTrue(Long sellerId);

    // =========================
    // Search - Seller Wise
    // =========================

    List<Product> findBySellerIdAndProductNameContainingIgnoreCase(
            Long sellerId,
            String productName
    );

    List<Product> findBySellerIdAndBrandContainingIgnoreCase(
            Long sellerId,
            String brand
    );
     
    List<Product> findByCurrentStockLessThanEqual(Integer stock);
    List<Product> findBySellerIdAndCurrentStockLessThanEqual(
            Long sellerId,
            Integer stock
    );

    // =========================
    // Buyer - Active Products
    // =========================

    List<Product> findByStatusTrue();

    // =========================
    // Dashboard
    // =========================

    @Query("SELECT COUNT(p) FROM Product p WHERE p.currentStock <= 10")
    long getLowStockProducts();

    @Query("SELECT COALESCE(SUM(p.currentStock), 0) FROM Product p")
    Integer getTotalStockQuantity();

    @Query("SELECT COALESCE(SUM(p.currentStock * p.purchasePrice), 0) FROM Product p")
    BigDecimal getTotalStockValue();
}