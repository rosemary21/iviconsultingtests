package com.example.iviconsultingtest.companies.controller;

import com.example.iviconsultingtest.cloudinary.CloudinaryStorageService;
import com.example.iviconsultingtest.companies.dto.CompanyDto;
import com.example.iviconsultingtest.companies.resp.Response;
import com.example.iviconsultingtest.companies.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;

@RestController
@Slf4j
@RequestMapping("/api/v1/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    CloudinaryStorageService cloudinaryStorageService;

    @PostMapping("/add")
    public ResponseEntity<Response> addCompany(@RequestParam("logo") MultipartFile file,@RequestParam("name") String name,@RequestParam("emailAddress") String emailAddress,@RequestParam("address") String address,@RequestParam("password") String password){
        Response response=null;
        try{
            CompanyDto companyDto=new CompanyDto();
            var cloudinaryResponse = cloudinaryStorageService.uploadImage(file);
            companyDto.setLogo(cloudinaryResponse.getSecureUrl());
            companyDto.setName(name);
            companyDto.setAddress(address);
            companyDto.setEmailAddress(emailAddress);
            companyDto.setPassword(password);
            response= companyService.addCompanies(companyDto);
            log.info("getting the code {}",response.getCode());
            if(response.getCode().equalsIgnoreCase(messageSource.getMessage("company.add.success",null, Locale.ENGLISH))){
                return new ResponseEntity<Response>(response, HttpStatus.OK);
            }
            else
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception e){

        }
        return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);

    }





}
