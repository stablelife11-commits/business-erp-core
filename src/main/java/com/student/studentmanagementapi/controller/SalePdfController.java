package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.service.SalePdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
public class SalePdfController {

    @Autowired
    private SalePdfService salePdfService;


    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadSalePdf(
            @PathVariable Long id) {

        try {

            byte[] pdf =
                    salePdfService.generateSalePdf(id);

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=sale-" + id + ".pdf"
                    )
                    .contentType(
                            MediaType.APPLICATION_PDF
                    )
                    .body(pdf);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}