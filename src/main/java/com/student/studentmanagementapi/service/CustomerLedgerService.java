package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.dto.CustomerLedgerEntryResponse;
import com.student.studentmanagementapi.dto.CustomerLedgerResponse;
import com.student.studentmanagementapi.entity.Customer;
import com.student.studentmanagementapi.entity.CustomerPayment;
import com.student.studentmanagementapi.entity.Sale;
import com.student.studentmanagementapi.repository.CustomerPaymentRepository;
import com.student.studentmanagementapi.repository.CustomerRepository;
import com.student.studentmanagementapi.repository.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CustomerLedgerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CustomerPaymentRepository customerPaymentRepository;


    // ==========================================
    // CUSTOMER LEDGER
    // ==========================================

    public CustomerLedgerResponse getCustomerLedger(Long customerId) {

        // ------------------------------------------
        // FIND CUSTOMER
        // ------------------------------------------

        Customer customer =
                customerRepository.findById(customerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer not found: " + customerId
                                )
                        );


        // ------------------------------------------
        // FIND CUSTOMER SALES
        // ------------------------------------------

        List<Sale> sales =
                saleRepository.findByCustomerMobile(
                        customer.getMobile()
                );


        // ------------------------------------------
        // FIND CUSTOMER PAYMENTS
        // ------------------------------------------

        List<CustomerPayment> payments =
                customerPaymentRepository.findByCustomerId(
                        customerId
                );


        // ------------------------------------------
        // RESPONSE
        // ------------------------------------------

        CustomerLedgerResponse response =
                new CustomerLedgerResponse();

        response.setCustomerId(customer.getId());
        response.setCustomerName(customer.getName());
        response.setMobile(customer.getMobile());


        // ------------------------------------------
        // TOTALS
        // ------------------------------------------

        BigDecimal totalSales = BigDecimal.ZERO;

        for (Sale sale : sales) {

            if (sale.getTotalAmount() != null) {

                totalSales =
                        totalSales.add(
                                sale.getTotalAmount()
                        );
            }
        }


        BigDecimal totalPaid = BigDecimal.ZERO;

        for (CustomerPayment payment : payments) {

            if (payment.getAmount() != null) {

                totalPaid =
                        totalPaid.add(
                                payment.getAmount()
                        );
            }
        }


        BigDecimal outstanding =
                totalSales.subtract(totalPaid);


        response.setTotalSales(totalSales);
        response.setTotalPaid(totalPaid);
        response.setOutstanding(outstanding);


        // ------------------------------------------
        // CREATE LEDGER ENTRIES
        // ------------------------------------------

        List<CustomerLedgerEntryResponse> entries =
                new ArrayList<>();


        // ------------------------------------------
        // SALES → DEBIT
        // ------------------------------------------

        for (Sale sale : sales) {

            CustomerLedgerEntryResponse entry =
                    new CustomerLedgerEntryResponse();

            entry.setDate(sale.getSaleDate());

            entry.setType("SALE");

            entry.setReferenceNumber(
                    sale.getSaleNumber()
            );

            entry.setDescription("Sale");

            entry.setDebit(
                    sale.getTotalAmount()
            );

            entry.setCredit(BigDecimal.ZERO);

            entries.add(entry);
        }


        // ------------------------------------------
        // PAYMENTS → CREDIT
        // ------------------------------------------

        for (CustomerPayment payment : payments) {

            CustomerLedgerEntryResponse entry =
                    new CustomerLedgerEntryResponse();

            entry.setDate(payment.getPaymentDate());

            entry.setType("PAYMENT");

            entry.setReferenceNumber(
                    payment.getReferenceNumber()
            );

            entry.setDescription(
                    payment.getPaymentMode()
            );

            entry.setDebit(BigDecimal.ZERO);

            entry.setCredit(
                    payment.getAmount()
            );

            entries.add(entry);
        }


        // ------------------------------------------
        // SORT BY DATE
        // ------------------------------------------

        entries.sort(
                Comparator.comparing(
                        CustomerLedgerEntryResponse::getDate
                )
        );


        // ------------------------------------------
        // RUNNING BALANCE
        // ------------------------------------------

        BigDecimal balance = BigDecimal.ZERO;

        for (CustomerLedgerEntryResponse entry : entries) {

            balance =
                    balance
                            .add(
                                    entry.getDebit()
                            )
                            .subtract(
                                    entry.getCredit()
                            );

            entry.setBalance(balance);
        }


        response.setEntries(entries);

        return response;
    }
}