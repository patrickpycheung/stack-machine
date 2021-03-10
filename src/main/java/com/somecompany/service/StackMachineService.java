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

	public Double neg() throws IllegalArgumentException {

		// Validate the neg command
		validationService.validateNeg();

		// Backup currentStack
		backup();

		Double num = currentStack.pop();

		// Push the negate of the top element to the stack
		return currentStack.push(-1 * num);
	}

	public Double inv() throws IllegalArgumentException {

		// Validate the inv command
		validationService.validateInv();

		// Backup currentStack
		backup();

		Double num = currentStack.pop();

		// Push the inverse of the top element to the stack
		return currentStack.push(1 / num);
	}

	public void undo() throws IllegalArgumentException {

		// Replace currentStack elements with that of backupStack
		currentStack.removeAllElements();
		currentStack.addAll(backupStack);
	}

	private void backup() {
		backupStack.removeAllElements();
		backupStack.addAll(currentStack);
	}

}
