package com.example.iviconsultingtest;

import com.example.iviconsultingtest.companies.dto.CompanyDto;
import com.example.iviconsultingtest.companies.service.CompanyService;
import com.example.iviconsultingtest.invoice.dto.InvoiceDto;
import com.example.iviconsultingtest.invoice.dto.TransactionDto;
import com.example.iviconsultingtest.invoice.service.InvoiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class IviconsultingtestApplicationTests {

    @Autowired
    CompanyService companyService;

    @Autowired
    InvoiceService invoiceService;

    @Test
    void addCompany() {
        CompanyDto companyDto=new CompanyDto();
        companyDto.setPassword("IVI@123");
        companyDto.setAddress("5 shobande street");
        companyDto.setEmailAddress("chiomachukelu1@gmail.com");
        companyDto.setName("Global Limited");
        companyDto.setLogo("https://res.cloudinary.com/dsdhgipbp/image/upload/v1712736059/lunftbiv9idl86cgckpj.png");
        companyService.addCompanies(companyDto);
    }


    @Test
    void generateInvoiceAndPaymentLink() {
        InvoiceDto invoiceDto=new InvoiceDto();
        TransactionDto transactionDto=new TransactionDto();
        transactionDto.setItem("video game");
        transactionDto.setQuantity(2);
        transactionDto.setTotal(new BigDecimal(2000));
        List<TransactionDto> transactionDtoList=new ArrayList<>();
        transactionDtoList.add(transactionDto);
        TransactionDto transactionDto1=new TransactionDto();
        transactionDto1.setItem("Clothing");
        transactionDto1.setQuantity(2);
        transactionDto1.setTotal(new BigDecimal(2000));
        transactionDtoList.add(transactionDto1);
        invoiceDto.setCustomerAddress("Victoria Island Lagos");
        invoiceDto.setCustomerName("IVI Consulting");
        invoiceDto.setCustomerAddress("Victoria Island Lagos");
        invoiceDto.setCustomerEmail("chukeluchioma408@yahoo.com");
        invoiceDto.setCompanyEmailAddress("chiomachukelu1@gmail.com");
        invoiceDto.setTransactions(transactionDtoList);
       invoiceService.generateInvoice(invoiceDto);
    }

}
