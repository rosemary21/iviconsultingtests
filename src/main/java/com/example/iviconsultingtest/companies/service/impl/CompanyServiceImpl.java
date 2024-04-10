package com.example.iviconsultingtest.companies.service.impl;

import com.example.iviconsultingtest.companies.auth.Jwt;
import com.example.iviconsultingtest.companies.card.req.InitializeTransactionRequest;
import com.example.iviconsultingtest.companies.card.resp.CardResponse;
import com.example.iviconsultingtest.companies.card.service.CardService;
import com.example.iviconsultingtest.companies.dto.CompanyDto;
import com.example.iviconsultingtest.companies.dto.MakePaymentDto;
import com.example.iviconsultingtest.companies.model.RegisteredCompany;
import com.example.iviconsultingtest.companies.repository.CompanyRepository;
import com.example.iviconsultingtest.companies.resp.Response;
import com.example.iviconsultingtest.companies.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    CardService cardService;


    @Value("${subscription.amount}")
    private String subAmount;

    @Override
    public Response addCompanies(CompanyDto companyDto) {
        Response response=new Response();
        try{
            RegisteredCompany company= modelMapper.map(companyDto, RegisteredCompany.class);
            companyRepository.save(company);
            String tokenValue= Jwt.defaultCompanyToken(company.getEmailAddress());
            response.setToken(tokenValue);
            response.setCode(messageSource.getMessage("ivi.success",null, Locale.ENGLISH));
            response.setMessage(messageSource.getMessage("company.add.success",null, Locale.ENGLISH));
            return response;
        }catch (Exception e){
            response.setCode(messageSource.getMessage("ivi.error",null, Locale.ENGLISH));
            response.setMessage(messageSource.getMessage("company.add.failure",null, Locale.ENGLISH));
            return response;
        }

    }

    @Override
    public Response makePayment(CompanyDto companyDto) {
  try{
        RegisteredCompany company=companyRepository.findByEmailAddress(companyDto.getEmailAddress());
        InitializeTransactionRequest initializeTransactionRequest=new InitializeTransactionRequest();
        initializeTransactionRequest.setAmount(Integer.valueOf(subAmount));
        initializeTransactionRequest.setEmail(company.getEmailAddress());
        CardResponse cardResponse= cardService.initTransaction(initializeTransactionRequest);
    }
   catch (Exception e){
      e.printStackTrace();
   }



        return null;
    }

    @Override
    public Response verifyPayment(MakePaymentDto makePaymentDto) {
        CardResponse cardResponse=cardService.verifyTransaction(makePaymentDto.getReference());
          RegisteredCompany company=   companyRepository.findByEmailAddress(makePaymentDto.getEmailAddress());
          company.setSubscriptionDone(true);
          companyRepository.save(company);
        return null;
    }
}
