package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.dto.DashboardResponse;
import com.student.studentmanagementapi.repository.CustomerRepository;
import com.student.studentmanagementapi.repository.ProductRepository;
import com.student.studentmanagementapi.repository.PurchaseRepository;
import com.student.studentmanagementapi.repository.SaleRepository;
import com.student.studentmanagementapi.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DashboardService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SaleRepository saleRepository;

    public DashboardResponse getDashboard() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalCustomers(customerRepository.count());
        response.setTotalSuppliers(supplierRepository.count());
        response.setTotalProducts(productRepository.count());

response.setTotalStockQuantity(
        productRepository.getTotalStockQuantity()
);

response.setTotalStockValue(
        productRepository.getTotalStockValue()
);

        long repoCount = purchaseRepository.count();

        System.out.println("===========");
        System.out.println("Purchase Count = " + repoCount);
        System.out.println("===========");

        response.setTotalPurchases(repoCount);
        System.out.println("Response Count = " + response.getTotalPurchases());
        response.setTotalPurchases(purchaseRepository.count());
        response.setTotalSales(saleRepository.count());

        Double totalPurchase = purchaseRepository.getTotalPurchaseAmount();

        response.setTotalPurchaseAmount(
                BigDecimal.valueOf(totalPurchase)
        );
        response.setTotalSaleAmount(saleRepository.getTotalSaleAmount());
        response.setLowStockProducts(productRepository.getLowStockProducts());

        response.setTodaySaleAmount(
                saleRepository.getTodaySaleAmount(LocalDate.now())
        );

        Double todayPurchase = purchaseRepository.getTodayPurchaseAmount(LocalDate.now());

        response.setTodayPurchaseAmount(
                BigDecimal.valueOf(todayPurchase)
        );

        response.setTodaySalesCount(
                saleRepository.countBySaleDate(LocalDate.now())
        );

        response.setTodayPurchasesCount(
                purchaseRepository.countByPurchaseDate(LocalDate.now())
        );

        return response;
    }


}