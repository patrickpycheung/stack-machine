package com.somecompany.service;

import java.util.Stack;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.somecompany.model.StackMachine;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationService {

	@Autowired
	private StackMachine stackMachine;

	Stack<Double> currentStack;
	Stack<Double> backupStack;

	@Value("${errorMsg.paramNotDecimalNum}")
	private String errorParamNotDecimalNum;

	@Value("${errorMsg.stackIsEmpty}")
	private String errorStackIsEmpty;

	@Value("${errorMsg.notEnoughElementsToAdd}")
	private String errorNotEnoughElementsToAdd;

	@Value("${errorMsg.notEnoughElementsToMul}")
	private String errorNotEnoughElementsToMul;

	@PostConstruct
	public void init() {
		currentStack = stackMachine.getCurrentStack();
		backupStack = stackMachine.getBackupStack();
	}

	public void validatePush(String paramStr) throws IllegalArgumentException {
		try {
			Double.valueOf(paramStr);
		} catch (NumberFormatException exception) {
			// Param not a decimal number

			log.error(errorParamNotDecimalNum);
			throw new IllegalArgumentException(errorParamNotDecimalNum);
		}
	}

	public void validatePop() throws IllegalArgumentException {
		if (currentStack.isEmpty()) {
			// Empty stack

			log.error(errorStackIsEmpty);
			throw new IllegalArgumentException(errorStackIsEmpty);
		}
	}

	public void validateAdd() throws IllegalArgumentException {
		if (!(currentStack.size() >= 2)) {
			// Not enough elements to perform add

			log.error(errorNotEnoughElementsToAdd);
			throw new IllegalArgumentException(errorNotEnoughElementsToAdd);
		}
	}

	public void validateMul() throws IllegalArgumentException {
		if (!(currentStack.size() >= 2)) {
			// Not enough elements to perform add

			log.error(errorNotEnoughElementsToMul);
			throw new IllegalArgumentException(errorNotEnoughElementsToMul);
		}
	}

	public void validateNeg() throws IllegalArgumentException {
		if (currentStack.isEmpty()) {
			// Empty stack

			log.error(errorStackIsEmpty);
			throw new IllegalArgumentException(errorStackIsEmpty);
		}
	}
}
