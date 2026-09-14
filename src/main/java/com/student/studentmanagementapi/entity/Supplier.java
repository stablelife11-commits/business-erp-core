package com.student.studentmanagementapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "suppliers")
@Data
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Supplier name is required")
    private String supplierName;

    private String companyName;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String mobile;

    @Email(message = "Invalid email")
    private String email;

    @Column(unique = true)
    private String gstNumber;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String status = "ACTIVE";
}