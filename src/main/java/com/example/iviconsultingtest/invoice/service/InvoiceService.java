package com.example.iviconsultingtest.invoice.service;

import com.example.iviconsultingtest.companies.resp.Response;
import com.example.iviconsultingtest.invoice.dto.InvoiceDto;
import com.example.iviconsultingtest.payment.model.Payment;

public interface InvoiceService {

    Response generateInvoice(InvoiceDto invoiceDto);

    Payment createPayment(InvoiceDto invoiceDto);
}
