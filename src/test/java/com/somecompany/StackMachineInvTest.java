package com.somecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.DecimalFormat;
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
public class StackMachineInvTest {
	@Autowired
	private StackMachineService stackMachineService;

	@Autowired
	private StackMachine stackMachine;

	@Value("${errorMsg.stackIsEmpty}")
	private String errorStackIsEmpty;

	@Value("${errorMsg.zeroCannotBeInverted}")
	private String errorZeroCannotBeInverted;

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
	public void shouldBeAbleToInvertElement() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");

		Double topElement = stackMachineService.inv();

		DecimalFormat df = new DecimalFormat("0.00");

		// Top element
		assertEquals("0.29", df.format(topElement));
		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals("0.29", df.format(currentStack.pop()));
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());

	}

	@Test
	public void shouldBeAbleToThrowErrorIfAttemptToInvertFromAnEmptyStack() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			stackMachineService.inv();
			;
		});

		assertEquals(errorStackIsEmpty, exception.getMessage());
	}

	@Test
	public void shouldBeAbleToThrowErrorIfAttemptToInvertZero() {
		stackMachineService.push("0.0");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			stackMachineService.inv();
			;
		});

		assertEquals(errorZeroCannotBeInverted, exception.getMessage());
	}
}
