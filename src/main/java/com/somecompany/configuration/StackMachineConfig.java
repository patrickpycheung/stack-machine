package com.somecompany.configuration;

import java.util.Stack;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.somecompany.model.StackMachine;

/**
 * Configuration for the Stack Machine application.
 * 
 * @author patrick
 */
@Configuration
public class StackMachineConfig {

	@Bean
	public StackMachine getStackMachine() {
		StackMachine stackMachine = new StackMachine();
		stackMachine.setCurrentStack(new Stack<Double>());
		stackMachine.setBackupStack(new Stack<Double>());
		return stackMachine;
	}
}
