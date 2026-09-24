package com.business.erp.product.controller;

import com.business.erp.product.entity.Product;
import com.business.erp.product.service.SellerProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/products")
public class SellerProductController {

    private final SellerProductService sellerProductService;

    public SellerProductController(
            SellerProductService sellerProductService) {

        this.sellerProductService = sellerProductService;
    }


    // =========================================================
    // ADD PRODUCT
    // =========================================================

    @PostMapping
    public ResponseEntity<Product> addProduct(
            @Valid @RequestBody Product product) {

        return ResponseEntity.ok(
                sellerProductService.addProduct(product)
        );
    }


    // =========================================================
    // GET MY PRODUCTS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Product>> getMyProducts() {

        return ResponseEntity.ok(
                sellerProductService.getMyProducts()
        );
    }


    // =========================================================
    // GET MY PRODUCT BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Product> getMyProductById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sellerProductService.getMyProductById(id)
        );
    }


    // =========================================================
    // UPDATE MY PRODUCT
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {

        return ResponseEntity.ok(
                sellerProductService.updateProduct(
                        id,
                        product
                )
        );
    }


    // =========================================================
    // DELETE MY PRODUCT
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id) {

        sellerProductService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}