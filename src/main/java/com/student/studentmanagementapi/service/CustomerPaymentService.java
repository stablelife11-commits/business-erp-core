package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Customer;
import com.student.studentmanagementapi.entity.CustomerPayment;
import com.student.studentmanagementapi.entity.Sale;
import com.student.studentmanagementapi.repository.CustomerPaymentRepository;
import com.student.studentmanagementapi.repository.CustomerRepository;
import com.student.studentmanagementapi.repository.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerPaymentService {

    @Autowired
    private CustomerPaymentRepository customerPaymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;


    // ==========================================
    // ADD CUSTOMER PAYMENT
    // ==========================================

    public CustomerPayment addPayment(
            Long customerId,
            Long saleId,
            CustomerPayment payment) {

        // ------------------------------------------
        // Validate Customer
        // ------------------------------------------

        Customer customer =
                customerRepository.findById(customerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer not found: " + customerId
                                )
                        );

        payment.setCustomer(customer);


        // ------------------------------------------
        // Validate Sale - OPTIONAL
        // ------------------------------------------

        if (saleId != null) {

            Sale sale =
                    saleRepository.findById(saleId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Sale not found: " + saleId
                                    )
                            );


            // ------------------------------------------
            // Validate Sale Belongs To Customer
            // ------------------------------------------

            if (sale.getCustomerMobile() == null ||
                    !sale.getCustomerMobile()
                            .equals(customer.getMobile())) {

                throw new RuntimeException(
                        "This sale does not belong to the selected customer"
                );
            }

            payment.setSale(sale);
        }


        // ------------------------------------------
        // Validate Amount
        // ------------------------------------------

        if (payment.getAmount() == null ||
                payment.getAmount().compareTo(
                        BigDecimal.ZERO
                ) <= 0) {

            throw new RuntimeException(
                    "Payment amount must be greater than zero"
            );
        }


        // ------------------------------------------
        // Save Payment
        // ------------------------------------------

        return customerPaymentRepository.save(payment);
    }


    // ==========================================
    // GET ALL PAYMENTS
    // ==========================================

    public List<CustomerPayment> getAllPayments() {

        return customerPaymentRepository.findAll();
    }


    // ==========================================
    // GET PAYMENT BY ID
    // ==========================================

    public CustomerPayment getPaymentById(Long id) {

        return customerPaymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found: " + id
                        )
                );
    }


    // ==========================================
    // DELETE PAYMENT
    // ==========================================

    public void deletePayment(Long id) {

        if (!customerPaymentRepository.existsById(id)) {

            throw new RuntimeException(
                    "Payment not found: " + id
            );
        }

        customerPaymentRepository.deleteById(id);
    }
}