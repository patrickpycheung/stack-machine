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
public class StackMachinePushTest {

	@Autowired
	private StackMachineService stackMachineService;

	@Autowired
	private StackMachine stackMachine;

	@Value("${errorMsg.paramNotDecimalNum}")
	private String errorParamNotDecimalNum;

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
	public void shouldBeAbleToPushParamToStackMachine() {
		stackMachineService.push("1.5");
		Double topElement = stackMachineService.push("3.5");

		// Returned top element
		assertThat(topElement.equals("3.5"));
		// Current stack
		assertThat(currentStack.size() == 2);
		assertThat(currentStack.pop() == 3.5);
		assertThat(currentStack.pop() == 1.5);
		// Backup stack
		assertThat(backupStack.size() == 1);
		assertThat(backupStack.pop() == 1.5);
	}

	@Test
	public void shouldBeAbleToThrowErrorIfParamIsEmpty() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			stackMachineService.push("");
		});

		assertThat(exception.equals(errorParamNotDecimalNum));
	}

	@Test
	public void shouldBeAbleToThrowErrorIfParamIsNotDecimalNum() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			stackMachineService.push("a");
		});

		assertThat(exception.equals(errorParamNotDecimalNum));
	}
}
