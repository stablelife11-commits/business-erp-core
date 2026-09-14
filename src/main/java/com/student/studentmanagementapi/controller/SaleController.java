package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.entity.Sale;
import com.student.studentmanagementapi.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public Sale addSale(@RequestBody Sale sale) {
        return saleService.addSale(sale);
    }

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public Optional<Sale> getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @GetMapping("/number/{saleNumber}")
    public Optional<Sale> getSaleBySaleNumber(@PathVariable String saleNumber) {
        return saleService.getSaleBySaleNumber(saleNumber);
    }

    @DeleteMapping("/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return "Sale deleted successfully.";
    }
}