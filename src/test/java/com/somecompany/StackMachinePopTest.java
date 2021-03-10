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
public class StackMachinePopTest {
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
	public void shouldBeAbleToPopTopElementFromStack() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");

		stackMachineService.pop();

		// Current stack
		assertThat(currentStack.size() == 1);
		assertThat(currentStack.pop() == 1.5);

		// Backup stack
		assertThat(backupStack.size() == 2);
		assertThat(backupStack.pop() == 3.5);
		assertThat(backupStack.pop() == 1.5);
	}

	@Test
	public void shouldBeAbleToThrowErrorIfAttemptToPopEmptyStack() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			stackMachineService.pop();
			;
		});

		assertThat(exception.equals(errorStackIsEmpty));
	}
}
