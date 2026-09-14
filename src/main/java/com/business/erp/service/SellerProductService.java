package com.business.erp.service;

import com.business.erp.entity.Product;
import com.business.erp.entity.User;
import com.business.erp.repository.ProductRepository;
import com.business.erp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public SellerProductService(
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // =========================================================
    // CURRENT LOGGED-IN SELLER
    // =========================================================

    private User getCurrentSeller() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException("Seller not authenticated");
        }

        String mobile = authentication.getName();

        User seller = userRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new RuntimeException("Seller not found"));

        if (!"SELLER".equalsIgnoreCase(seller.getRole())) {

            throw new RuntimeException(
                    "Only seller can access seller product APIs"
            );
        }

        if (!Boolean.TRUE.equals(seller.getStatus())) {

            throw new RuntimeException(
                    "Seller account is inactive"
            );
        }

        return seller;
    }


    // =========================================================
    // ADD PRODUCT
    // =========================================================

    public Product addProduct(Product product) {

        User seller = getCurrentSeller();

        // Seller ID हमेशा backend set करेगा.
        // Android से भेजी गई seller information को ignore किया जाएगा.
        product.setSeller(seller);

        // Duplicate product code check - seller wise
        if (productRepository.existsBySellerIdAndProductCode(
                seller.getId(),
                product.getProductCode())) {

            throw new RuntimeException(
                    "Product code already exists for this seller"
            );
        }

        return productRepository.save(product);
    }


    // =========================================================
    // GET SELLER PRODUCTS
    // =========================================================

    public List<Product> getMyProducts() {

        User seller = getCurrentSeller();

        return productRepository.findBySellerId(
                seller.getId()
        );
    }


    // =========================================================
    // GET PRODUCT BY ID
    // =========================================================

    public Product getMyProductById(Long id) {

        User seller = getCurrentSeller();

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        // Ownership check
        if (!product.getSeller().getId()
                .equals(seller.getId())) {

            throw new RuntimeException(
                    "You are not allowed to access this product"
            );
        }

        return product;
    }


    // =========================================================
    // UPDATE PRODUCT
    // =========================================================

    public Product updateProduct(
            Long id,
            Product updatedProduct) {

        User seller = getCurrentSeller();

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        // -----------------------------------------------------
        // OWNERSHIP CHECK
        // -----------------------------------------------------

        if (!product.getSeller().getId()
                .equals(seller.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this product"
            );
        }


        // -----------------------------------------------------
        // PRODUCT CODE CHECK
        // -----------------------------------------------------

        String newProductCode =
                updatedProduct.getProductCode();

        boolean codeChanged =
                !product.getProductCode()
                        .equalsIgnoreCase(newProductCode);

        if (codeChanged &&
                productRepository
                        .existsBySellerIdAndProductCode(
                                seller.getId(),
                                newProductCode)) {

            throw new RuntimeException(
                    "Product code already exists for this seller"
            );
        }


        // -----------------------------------------------------
        // UPDATE PRODUCT FIELDS
        // -----------------------------------------------------

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

        // Seller कभी change नहीं होगा
        product.setSeller(seller);

        return productRepository.save(product);
    }


    // =========================================================
    // DELETE PRODUCT
    // =========================================================

    public void deleteProduct(Long id) {

        User seller = getCurrentSeller();

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        // Ownership check
        if (!product.getSeller().getId()
                .equals(seller.getId())) {

            throw new RuntimeException(
                    "You are not allowed to delete this product"
            );
        }

        productRepository.delete(product);
    }
}