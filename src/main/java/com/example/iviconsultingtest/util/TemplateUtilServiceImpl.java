package com.example.iviconsultingtest.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.iviconsultingtest.cloudinary.dto.CloudiaryResponseDto;
import com.example.iviconsultingtest.invoice.dto.InvoiceDto;
import com.example.iviconsultingtest.notification.dto.EmailDto;
import com.example.iviconsultingtest.notification.service.NotificationService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j

@Service
@RequiredArgsConstructor
public class TemplateUtilServiceImpl implements TemplateUtilService {

    @Qualifier("templateEngine")
    private final TemplateEngine templateEngine;

    @Autowired
    MessageSource messageSource;


    private final Cloudinary cloudinaryConfig;

   private final NotificationService notificationService;

//    String pdfDirectory= "/home/ubuntu/savingsservice/logs/statement";

   String pdfDirectory= "C:\\Users\\KafkaService";

    LocalDate date = LocalDate.now();
    LocalTime time =LocalTime.now();
    int hour = time.getHour();
    int min = time.getMinute();
    String pdfFileName = "\\invoice"+"-"+date+"-"+hour+"-"+min + ".pdf";

    public String getPdfDirectory() {
        String streamedFileDirectory =  pdfDirectory.replaceAll("\\s+","");
        return streamedFileDirectory;
    }

    public String getPdfFileName() {
        String streamedFilePath =  pdfFileName.replaceAll("\\s+","");
        return streamedFilePath;
    }


    @Override
    public String  setStatementAccount(InvoiceDto obj){


        Context context = new Context();

        context.setVariable("invoiceNumber",obj.getInvoiceNumber());
        context.setVariable("invoiceDate",obj.getInvoiceDate());
        context.setVariable("billFrom",obj.getBillFrom());
        context.setVariable("companyAddress",obj.getCompanyAddress());
        context.setVariable("companyName",obj.getCompanyName());
        context.setVariable("billTo",obj.getBillTo());
        context.setVariable("customerName",obj.getCustomerName());
        context.setVariable("companyAddress",obj.getCompanyAddress());
        context.setVariable("sumTotal",obj.getSumTotal());
        context.setVariable("logo",obj.getLogo());

        Map<String, Object> map = new HashMap<String, Object>();


        map.put("transactions",obj.getTransactions());


        context.setVariables(map);
        String content  = templateEngine.process("invoice", context);
        log.info("gettting the content {}",content);
        return content;
    }

    @Override
    public String convertToPdf(String htmlfile){
        String file = pdfDirectory+pdfFileName;

        try {
            String StreamedFileName =file.replaceAll("\\s+","");

            log.info("StreamedFileName : {}", StreamedFileName);
            log.info("OriginalFileName : {}", file);


            FileOutputStream fileOutputStream = new FileOutputStream(StreamedFileName);
            ITextRenderer renderer = new ITextRenderer();
            log.info("html file {}",htmlfile);
            renderer.setDocumentFromString(htmlfile);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
            return  file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
    @Override
    public CloudiaryResponseDto uploadInvoice(InvoiceDto invoiceDto) {
        try {

            final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//            String filePath = templateUtil.getPdfDirectory()+templateUtil.getPdfFileName();


            File convFile = new File(invoiceDto.getFilePath());

            var result = cloudinaryConfig.uploader().upload(convFile,  ObjectUtils.asMap("resource_type", "auto"));
            convFile.delete();
            return mapper.convertValue(result, CloudiaryResponseDto.class);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendInvoiceMail(InvoiceDto notificationDto) {
        CloudiaryResponseDto responseDto= uploadInvoice(notificationDto);
        log.info("getting the response secure url {}",responseDto.getSecureUrl());
        EmailDto emailDto=new EmailDto();
        emailDto.setEmailAddressTo(notificationDto.getCustomerEmail());
        emailDto.setInvoiceUrl(responseDto.getSecureUrl());
        emailDto.setLogo(notificationDto.getLogo());
        emailDto.setCustomerName(notificationDto.getCustomerName());
        emailDto.setPayStackUrl(notificationDto.getPayStackLink());
        emailDto.setTemplateName("generatelink");
        notificationService.sendEmail(emailDto);

    }



}
