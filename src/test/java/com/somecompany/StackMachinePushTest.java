package com.somecompany;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	@Test
	public void shouldBeAbleToPushParamToStackMachine() {
		stackMachineService.push("1.5");
		Double topElement = stackMachineService.push("3.5");

		// Returned top element
		assertThat(topElement.equals("3.5"));
		// Current stack
		assertThat(stackMachine.getCurrentStack().size() == 2);
		assertThat(stackMachine.getCurrentStack().pop() == 3.5);
		assertThat(stackMachine.getCurrentStack().pop() == 1.5);
		// Backup stack
		assertThat(stackMachine.getBackupStack().size() == 1);
		assertThat(stackMachine.getBackupStack().pop() == 1.5);
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
