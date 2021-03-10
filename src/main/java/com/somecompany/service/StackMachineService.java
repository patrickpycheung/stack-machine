package com.somecompany.service;

import java.util.Stack;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somecompany.model.StackMachine;

@Service
public class StackMachineService {

	@Autowired
	private ValidationService validationService;

	@Autowired
	private StackMachine stackMachine;

	Stack<Double> currentStack;
	Stack<Double> backupStack;

	@PostConstruct
	public void init() {
		currentStack = stackMachine.getCurrentStack();
		backupStack = stackMachine.getBackupStack();
	}

	public Double push(String paramStr) throws IllegalArgumentException {

		// Validate the param
		validationService.validatePush(paramStr);

		Double param = Double.valueOf(paramStr);

		// Backup currentStack
		backup();

		// Push param to stack
		currentStack.push(param);
		return currentStack.peek();
	}

	public void pop() throws IllegalArgumentException {

		// Validate the pop command
		validationService.validatePop();

		// Backup currentStack
		backup();

		// Pop top element from stack
		currentStack.pop();
	}

	public void clear() throws IllegalArgumentException {

		// Backup currentStack
		backup();

		// Remove all elements from the stack
		currentStack.removeAllElements();
	}

	public Double add() throws IllegalArgumentException {

		// Validate the add command
		validationService.validateAdd();

		// Backup currentStack
		backup();

		Double firstNum = currentStack.pop();
		Double secondNum = currentStack.pop();

		// Push sum of top 2 elements to the stack
		return currentStack.push(firstNum + secondNum);
	}

	public Double mul() throws IllegalArgumentException {

		// Validate the add command
		validationService.validateMul();

		// Backup currentStack
		backup();

		Double firstNum = currentStack.pop();
		Double secondNum = currentStack.pop();

		// Push product of top 2 elements to the stack
		return currentStack.push(firstNum * secondNum);
	}

	private void backup() {
		backupStack.removeAllElements();
		backupStack.addAll(currentStack);
	}

}
