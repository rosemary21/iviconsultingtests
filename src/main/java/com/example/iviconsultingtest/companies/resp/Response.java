package com.example.iviconsultingtest.companies.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {
    private String code;
    private String message;
    private String token;
}
