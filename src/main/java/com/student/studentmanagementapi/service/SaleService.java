package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Product;
import com.student.studentmanagementapi.entity.Sale;
import com.student.studentmanagementapi.entity.SaleItem;
import com.student.studentmanagementapi.repository.ProductRepository;
import com.student.studentmanagementapi.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;


    // ==========================================
    // ADD SALE - MULTIPLE PRODUCTS
    // ==========================================

    public Sale addSale(Sale sale) {

        // ------------------------------------------
        // Generate Sale Number
        // ------------------------------------------

        Optional<Sale> lastSale =
                saleRepository.findTopByOrderByIdDesc();

        if (lastSale.isPresent()) {

            String lastNumber =
                    lastSale.get().getSaleNumber();

            int number =
                    Integer.parseInt(lastNumber.substring(4));

            sale.setSaleNumber(
                    String.format("SAL-%06d", number + 1)
            );

        } else {

            sale.setSaleNumber("SAL-000001");
        }


        // ------------------------------------------
        // Validate Items
        // ------------------------------------------

        if (sale.getItems() == null ||
                sale.getItems().isEmpty()) {

            throw new RuntimeException(
                    "Sale must contain at least one product"
            );
        }


        // ------------------------------------------
        // Calculate Invoice Total
        // ------------------------------------------

        BigDecimal grandTotal = BigDecimal.ZERO;


        for (SaleItem item : sale.getItems()) {

            // ------------------------------------------
            // Validate Product
            // ------------------------------------------

            if (item.getProduct() == null ||
                    item.getProduct().getId() == null) {

                throw new RuntimeException(
                        "Product is required for every sale item"
                );
            }


            // ------------------------------------------
            // Validate Quantity
            // ------------------------------------------

            if (item.getQuantity() == null ||
                    item.getQuantity() <= 0) {

                throw new RuntimeException(
                        "Quantity must be greater than zero"
                );
            }


            // ------------------------------------------
            // Find Product
            // ------------------------------------------

            Product product =
                    productRepository.findById(
                            item.getProduct().getId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found: "
                                            + item.getProduct().getId()
                            )
                    );
                    item.setProduct(product);


            // ------------------------------------------
            // Validate Sale Price
            // ------------------------------------------

            if (item.getSalePrice() == null ||
                    item.getSalePrice().compareTo(BigDecimal.ZERO) < 0) {

                throw new RuntimeException(
                        "Sale price is required"
                );
            }


            // ------------------------------------------
            // Calculate Item Total
            // ------------------------------------------

            BigDecimal itemTotal =
                    item.getSalePrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getQuantity()
                                    )
                            );

            item.setTotalPrice(itemTotal);


            // ------------------------------------------
            // Set Sale Relationship
            // ------------------------------------------

            item.setSale(sale);


            // ------------------------------------------
            // Update Stock
            // ------------------------------------------

            product.setCurrentStock(
                    product.getCurrentStock()
                            - item.getQuantity()
            );


            // ------------------------------------------
            // Update Latest Sale Price
            // ------------------------------------------

            product.setSalePrice(
                    item.getSalePrice()
            );


            productRepository.save(product);


            // ------------------------------------------
            // Add To Grand Total
            // ------------------------------------------

            grandTotal =
                    grandTotal.add(itemTotal);
        }


        // ------------------------------------------
        // Set Invoice Total
        // ------------------------------------------

        sale.setTotalAmount(grandTotal);


        // ------------------------------------------
        // Save Sale + Sale Items
        // CascadeType.ALL
        // ------------------------------------------

        return saleRepository.save(sale);
    }


    // ==========================================
    // GET ALL SALES
    // ==========================================

    public List<Sale> getAllSales() {

        return saleRepository.findAll();
    }


    // ==========================================
    // GET SALE BY ID
    // ==========================================

    public Optional<Sale> getSaleById(Long id) {

        return saleRepository.findById(id);
    }


    // ==========================================
    // GET SALE BY SALE NUMBER
    // ==========================================

    public Optional<Sale> getSaleBySaleNumber(
            String saleNumber) {

        return saleRepository.findBySaleNumber(
                saleNumber
        );
    }


    // ==========================================
    // DELETE SALE
    // ==========================================

    public void deleteSale(Long id) {

        saleRepository.deleteById(id);
    }
}