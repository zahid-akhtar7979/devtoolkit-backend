package com.devtoolkit.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuration class for MessageSource setup
 */
@Configuration
public class MessageSourceConfig {

    @Bean
    @Primary
    public MessageSource errorMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("error-messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
} 