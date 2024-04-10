package com.example.iviconsultingtest.util;

import com.example.iviconsultingtest.cloudinary.dto.CloudiaryResponseDto;
import com.example.iviconsultingtest.invoice.dto.InvoiceDto;

public interface TemplateUtilService {

    String  setStatementAccount(InvoiceDto obj);

    String convertToPdf(String htmlfile);

    CloudiaryResponseDto uploadInvoice(InvoiceDto invoiceDto);

    void sendInvoiceMail(InvoiceDto notificationDto);
}
