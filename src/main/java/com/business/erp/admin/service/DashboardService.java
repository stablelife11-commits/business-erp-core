package com.business.erp.admin.service;

import com.business.erp.auth.customer.repository.CustomerRepository;
import com.business.erp.common.model.DashboardResponse;
import com.business.erp.product.repository.ProductRepository;
import com.business.erp.sale.repository.PurchaseRepository;
import com.business.erp.sale.repository.SaleRepository;
import com.business.erp.admin.repository.SupplierRepository;
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