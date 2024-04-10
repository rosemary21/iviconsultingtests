package com.example.iviconsultingtest.invoice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class InvoiceDto {
    private String billFrom;
    private String invoiceNumber;
    private Date invoiceDate;
    private String billTo;
    private String companyAddress;
    private String companyEmailAddress;
    private String customerName;
    private String customerAddress;
    private String companyName;
    private String customerEmail;
    private BigDecimal sumTotal;
    private String payStackLink;
    private String filePath;
    private String logo;
    private List<TransactionDto> transactions;


}
