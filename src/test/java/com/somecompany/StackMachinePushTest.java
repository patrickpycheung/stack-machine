package com.somecompany;

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
public class StackMachinePushTest {

	@Autowired
	private StackMachineService stackMachineService;

	@Autowired
	private StackMachine stackMachine;

	@Value("${errorMsg.paramNotDecimalNum}")
	private String errorParamNotDecimalNum;

	private Stack<Double> currentStack;
	private Stack<Double> backupStack;

	@BeforeEach
	public void init() {
		currentStack = stackMachine.getCurrentStack();
		backupStack = stackMachine.getBackupStack();

		currentStack.removeAllElements();
		backupStack.removeAllElements();
	}

	@Test
	public void shouldBeAbleToPushParamToStackMachine() {
		stackMachineService.push("1.5");
		Double topElement = stackMachineService.push("3.5");

		// Returned top element
		assertEquals(3.5, topElement);
		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		assertEquals(1.5, currentStack.pop());
		// Backup stack
		assertEquals(1, backupStack.size());
		assertEquals(1.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToThrowErrorIfParamIsEmpty() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			stackMachineService.push("");
		});

		assertEquals(errorParamNotDecimalNum, exception.getMessage());
	}

	@Test
	public void shouldBeAbleToThrowErrorIfParamIsNotDecimalNum() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			stackMachineService.push("a");
		});

		assertEquals(errorParamNotDecimalNum, exception.getMessage());
	}
}
