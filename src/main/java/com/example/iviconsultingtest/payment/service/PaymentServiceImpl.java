package com.example.iviconsultingtest.payment.service;

import com.example.iviconsultingtest.companies.card.resp.CardResponse;
import com.example.iviconsultingtest.companies.card.service.CardService;
import com.example.iviconsultingtest.companies.resp.Response;
import com.example.iviconsultingtest.payment.dto.PaymentResponse;
import com.example.iviconsultingtest.payment.model.Payment;
import com.example.iviconsultingtest.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    CardService cardService;
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    MessageSource messageSource;
    @Override
    public PaymentResponse verifyPayment(String reference) {
        PaymentResponse response=new PaymentResponse();
        CardResponse cardResponse= cardService.verifyTransaction(reference);
        if(cardResponse.getResponseDto().getCode().equalsIgnoreCase(messageSource.getMessage("ivi.success",null, Locale.ENGLISH))){
            Payment payment= paymentRepository.findByReference(reference);
            payment.setPaid(true);
            paymentRepository.save(payment);
        }
        response.setCode(messageSource.getMessage("ivi.success",null, Locale.ENGLISH));
        response.setMessage(messageSource.getMessage("invoice.generate.success",null, Locale.ENGLISH));
        return response;
    }
}
