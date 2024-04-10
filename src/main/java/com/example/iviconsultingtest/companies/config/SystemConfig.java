package com.example.iviconsultingtest.companies.config;

import com.cloudinary.Cloudinary;
import com.example.iviconsultingtest.cloudinary.CloudinaryApplicationProperties;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SystemConfig {

    @Autowired
    private CloudinaryApplicationProperties cloudinaryAppProps;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:/messages/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", cloudinaryAppProps.getCloudName());
        config.put("api_key", cloudinaryAppProps.getApiKey());
        config.put("api_secret", cloudinaryAppProps.getApiSecret());
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }



}
