package com.example.iviconsultingtest.payment.service;

import com.example.iviconsultingtest.payment.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse verifyPayment(String reference);

}
