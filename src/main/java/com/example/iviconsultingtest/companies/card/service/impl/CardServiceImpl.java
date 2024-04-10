package com.example.iviconsultingtest.companies.card.service.impl;

import com.example.iviconsultingtest.companies.card.req.InitializeTransactionRequest;
import com.example.iviconsultingtest.companies.card.resp.CardResponse;
import com.example.iviconsultingtest.companies.card.resp.InitializeTransactionResponse;
import com.example.iviconsultingtest.companies.card.resp.PaystackVerifyTransactionResponse;
import com.example.iviconsultingtest.companies.card.service.CardService;
import com.example.iviconsultingtest.companies.resp.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

@Service
@Slf4j
public class CardServiceImpl implements CardService {

    @Autowired
    MessageSource messageSource;

    @Value("${paystack.secret.key.url}")
    private String PAYSTACK_SECRET_KEY;

    @Value("${paystack.base.url}")
    private String PAYSTACK_BASE_URL;

    @Value("${paystack.initialize.url}")
    private String PAYSTACK_INITIAL_URL;

    @Override
    public CardResponse initTransaction(InitializeTransactionRequest request) throws Exception {
        CardResponse cardResponse=new CardResponse();
        Response responseDto=new Response();
        InitializeTransactionResponse initializeTransactionResponse = null;
        try {
            // convert transaction to json then use it as a body to post json
            Gson gson = new Gson();
            // add paystack chrges to the amount
            log.info("getting the request"+request.getEmail());
            StringEntity postingString = new StringEntity(gson.toJson(request));
            System.out.println("post String"+postingString);
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_BASE_URL+PAYSTACK_INITIAL_URL);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer "+PAYSTACK_SECRET_KEY);
            StringBuilder result = new StringBuilder();
            org.apache.http.HttpResponse response = client.execute(post);
            System.out.println("Entry getting the response"+response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new Exception("Error Occurred while initializing transaction");
            }
            ObjectMapper mapper = new ObjectMapper();

            initializeTransactionResponse = mapper.readValue(result.toString(), InitializeTransactionResponse.class);
            cardResponse.setInitializeTransactionResponse(initializeTransactionResponse);
            log.info("card response {}",mapper.writeValueAsString(initializeTransactionResponse));
            responseDto.setCode(messageSource.getMessage("ivi.success",null, Locale.ENGLISH));
            responseDto.setMessage(messageSource.getMessage("card.charge.success",null,Locale.ENGLISH));
            cardResponse.setResponseDto(responseDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failure initializaing paystack transaction");
        }

        return cardResponse;
    }

    @Override
    public CardResponse verifyTransaction(String reference){
        CardResponse cardResponse=new CardResponse();
        Response responseDto=new Response();
        PaystackVerifyTransactionResponse paystackresponse = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            System.out.println("Reference "+reference);
            HttpGet request = new HttpGet("https://api.paystack.co/transaction/verify/"+reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);
            System.out.println("response.getStatusLine()"+ response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                System.out.println("Getting the spring builder"+result.toString());

            } else {
                throw new Exception("Error Occured while connecting to paystack url");
            }
            ObjectMapper mapper = new ObjectMapper();

            paystackresponse = mapper.readValue(result.toString(), PaystackVerifyTransactionResponse.class);
            cardResponse.setPaystackVerifyTransactionResponse(paystackresponse);
            responseDto.setCode(messageSource.getMessage("ivi.success",null, Locale.ENGLISH));
            responseDto.setMessage(messageSource.getMessage("card.verify.success",null,Locale.ENGLISH));
            cardResponse.setResponseDto(responseDto);

            if (paystackresponse == null || !(paystackresponse.isStatus())) {
                throw new Exception("An error occurred while  verifying payment");
            } else if (paystackresponse.getData().getStatus().equals("success")) {
                //PAYMENT IS SUCCESSFUL, APPLY VALUE TO THE TRANSACTION
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //  throw new Exception("Internal server error");
        }
        return cardResponse;
    }

}
