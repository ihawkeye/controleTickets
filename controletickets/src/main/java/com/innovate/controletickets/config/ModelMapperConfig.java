package com.innovate.controletickets.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    // produz um objeto.
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
