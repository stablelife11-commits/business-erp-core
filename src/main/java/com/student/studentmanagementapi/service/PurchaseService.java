package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.repository.ProductRepository;
import com.student.studentmanagementapi.repository.PurchaseItemRepository;
import com.student.studentmanagementapi.repository.PurchaseRepository;
import com.student.studentmanagementapi.repository.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.student.studentmanagementapi.dto.PurchaseRequest;
import com.student.studentmanagementapi.dto.PurchaseResponse;
import com.student.studentmanagementapi.dto.PurchaseItemRequest;

import com.student.studentmanagementapi.entity.Purchase;
import com.student.studentmanagementapi.entity.Supplier;
import com.student.studentmanagementapi.entity.Product;
import com.student.studentmanagementapi.entity.PurchaseItem;

import java.math.BigDecimal;
import java.util.List;
import com.student.studentmanagementapi.dto.PurchaseItemResponse;
import java.util.ArrayList;


@Service
@Transactional
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    public PurchaseResponse addPurchase(PurchaseRequest request) {


        // Find Supplier
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));


        // Create Purchase
        Purchase purchase = new Purchase();

        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(request.getPurchaseDate());
        purchase.setInvoiceNumber(request.getInvoiceNumber());
        purchase.setRemarks(request.getRemarks());


        // Generate temporary purchase number
        purchase.setPurchaseNumber("TEMP");


// Save Purchase
        purchase = purchaseRepository.save(purchase);


// Generate final purchase number
        purchase.setPurchaseNumber(
                generatePurchaseNumber(purchase.getId())
        );


// Update Purchase
        purchase = purchaseRepository.save(purchase);



        List<PurchaseItemRequest> items = request.getItems();


        BigDecimal grandTotal = BigDecimal.ZERO;



        for (PurchaseItemRequest itemRequest : items) {


            // Find Product
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found with ID: "
                                            + itemRequest.getProductId()
                            ));



            // Create Purchase Item

            PurchaseItem purchaseItem = new PurchaseItem();

            purchaseItem.setPurchase(purchase);
            purchaseItem.setProduct(product);
            purchaseItem.setQuantity(itemRequest.getQuantity());


            Double purchasePrice = itemRequest.getPurchasePrice();

            Double itemTotal =
                    itemRequest.getQuantity() * purchasePrice;


            purchaseItem.setPurchasePrice(purchasePrice);

            purchaseItem.setTotalPrice(itemTotal);



            // Save Purchase Item

            purchaseItemRepository.save(purchaseItem);



            // Update Stock

            product.setCurrentStock(
                    product.getCurrentStock()
                            + itemRequest.getQuantity()
            );


            // Update Latest Purchase Price

            product.setPurchasePrice(
                    BigDecimal.valueOf(itemRequest.getPurchasePrice())
            );


            productRepository.save(product);



            // Add Total

            grandTotal = grandTotal.add(BigDecimal.valueOf(itemTotal));

        }



        // Update Purchase Total

        purchase.setTotalAmount(
                grandTotal.doubleValue()
        );

        purchase.setStatus("COMPLETED");

        purchaseRepository.save(purchase);



        PurchaseResponse response = new PurchaseResponse();

        response.setId(purchase.getId());
        response.setPurchaseNumber(purchase.getPurchaseNumber());
        response.setPurchaseDate(purchase.getPurchaseDate());
        response.setSupplierName(purchase.getSupplier().getSupplierName());
        response.setInvoiceNumber(purchase.getInvoiceNumber());
        response.setTotalAmount(purchase.getTotalAmount());
        response.setStatus(purchase.getStatus());

        List<PurchaseItemResponse> itemResponses = new ArrayList<>();

        List<PurchaseItem> savedItems =
                purchaseItemRepository.findByPurchaseId(purchase.getId());


        for (PurchaseItem item : savedItems) {

            PurchaseItemResponse itemResponse = new PurchaseItemResponse();

            itemResponse.setProductName(
                    item.getProduct().getProductName()
            );

            itemResponse.setQuantity(
                    item.getQuantity()
            );

            itemResponse.setPurchasePrice(
                    item.getPurchasePrice()
            );

            itemResponse.setTotalPrice(
                    item.getTotalPrice()
            );

            itemResponses.add(itemResponse);
        }


        response.setItems(itemResponses);

        return response;
    }
    // Get All Purchases
    public List<PurchaseResponse> getAllPurchases() {

        List<Purchase> purchases = purchaseRepository.findAll();

        List<PurchaseResponse> responseList = new ArrayList<>();

        for (Purchase purchase : purchases) {

            PurchaseResponse response = new PurchaseResponse();

            response.setId(purchase.getId());
            response.setPurchaseNumber(purchase.getPurchaseNumber());
            response.setPurchaseDate(purchase.getPurchaseDate());
            response.setSupplierName(purchase.getSupplier().getSupplierName());
            response.setInvoiceNumber(purchase.getInvoiceNumber());
            response.setTotalAmount(purchase.getTotalAmount());
            response.setStatus(purchase.getStatus());

            List<PurchaseItemResponse> itemResponses = new ArrayList<>();

            List<PurchaseItem> purchaseItems =
                    purchaseItemRepository.findByPurchaseId(purchase.getId());

            for (PurchaseItem item : purchaseItems) {

                PurchaseItemResponse itemResponse = new PurchaseItemResponse();

                itemResponse.setProductName(item.getProduct().getProductName());
                itemResponse.setQuantity(item.getQuantity());
                itemResponse.setPurchasePrice(item.getPurchasePrice());
                itemResponse.setTotalPrice(item.getTotalPrice());

                itemResponses.add(itemResponse);
            }

            response.setItems(itemResponses);

            responseList.add(response);
        }

        return responseList;
    }

    // Get Purchase By ID
    public PurchaseResponse getPurchaseById(Long id) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        PurchaseResponse response = new PurchaseResponse();

        response.setId(purchase.getId());
        response.setPurchaseNumber(purchase.getPurchaseNumber());
        response.setPurchaseDate(purchase.getPurchaseDate());
        response.setSupplierName(purchase.getSupplier().getSupplierName());
        response.setInvoiceNumber(purchase.getInvoiceNumber());
        response.setTotalAmount(purchase.getTotalAmount());
        response.setStatus(purchase.getStatus());

        List<PurchaseItemResponse> itemResponses = new ArrayList<>();

        List<PurchaseItem> purchaseItems =
                purchaseItemRepository.findByPurchaseId(purchase.getId());

        for (PurchaseItem item : purchaseItems) {

            PurchaseItemResponse itemResponse = new PurchaseItemResponse();

            itemResponse.setProductName(item.getProduct().getProductName());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPurchasePrice(item.getPurchasePrice());
            itemResponse.setTotalPrice(item.getTotalPrice());

            itemResponses.add(itemResponse);
        }

        response.setItems(itemResponses);

        return response;
    }



    private String generatePurchaseNumber(Long id) {

        return String.format("PUR-%06d", id);

    }

}