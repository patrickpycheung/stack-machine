package com.somecompany;

import static org.assertj.core.api.Assertions.assertThat;

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
public class StackMachineClearTest {
	@Autowired
	private StackMachineService stackMachineService;

	@Autowired
	private StackMachine stackMachine;

	@Value("${errorMsg.stackIsEmpty}")
	private String errorStackIsEmpty;

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
	public void shouldBeAbleToClearAllElementsFromStack() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");

		stackMachineService.clear();

		// Current stack
		assertThat(currentStack.size() == 0);

		// Backup stack
		assertThat(backupStack.size() == 0);
	}

	@Test
	public void shouldBeAbleToClearAllElementsFromEmptyStack() {
		stackMachineService.clear();

		// Current stack
		assertThat(currentStack.size() == 0);

		// Backup stack
		assertThat(backupStack.size() == 0);
	}
}
