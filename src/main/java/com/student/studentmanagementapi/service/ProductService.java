package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Product;
import com.student.studentmanagementapi.entity.User;
import com.student.studentmanagementapi.repository.ProductRepository;
import com.student.studentmanagementapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    // =========================================================
    // CURRENT LOGGED-IN USER
    // =========================================================

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException("User not authenticated");
        }

        String mobile = authentication.getName();

        return userRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }


    // =========================================================
    // ADD PRODUCT
    // =========================================================

    public Product addProduct(Product product) {

        User currentUser = getCurrentUser();

        // -----------------------------------------------------
        // SELLER
        // -----------------------------------------------------
        // Seller apne login se product add karega.
        // Seller request me seller id bheje ya na bheje,
        // backend current logged-in seller ko hi assign karega.
        // -----------------------------------------------------

        if ("SELLER".equalsIgnoreCase(currentUser.getRole())) {

            product.setSeller(currentUser);

        }

        // -----------------------------------------------------
        // ADMIN
        // -----------------------------------------------------
        // Admin kisi bhi seller ke naam par product create kar sakta hai.
        // Request me seller.id dena hoga.
        // -----------------------------------------------------

        else if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {

            if (product.getSeller() == null ||
                    product.getSeller().getId() == null) {

                throw new RuntimeException(
                        "Admin must select a seller"
                );
            }

            Long sellerId = product.getSeller().getId();

            User seller = userRepository.findById(sellerId)
                    .orElseThrow(() ->
                            new RuntimeException("Seller not found"));

            if (!"SELLER".equalsIgnoreCase(seller.getRole())) {

                throw new RuntimeException(
                        "Selected user is not a seller"
                );
            }

            product.setSeller(seller);

        }

        // -----------------------------------------------------
        // OTHER ROLES
        // -----------------------------------------------------

        else {

            throw new RuntimeException(
                    "You are not allowed to add products"
            );
        }


        // =====================================================
        // DUPLICATE PRODUCT CODE
        // =====================================================

        Long sellerId = product.getSeller().getId();

        if (productRepository.existsBySellerIdAndProductCode(
                sellerId,
                product.getProductCode())) {

            throw new RuntimeException(
                    "Product code already exists for this seller"
            );
        }


        // =====================================================
        // SAVE
        // =====================================================

        return productRepository.save(product);
    }


    // =========================================================
    // GET ALL PRODUCTS
    // =========================================================

    public List<Product> getAllProducts() {

        User currentUser = getCurrentUser();

        // ADMIN -> all products
        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository.findAll();
        }

        // SELLER -> only own products
        if ("SELLER".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository.findBySellerId(
                    currentUser.getId()
            );
        }

        // CUSTOMER -> only active products
        if ("CUSTOMER".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository.findByStatusTrue();
        }

        throw new RuntimeException(
                "You are not allowed to view products"
        );
    }


    // =========================================================
    // GET PRODUCT BY ID
    // =========================================================

    public Product getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        User currentUser = getCurrentUser();


        // ADMIN -> can view any product
        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {

            return product;
        }


        // SELLER -> only own product
        if ("SELLER".equalsIgnoreCase(currentUser.getRole())) {

            if (!product.getSeller().getId()
                    .equals(currentUser.getId())) {

                throw new RuntimeException(
                        "You are not allowed to access this product"
                );
            }

            return product;
        }


        // CUSTOMER -> only active product
        if ("CUSTOMER".equalsIgnoreCase(currentUser.getRole())) {

            if (!Boolean.TRUE.equals(product.getStatus())) {

                throw new RuntimeException(
                        "Product is not available"
                );
            }

            return product;
        }


        throw new RuntimeException(
                "You are not allowed to view this product"
        );
    }


    // =========================================================
    // UPDATE PRODUCT
    // =========================================================

    public Product updateProduct(
            Long id,
            Product updatedProduct) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        User currentUser = getCurrentUser();


        // =====================================================
        // SELLER
        // =====================================================

        if ("SELLER".equalsIgnoreCase(currentUser.getRole())) {

            // Seller दूसरे seller का product update नहीं कर सकता

            if (!product.getSeller().getId()
                    .equals(currentUser.getId())) {

                throw new RuntimeException(
                        "You are not allowed to update this product"
                );
            }

            // Seller अपना seller नहीं बदल सकता
            product.setSeller(currentUser);
        }


        // =====================================================
        // ADMIN
        // =====================================================

        else if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {

            // Admin seller change कर सकता है

            if (updatedProduct.getSeller() != null &&
                    updatedProduct.getSeller().getId() != null) {

                User seller = userRepository.findById(
                        updatedProduct.getSeller().getId()
                ).orElseThrow(() ->
                        new RuntimeException("Seller not found"));

                if (!"SELLER".equalsIgnoreCase(
                        seller.getRole())) {

                    throw new RuntimeException(
                            "Selected user is not a seller"
                    );
                }

                product.setSeller(seller);
            }
        }

        else {

            throw new RuntimeException(
                    "You are not allowed to update products"
            );
        }


        // =====================================================
        // PRODUCT CODE CHECK
        // =====================================================

        String newProductCode =
                updatedProduct.getProductCode();

        Long sellerId =
                product.getSeller().getId();

        boolean codeChanged =
                !product.getProductCode()
                        .equals(newProductCode);

        if (codeChanged &&
                productRepository
                        .existsBySellerIdAndProductCode(
                                sellerId,
                                newProductCode)) {

            throw new RuntimeException(
                    "Product code already exists for this seller"
            );
        }


        // =====================================================
        // UPDATE FIELDS
        // =====================================================

        product.setProductName(
                updatedProduct.getProductName());

        product.setProductCode(
                updatedProduct.getProductCode());

        product.setBrand(
                updatedProduct.getBrand());

        product.setCategory(
                updatedProduct.getCategory());

        product.setSize(
                updatedProduct.getSize());

        product.setColor(
                updatedProduct.getColor());

        product.setPurchasePrice(
                updatedProduct.getPurchasePrice());

        product.setSalePrice(
                updatedProduct.getSalePrice());

        product.setOpeningStock(
                updatedProduct.getOpeningStock());

        product.setCurrentStock(
                updatedProduct.getCurrentStock());

        product.setStatus(
                updatedProduct.getStatus());


        return productRepository.save(product);
    }


    // =========================================================
    // DELETE PRODUCT
    // =========================================================

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        User currentUser = getCurrentUser();


        // ADMIN -> delete any product
        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {

            productRepository.delete(product);
            return;
        }


        // SELLER -> delete only own product
        if ("SELLER".equalsIgnoreCase(currentUser.getRole())) {

            if (!product.getSeller().getId()
                    .equals(currentUser.getId())) {

                throw new RuntimeException(
                        "You are not allowed to delete this product"
                );
            }

            productRepository.delete(product);
            return;
        }


        throw new RuntimeException(
                "You are not allowed to delete products"
        );
    }


    // =========================================================
    // SEARCH BY PRODUCT CODE
    // =========================================================

    public Product getProductByCode(String productCode) {

        User currentUser = getCurrentUser();


        // ADMIN
        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getProductCode()
                            .equalsIgnoreCase(productCode))
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found"));
        }


        // SELLER
        if ("SELLER".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findBySellerIdAndProductCode(
                            currentUser.getId(),
                            productCode)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found"));
        }


        // CUSTOMER
        if ("CUSTOMER".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findByStatusTrue()
                    .stream()
                    .filter(p -> p.getProductCode()
                            .equalsIgnoreCase(productCode))
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found"));
        }


        throw new RuntimeException(
                "You are not allowed to search products"
        );
    }


    // =========================================================
    // SEARCH BY PRODUCT NAME
    // =========================================================

    public List<Product> searchByProductName(
            String productName) {

        User currentUser = getCurrentUser();


        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getProductName()
                            .toLowerCase()
                            .contains(productName.toLowerCase()))
                    .toList();
        }


        if ("SELLER".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findBySellerIdAndProductNameContainingIgnoreCase(
                            currentUser.getId(),
                            productName);
        }


        if ("CUSTOMER".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findByStatusTrue()
                    .stream()
                    .filter(p -> p.getProductName()
                            .toLowerCase()
                            .contains(productName.toLowerCase()))
                    .toList();
        }


        throw new RuntimeException(
                "You are not allowed to search products"
        );
    }


    // =========================================================
    // SEARCH BY BRAND
    // =========================================================

    public List<Product> searchByBrand(String brand) {

        User currentUser = getCurrentUser();


        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getBrand()
                            .toLowerCase()
                            .contains(brand.toLowerCase()))
                    .toList();
        }


        if ("SELLER".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findBySellerIdAndBrandContainingIgnoreCase(
                            currentUser.getId(),
                            brand);
        }


        if ("CUSTOMER".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findByStatusTrue()
                    .stream()
                    .filter(p -> p.getBrand()
                            .toLowerCase()
                            .contains(brand.toLowerCase()))
                    .toList();
        }


        throw new RuntimeException(
                "You are not allowed to search products"
        );
    }


    // =========================================================
    // LOW STOCK
    // =========================================================

    public List<Product> getLowStockProducts() {

        User currentUser = getCurrentUser();


        // ADMIN
        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findByCurrentStockLessThanEqual(10);
        }


        // SELLER
        if ("SELLER".equalsIgnoreCase(currentUser.getRole())) {

            return productRepository
                    .findBySellerIdAndCurrentStockLessThanEqual(
                            currentUser.getId(),
                            10);
        }


        throw new RuntimeException(
                "You are not allowed to view low stock products"
        );
    }
}