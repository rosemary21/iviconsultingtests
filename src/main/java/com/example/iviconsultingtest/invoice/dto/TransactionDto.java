package com.example.iviconsultingtest.invoice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransactionDto {
    private String item;
    private int quantity;
    private BigDecimal total;
}
