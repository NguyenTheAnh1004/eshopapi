package com.eshop.conf;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class converter {
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
