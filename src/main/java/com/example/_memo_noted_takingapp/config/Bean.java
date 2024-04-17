package com.example._memo_noted_takingapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Bean {
    @org.springframework.context.annotation.Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
