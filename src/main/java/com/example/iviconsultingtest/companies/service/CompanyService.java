package com.example.iviconsultingtest.companies.service;

import com.example.iviconsultingtest.companies.dto.CompanyDto;
import com.example.iviconsultingtest.companies.dto.MakePaymentDto;
import com.example.iviconsultingtest.companies.resp.Response;

public interface CompanyService {

    Response addCompanies(CompanyDto companyDto);

    Response makePayment(CompanyDto companyDto) ;

    Response verifyPayment(MakePaymentDto makePaymentDto);
}
