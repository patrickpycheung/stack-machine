package com.somecompany;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.somecompany.model.StackMachine;
import com.somecompany.service.StackMachineService;

@SpringBootTest
@ActiveProfiles("test")
public class StackMachineMulTest {
	@Autowired
	private StackMachineService stackMachineService;

	@Autowired
	private StackMachine stackMachine;

	@Value("${errorMsg.stackIsEmpty}")
	private String errorStackIsEmpty;

	@Value("${errorMsg.notEnoughElementsToAdd}")
	private String errorNotEnoughElementsToAdd;

	@Value("${errorMsg.notEnoughElementsToMul}")
	private String errorNotEnoughElementsToMul;

	Stack<Double> currentStack;
	Stack<Double> backupStack;

	@BeforeEach
	public void init() {
		currentStack = stackMachine.getCurrentStack();
		backupStack = stackMachine.getBackupStack();

		currentStack.removeAllElements();
		backupStack.removeAllElements();
	}

	@Test
	public void shouldBeAbleToMultiplyElements() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");

		Double topElement = stackMachineService.mul();

		// Top element
		assertEquals(5.25, topElement);
		// Current stack
		assertEquals(1, currentStack.size());
		assertEquals(5.25, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
		assertEquals(1.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToThrowErrorIfNotEnoughElementsToPerformMultiply() {
		stackMachineService.push("1.5");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			stackMachineService.mul();
			;
		});

		assertThat(exception.equals(errorNotEnoughElementsToMul));
		assertEquals(errorNotEnoughElementsToMul, exception.getMessage());
	}
}
