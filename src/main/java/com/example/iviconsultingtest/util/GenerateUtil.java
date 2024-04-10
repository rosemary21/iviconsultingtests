package com.example.iviconsultingtest.util;

import java.util.Random;

public class GenerateUtil {


    public static  String generateTransactionId() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String value=String.format("%12d", number);
        return value;
    }
}
