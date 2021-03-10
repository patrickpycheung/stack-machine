package com.somecompany.service;

import java.util.Stack;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.somecompany.model.Command;
import com.somecompany.model.StackMachine;

import lombok.extern.slf4j.Slf4j;

/**
 * Backend services for validating the user input and parameters.
 * 
 * @author patrick
 */
@Service
@Slf4j
public class ValidationService {

	@Autowired
	private StackMachine stackMachine;

	private Stack<Double> currentStack;

	@Value("${errorMsg.inputIsEmpty}")
	private String errorInputIsEmpty;

	@Value("${errorMsg.invalidInput}")
	private String errorInvalidInput;

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
	}

	/**
	 * Validate the user input from the console.
	 * 
	 * @param Raw user input from the console
	 */
	public void validateUsrInput(String usrInput) throws IllegalArgumentException {
		if (usrInput == null || usrInput.equals("")) {
			// Null or empty input

			log.error(errorInputIsEmpty);
			throw new IllegalArgumentException(errorInputIsEmpty);
		}

		String[] usrInputArr = usrInput.split(" ");

		String command = usrInputArr[0];

		// Check if it is "direct push" (i.e. Just provide the value to push without the "PUSH" word)

		try {
			// Check is double
			Double.valueOf(command);

			if (usrInputArr.length > 1) {
				// Invalid "direct push" input

				log.error(errorInvalidInput);
				throw new IllegalArgumentException(errorInvalidInput);
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

			log.error(errorInvalidInput);
			throw new IllegalArgumentException(errorInvalidInput);
		}
	}

	/**
	 * Check the parameter count for "PUSH" command.
	 * 
	 * @param Raw user input from the console
	 * @throws IllegalArgumentException
	 */
	private void validatePushUsrInput(String[] usrInputArr) throws IllegalArgumentException {
		if (!(usrInputArr.length == 2)) {
			// Invalid "PUSH" input

			log.error(errorInvalidInput);
			throw new IllegalArgumentException(errorInvalidInput);
		}

		try {
			// Check is double
			Double.valueOf(usrInputArr[1]);
		} catch (NumberFormatException exception) {
			// The param is not decimal number

			log.error(errorInvalidInput);
			throw new IllegalArgumentException(errorInvalidInput);
		}
	}

	/**
	 * Check the parameter count for commands other than "PUSH".
	 * 
	 * @param Raw user input from the console
	 * @throws IllegalArgumentException
	 */
	private void validateNonPushUsrInput(String[] usrInputArr) throws IllegalArgumentException {
		if (!(usrInputArr.length == 1)) {
			// Invalid non-"PUSH" input

			log.error(errorInvalidInput);
			throw new IllegalArgumentException(errorInvalidInput);
		}
	}

	/**
	 * Validate the "PUSH" operation validity.
	 * 
	 * @param The decimal number to be pushed
	 * @throws IllegalArgumentException
	 */
	public void validatePush(String paramStr) throws IllegalArgumentException {
		try {
			Double.valueOf(paramStr);
		} catch (NumberFormatException exception) {
			// Param not a decimal number

			log.error(errorParamNotDecimalNum);
			throw new IllegalArgumentException(errorParamNotDecimalNum);
		}
	}

	/**
	 * Validate the "POP" operation validity.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void validatePop() throws IllegalArgumentException {
		if (currentStack.isEmpty()) {
			// Empty stack

			log.error(errorStackIsEmpty);
			throw new IllegalArgumentException(errorStackIsEmpty);
		}
	}

	/**
	 * Validate the "ADD" operation validity.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void validateAdd() throws IllegalArgumentException {
		if (!(currentStack.size() >= 2)) {
			// Not enough elements to perform add

			log.error(errorNotEnoughElementsToAdd);
			throw new IllegalArgumentException(errorNotEnoughElementsToAdd);
		}
	}

	/**
	 * Validate the "MUL" operation validity.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void validateMul() throws IllegalArgumentException {
		if (!(currentStack.size() >= 2)) {
			// Not enough elements to perform add

			log.error(errorNotEnoughElementsToMul);
			throw new IllegalArgumentException(errorNotEnoughElementsToMul);
		}
	}

	/**
	 * Validate the "NEG" operation validity.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void validateNeg() throws IllegalArgumentException {
		if (currentStack.isEmpty()) {
			// Empty stack

			log.error(errorStackIsEmpty);
			throw new IllegalArgumentException(errorStackIsEmpty);
		}
	}

	/**
	 * Validate the "INV" operation validity.
	 * 
	 * @throws IllegalArgumentException
	 */
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
