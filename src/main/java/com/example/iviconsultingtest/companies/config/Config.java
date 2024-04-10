//package com.example.iviconsultingtest.companies.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring6.view.ThymeleafViewResolver;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//
//@Configuration
//public class Config implements WebMvcConfigurer {
//
//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        System.out.println("getting here");
//        SpringResourceTemplateResolver  resolver = new SpringResourceTemplateResolver ();
//
//        resolver.setPrefix("templates/"); // Location of thymeleaf template
//        resolver.setCacheable(false); // Turning of cache to facilitate template changes
//        resolver.setSuffix(".html"); // Template file extension
//        resolver.setTemplateMode("HTML"); // Template Type
//        resolver.setCharacterEncoding("UTF-8");
//
//        return resolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.setTemplateResolver(templateResolver());
//
//        return engine;
//    }
//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver() {
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine());
//        return viewResolver;
//    }
//}
