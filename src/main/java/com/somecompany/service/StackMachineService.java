package com.somecompany.service;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somecompany.model.StackMachine;

@Service
public class StackMachineService {

	@Autowired
	private ValidationService validationService;

	@Autowired
	private StackMachine stackMachine;

	public void push(String paramStr) throws IllegalArgumentException {

		// Validate the param
		validationService.validatePush(paramStr);

		Double param = Double.valueOf(paramStr);

		// Backup currentStack
		backup();

		// Push param to stack
		stackMachine.getCurrentStack().push(param);
	}

	private void backup() {
		Stack<Double> backupStack = stackMachine.getBackupStack();
		backupStack.removeAllElements();
		backupStack.addAll(stackMachine.getCurrentStack());
	}
}
