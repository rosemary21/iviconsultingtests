package com.example.iviconsultingtest.companies.card.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class InitializeTransactionRequest {

    private Integer amount;

    private String email;
    private String plan;
    private String reference;
    private String subaccount;
    private String callback_url;
    private Float quantity;
    private Integer invoice_limit;
    private Integer transaction_charge;
    private List<String> channel;
}
