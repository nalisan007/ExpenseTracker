package io.expensetracker.ExpenseTracker.restApi.EntityMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	@Bean
	public static ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
