package com.business.erp.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    private String name;

    private String mobile;

    private String email;

    private String address;
}
