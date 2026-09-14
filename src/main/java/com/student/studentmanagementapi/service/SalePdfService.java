package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Product;
import com.student.studentmanagementapi.entity.Sale;
import com.student.studentmanagementapi.entity.SaleItem;
import com.student.studentmanagementapi.repository.SaleRepository;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SalePdfService {

    @Autowired
    private SaleRepository saleRepository;


    // ==========================================
    // GENERATE SALE PDF
    // ==========================================

    @Transactional(readOnly = true)
    public byte[] generateSalePdf(Long saleId) throws IOException {

        Optional<Sale> optionalSale =
                saleRepository.findById(saleId);

        if (optionalSale.isEmpty()) {
            throw new RuntimeException(
                    "Sale not found: " + saleId
            );
        }

        Sale sale = optionalSale.get();

        // Force loading of Sale Items
        sale.getItems().size();

        // Force loading of Product details
        for (SaleItem item : sale.getItems()) {

            if (item.getProduct() != null) {
                item.getProduct().getProductName();
            }
        }


        try (PDDocument document = new PDDocument()) {

            PDPage page =
                    new PDPage(PDRectangle.A4);

            document.addPage(page);


            try (PDPageContentStream content =
                         new PDPageContentStream(
                                 document,
                                 page
                         )) {

                float y = 800;


                // ==========================================
                // FONTS
                // ==========================================

                PDType1Font boldFont =
                        new PDType1Font(
                                Standard14Fonts.FontName.HELVETICA_BOLD
                        );

                PDType1Font normalFont =
                        new PDType1Font(
                                Standard14Fonts.FontName.HELVETICA
                        );


                // ==========================================
                // BUSINESS HEADER
                // ==========================================

                content.setFont(
                        boldFont,
                        18
                );

                y = writeLine(
                        content,
                        "BUSINESS ERP",
                        50,
                        y
                );


                y -= 5;

                content.setFont(
                        boldFont,
                        14
                );

                y = writeLine(
                        content,
                        "SALE INVOICE",
                        50,
                        y
                );


                // ==========================================
                // SALE DETAILS
                // ==========================================

                y -= 20;

                content.setFont(
                        normalFont,
                        10
                );

                y = writeLine(
                        content,
                        "Invoice No: "
                                + safe(sale.getSaleNumber()),
                        50,
                        y
                );

                y = writeLine(
                        content,
                        "Date: "
                                + safe(sale.getSaleDate()),
                        50,
                        y
                );

                y = writeLine(
                        content,
                        "Customer: "
                                + safe(sale.getCustomerName()),
                        50,
                        y
                );

                y = writeLine(
                        content,
                        "Mobile: "
                                + safe(sale.getCustomerMobile()),
                        50,
                        y
                );


                // ==========================================
                // ITEM TABLE
                // ==========================================

                y -= 20;

                content.setFont(
                        boldFont,
                        10
                );

                writeLine(
                        content,
                        "Product",
                        50,
                        y
                );

                writeLine(
                        content,
                        "Qty",
                        300,
                        y
                );

                writeLine(
                        content,
                        "Rate",
                        350,
                        y
                );

                writeLine(
                        content,
                        "Total",
                        440,
                        y
                );


                y -= 18;


                // ==========================================
                // LINE
                // ==========================================

                content.moveTo(
                        50,
                        y
                );

                content.lineTo(
                        540,
                        y
                );

                content.stroke();

                y -= 18;


                // ==========================================
                // SALE ITEMS
                // ==========================================

                content.setFont(
                        normalFont,
                        10
                );

                if (sale.getItems() != null &&
                        !sale.getItems().isEmpty()) {

                    for (SaleItem item :
                            sale.getItems()) {

                        Product product =
                                item.getProduct();

                        String productName =
                                "Unknown Product";

                        if (product != null) {

                            productName =
                                    safe(
                                            product.getProductName()
                                    );
                        }


                        // Product
                        writeLine(
                                content,
                                productName,
                                50,
                                y
                        );


                        // Quantity
                        writeLine(
                                content,
                                safe(item.getQuantity()),
                                300,
                                y
                        );


                        // Rate
                        writeLine(
                                content,
                                safeMoney(
                                        item.getSalePrice()
                                ),
                                350,
                                y
                        );


                        // Total
                        writeLine(
                                content,
                                safeMoney(
                                        item.getTotalPrice()
                                ),
                                440,
                                y
                        );


                        y -= 20;


                        // ==================================
                        // NEW PAGE PROTECTION
                        // ==================================

                        if (y < 100) {
                            break;
                        }
                    }

                } else {

                    writeLine(
                            content,
                            "No items found",
                            50,
                            y
                    );

                    y -= 20;
                }


                // ==========================================
                // TABLE BOTTOM LINE
                // ==========================================

                content.moveTo(
                        50,
                        y
                );

                content.lineTo(
                        540,
                        y
                );

                content.stroke();


                // ==========================================
                // GRAND TOTAL
                // ==========================================

                y -= 30;

                content.setFont(
                        boldFont,
                        12
                );

                writeLine(
                        content,
                        "Grand Total: Rs. "
                                + safeMoney(
                                sale.getTotalAmount()
                        ),
                        50,
                        y
                );


                // ==========================================
                // PAYMENT MODE
                // ==========================================

                y -= 25;

                content.setFont(
                        normalFont,
                        10
                );

                writeLine(
                        content,
                        "Payment Mode: "
                                + safe(
                                sale.getPaymentMode()
                        ),
                        50,
                        y
                );


                // ==========================================
                // REMARKS
                // ==========================================

                y -= 20;

                writeLine(
                        content,
                        "Remarks: "
                                + safe(
                                sale.getRemarks()
                        ),
                        50,
                        y
                );


                // ==========================================
                // FOOTER
                // ==========================================

                content.setFont(
                        normalFont,
                        9
                );

                writeLine(
                        content,
                        "Generated by Business ERP",
                        50,
                        60
                );
            }


            // ==========================================
            // PDF → BYTE[]
            // ==========================================

            ByteArrayOutputStream output =
                    new ByteArrayOutputStream();

            document.save(output);

            return output.toByteArray();
        }
    }


    // ==========================================
    // WRITE LINE
    // ==========================================

    private float writeLine(
            PDPageContentStream content,
            String text,
            float x,
            float y
    ) throws IOException {

        content.beginText();

        content.newLineAtOffset(
                x,
                y
        );

        content.showText(
                text
        );

        content.endText();

        return y - 15;
    }


    // ==========================================
    // SAFE STRING
    // ==========================================

    private String safe(
            Object value
    ) {

        if (value == null) {
            return "";
        }

        return value.toString();
    }


    // ==========================================
    // SAFE MONEY
    // ==========================================

    private String safeMoney(
            BigDecimal value
    ) {

        if (value == null) {
            return "0.00";
        }

        return value
                .setScale(
                        2
                )
                .toString();
    }
}