package com.business.erp.controller;

import com.business.erp.dto.PurchaseRequest;
import com.business.erp.dto.PurchaseResponse;
import com.business.erp.service.PurchaseService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {


    @Autowired
    private PurchaseService purchaseService;


    @PostMapping
    public PurchaseResponse addPurchase(
            @Valid @RequestBody PurchaseRequest request) {

        return purchaseService.addPurchase(request);
    }
    @GetMapping
    public List<PurchaseResponse> getAllPurchases() {

        return purchaseService.getAllPurchases();
    }

    @GetMapping("/{id}")
    public PurchaseResponse getPurchaseById(@PathVariable Long id) {

        return purchaseService.getPurchaseById(id);
    }

}