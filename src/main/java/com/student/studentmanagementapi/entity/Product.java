package com.student.studentmanagementapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_seller_product_code",
                        columnNames = {"seller_id", "product_code"}
                )
        }
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Product kis seller ka hai
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @NotBlank(message = "Product name is required")
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @NotBlank(message = "Product code is required")
    @Column(name = "product_code", nullable = false, length = 30)
    private String productCode;

    @NotBlank(message = "Brand is required")
    @Column(nullable = false, length = 50)
    private String brand;

    @NotBlank(message = "Category is required")
    @Column(nullable = false, length = 50)
    private String category;

    @Column(length = 20)
    private String size;

    @Column(length = 30)
    private String color;

    @DecimalMin(
            value = "0.0",
            message = "Purchase price must be greater than or equal to 0"
    )
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @DecimalMin(
            value = "0.0",
            message = "Sale price must be greater than or equal to 0"
    )
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column(nullable = false)
    private Integer openingStock = 0;

    @Column(nullable = false)
    private Integer currentStock = 0;

    @Column(nullable = false)
    private Boolean status = true;
}