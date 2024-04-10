package com.example.iviconsultingtest.companies.card.service;

import com.example.iviconsultingtest.companies.card.req.InitializeTransactionRequest;
import com.example.iviconsultingtest.companies.card.resp.CardResponse;

public interface CardService {

    CardResponse initTransaction(InitializeTransactionRequest request) throws Exception;

    CardResponse verifyTransaction(String reference);
}
