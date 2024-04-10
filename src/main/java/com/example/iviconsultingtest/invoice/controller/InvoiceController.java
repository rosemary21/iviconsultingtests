package com.example.iviconsultingtest.invoice.controller;


import com.example.iviconsultingtest.companies.resp.Response;
import com.example.iviconsultingtest.invoice.dto.InvoiceDto;
import com.example.iviconsultingtest.invoice.service.InvoiceService;
import com.example.iviconsultingtest.payment.dto.PaymentResponse;
import com.example.iviconsultingtest.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    MessageSource messageSource;

    @PostMapping("/generate")
    public ResponseEntity<Response> addCompany(@RequestBody InvoiceDto invoiceDto){

        Response response= invoiceService.generateInvoice(invoiceDto);
        if(response.getCode().equalsIgnoreCase(messageSource.getMessage("invoice.generate.success",null, Locale.ENGLISH))){
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{reference}" ,method = RequestMethod.POST)
    public ResponseEntity<PaymentResponse> verifyPayment(@RequestParam("trxref") String trxref,@RequestParam("reference") String reference) {

        PaymentResponse paymentResponse= paymentService.verifyPayment(reference);

        if(paymentResponse.getCode().equalsIgnoreCase(messageSource.getMessage("verify.payment.success",null, Locale.ENGLISH))){
            return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.OK);
        }
        else
            return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.BAD_REQUEST);
    }

}

