package com.somecompany.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.somecompany.model.StackMachine;

@Configuration
public class StackMachineConfig {

	@Bean
	public StackMachine getStackMachine() {
		StackMachine stackMachine = new StackMachine();
		return stackMachine;
	}
}
