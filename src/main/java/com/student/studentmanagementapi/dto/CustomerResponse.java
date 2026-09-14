package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private Long id;

    private String name;

    private String mobile;

    private String email;

    private String address;

    private Boolean status;
}
