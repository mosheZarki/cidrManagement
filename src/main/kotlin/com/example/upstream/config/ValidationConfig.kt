package com.example.upstream.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.validation.Validation
import javax.validation.Validator

@Configuration
class ValidationConfig {

    @Bean
    fun validator(): Validator {
        return Validation.buildDefaultValidatorFactory().validator
    }
}
