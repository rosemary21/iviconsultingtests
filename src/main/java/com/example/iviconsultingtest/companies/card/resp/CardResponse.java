package com.example.iviconsultingtest.companies.card.resp;

import com.example.iviconsultingtest.companies.resp.Response;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardResponse {
    Response responseDto;
    InitializeTransactionResponse initializeTransactionResponse;
    PaystackVerifyTransactionResponse paystackVerifyTransactionResponse;
}
