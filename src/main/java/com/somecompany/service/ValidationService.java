package com.somecompany.service;

import java.util.Stack;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.somecompany.model.Command;
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

	@Value("${errorMsg.zeroCannotBeInverted}")
	private String errorZeroCannotBeInverted;

	@PostConstruct
	public void init() {
		currentStack = stackMachine.getCurrentStack();
		backupStack = stackMachine.getBackupStack();
	}

	public void validateUsrInput(String usrInput) {
		if (usrInput == null || usrInput.equals("")) {
			// Null or empty input

			log.error("Error: Empty input not allowed!");
			throw new IllegalArgumentException("Error: Empty input not allowed!");
		}

		String[] usrInputArr = usrInput.split(" ");

		String command = usrInputArr[0];

		// Check if it is "direct push" (i.e. Just provide the value to push without the "PUSH" word)

		try {
			// Check is double
			Double.valueOf(command);

			if (usrInputArr.length > 1) {
				// Invalid "direct push" input

				log.error("Error: Invalid input!");
				throw new IllegalArgumentException("Error: Invalid input!");
			}

			// Is valid "direct push"
			return;
		} catch (NumberFormatException exception) {
			// The command is not pure double
			// Just continue to see if it is other commands
		}

		command = command.toUpperCase();

		if (command.equals(Command.PUSH.name())) {
			// PUSH command

			validatePushUsrInput(usrInputArr);
		} else if (command.equals(Command.POP.name()) || command.equals(Command.CLEAR.name())
				|| command.equals(Command.ADD.name()) || command.equals(Command.MUL.name())
				|| command.equals(Command.NEG.name()) || command.equals(Command.INV.name())
				|| command.equals(Command.UNDO.name()) || command.equals(Command.PRINT.name())
				|| command.equals(Command.QUIT.name())) {
			// Other commands

			validateNonPushUsrInput(usrInputArr);
		} else {
			// Invalid command

			log.error("Error: Invalid input!");
			throw new IllegalArgumentException("Error: Invalid input!");
		}
	}

	private void validatePushUsrInput(String[] usrInputArr) {
		if (!(usrInputArr.length == 2)) {
			// Invalid "PUSH" input

			log.error("Error: Invalid input!");
			throw new IllegalArgumentException("Error: Invalid input!");
		}

		try {
			// Check is double
			Double.valueOf(usrInputArr[1]);
		} catch (NumberFormatException exception) {
			// The param is not decimal number

			log.error("Error: Invalid input!");
			throw new IllegalArgumentException("Error: Invalid input!");
		}
	}

	private void validateNonPushUsrInput(String[] usrInputArr) {
		if (!(usrInputArr.length == 1)) {
			// Invalid non-"PUSH" input

			log.error("Error: Invalid input!");
			throw new IllegalArgumentException("Error: Invalid input!");
		}
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

	public void validateInv() throws IllegalArgumentException {
		if (currentStack.isEmpty()) {
			// Empty stack

			log.error(errorStackIsEmpty);
			throw new IllegalArgumentException(errorStackIsEmpty);
		}

		Double num = currentStack.peek();

		if (num.equals(0.0D)) {
			// Arithmetic error, attempting to invert zero

			log.error(errorZeroCannotBeInverted);
			throw new IllegalArgumentException(errorZeroCannotBeInverted);
		}
	}
}
