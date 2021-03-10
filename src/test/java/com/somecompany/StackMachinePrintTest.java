package com.somecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.somecompany.model.StackMachine;
import com.somecompany.service.StackMachineService;

@SpringBootTest
@ActiveProfiles("test")
public class StackMachinePrintTest {
	@Autowired
	private StackMachineService stackMachineService;

	@Autowired
	private StackMachine stackMachine;

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
	public void shouldBeAbleToPrintAllElements() {
		stackMachineService.push("1.5");
		stackMachineService.push("0.5");
		stackMachineService.push("3.5");

		String printStr = stackMachineService.print();

		assertEquals("3.50,0.50,1.50", printStr);
	}

	@Test
	public void shouldBeAbleToShowMessageIfStackIsEmpty() {
		String printStr = stackMachineService.print();
		assertEquals("There are now no elements in the stack.", printStr);
	}
}
