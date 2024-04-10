package com.example.iviconsultingtest.companies.card.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitializeTransactionResponse {

    private boolean status;
    private String message;
    private String email;
    private Data data;


}