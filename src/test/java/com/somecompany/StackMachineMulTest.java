package com.somecompany;

import static org.assertj.core.api.Assertions.assertThat;
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
		assertThat(topElement == 5.25);
		// Current stack
		assertThat(currentStack.size() == 1);
		assertThat(currentStack.pop() == 5.25);
		// Backup stack
		assertThat(backupStack.size() == 2);
		assertThat(backupStack.pop() == 3.5);
		assertThat(backupStack.pop() == 1.5);
	}

	@Test
	public void shouldBeAbleToThrowErrorIfNotEnoughElementsToPerformMultiply() {
		stackMachineService.push("1.5");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			stackMachineService.mul();
			;
		});

		assertThat(exception.equals(errorNotEnoughElementsToMul));
	}
}
