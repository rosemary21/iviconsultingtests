package com.example.iviconsultingtest.companies.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyDto {
    private String name;
    private String address;
    private String logo;
    private String emailAddress;
    private String password;
    private boolean isSubscriptionDone;
}
