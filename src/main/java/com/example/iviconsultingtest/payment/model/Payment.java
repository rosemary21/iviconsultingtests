package com.example.iviconsultingtest.payment.model;

import com.example.iviconsultingtest.companies.model.RegisteredCompany;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;
    private String invoiceNumber;
    private String customerName;
    private String customerAddress;
    private Date invoiceDate;
    @OneToMany
    private List<Transaction> transactionList;

    @ManyToOne
    private RegisteredCompany company;
    private boolean paid;
    private String reference;
    private BigDecimal sumTotal;
}
