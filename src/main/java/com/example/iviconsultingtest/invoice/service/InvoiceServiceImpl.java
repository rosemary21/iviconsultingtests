package com.example.iviconsultingtest.invoice.service;

import com.example.iviconsultingtest.companies.card.req.InitializeTransactionRequest;
import com.example.iviconsultingtest.companies.card.resp.CardResponse;
import com.example.iviconsultingtest.companies.card.service.CardService;
import com.example.iviconsultingtest.companies.model.RegisteredCompany;
import com.example.iviconsultingtest.companies.repository.CompanyRepository;
import com.example.iviconsultingtest.companies.resp.Response;
import com.example.iviconsultingtest.invoice.dto.InvoiceDto;
import com.example.iviconsultingtest.invoice.dto.TransactionDto;
import com.example.iviconsultingtest.payment.model.Payment;
import com.example.iviconsultingtest.payment.model.Transaction;
import com.example.iviconsultingtest.payment.repository.PaymentRepository;
import com.example.iviconsultingtest.payment.repository.TransactionRepository;
import com.example.iviconsultingtest.util.GenerateUtil;
import com.example.iviconsultingtest.util.TemplateUtilService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService{



    @Autowired
    CardService cardService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TemplateUtilService templateUtilService;

    @Override
    public Response generateInvoice(InvoiceDto invoiceDto) {
        try{
            Response response=new Response();
            InitializeTransactionRequest initializeTransactionRequest=new InitializeTransactionRequest();

            log.info("About Creating Payment");
            Payment payment=  createPayment(invoiceDto);
            initializeTransactionRequest.setEmail(invoiceDto.getCustomerEmail());
            BigDecimal value=payment.getSumTotal().multiply(new BigDecimal(100));
            initializeTransactionRequest.setAmount(value.intValue());
            RegisteredCompany company=companyRepository.findByEmailAddress(invoiceDto.getCompanyEmailAddress());
            String id= GenerateUtil.generateTransactionId();
            invoiceDto.setInvoiceNumber(id);
            invoiceDto.setInvoiceDate(new Date());
            invoiceDto.setSumTotal(payment.getSumTotal());
            CardResponse cardResponse= cardService.initTransaction(initializeTransactionRequest);
            invoiceDto.setLogo(company.getLogo());
            log.info("getting the response card url{}",cardResponse.getInitializeTransactionResponse().getData().getAuthorization_url());
            invoiceDto.setPayStackLink(cardResponse.getInitializeTransactionResponse().getData().getAuthorization_url());
            log.info("getting the logo {}",invoiceDto.getLogo());
            var file = templateUtilService.setStatementAccount(invoiceDto);
            String url= templateUtilService.convertToPdf(file);
            log.info("getting the invoice dto {}",invoiceDto);
            invoiceDto.setFilePath(url);
            templateUtilService.sendInvoiceMail(invoiceDto);
            response.setCode(messageSource.getMessage("ivi.success",null, Locale.ENGLISH));
            response.setMessage(messageSource.getMessage("invoice.generate.success",null, Locale.ENGLISH));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            Response response=new Response();
            response.setCode(messageSource.getMessage("ivi.failure",null, Locale.ENGLISH));
            response.setMessage(messageSource.getMessage("invoice.generate.failed",null, Locale.ENGLISH));
            return response;
        }

    }

    @Override
    public   Payment  createPayment(InvoiceDto invoiceDto){
        Payment payment=new Payment();
       RegisteredCompany company= companyRepository.findByEmailAddress(invoiceDto.getCompanyAddress());
        List<Transaction> transactionList=new ArrayList<>();
        BigDecimal totalAmount=new BigDecimal(0);
        for(TransactionDto transactionDto: invoiceDto.getTransactions()){
          Transaction transaction1=  modelMapper.map(transactionDto,Transaction.class);
          Transaction transaction=transactionRepository.save(transaction1);
          transactionList.add(transaction);
          totalAmount=totalAmount.add(transaction.getTotal());
        }
        payment.setPaid(false);
        payment.setInvoiceDate(invoiceDto.getInvoiceDate());
        payment.setInvoiceNumber(invoiceDto.getInvoiceNumber());
        payment.setCustomerName(invoiceDto.getCustomerName());
        payment.setTransactionList(transactionList);
        payment.setSumTotal(totalAmount);
        payment.setCompany(company);
        payment.setCustomerAddress(invoiceDto.getCustomerAddress());
       Payment payment1=  paymentRepository.save(payment);
       return payment1;
    }
}
