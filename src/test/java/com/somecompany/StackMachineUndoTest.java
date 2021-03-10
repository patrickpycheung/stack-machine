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
public class StackMachineUndoTest {
	@Autowired
	private StackMachineService stackMachineService;

	@Autowired
	private StackMachine stackMachine;

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
	public void shouldBeAbleToUndoPushWhereTheResultantStackIsNotEmpty() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");

		String result = stackMachineService.undo();

		// Top element
		assertEquals("1.50", result);
		// Current stack
		assertEquals(1, currentStack.size());
		assertEquals(1.5, currentStack.pop());
		// Backup stack
		assertEquals(1, backupStack.size());
		assertEquals(1.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToUndoPushWhereTheResultantStackIsEmpty() {
		stackMachineService.push("1.5");

		String result = stackMachineService.undo();

		// Top element
		assertEquals("EMPTY", result);
		// Current stack
		assertEquals(0, currentStack.size());
		// Backup stack
		assertEquals(0, backupStack.size());
	}

	@Test
	public void shouldBeAbleToUndoPop() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");
		stackMachineService.pop();

		String result = stackMachineService.undo();

		// Top element
		assertEquals("3.50", result);
		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleUndoClearWhereTheResultantStackIsNotEmpty() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");
		stackMachineService.clear();

		String result = stackMachineService.undo();

		// Top element
		assertEquals("3.50", result);
		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleUndoClearWhereTheResultantStackIsEmpty() {
		stackMachineService.clear();

		String result = stackMachineService.undo();

		// Top element
		assertEquals("EMPTY", result);
		// Current stack
		assertEquals(0, currentStack.size());
		// Backup stack
		assertEquals(0, backupStack.size());
	}

	@Test
	public void shouldBeAbleToUndoAdd() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");
		stackMachineService.add();

		String result = stackMachineService.undo();

		// Top element
		assertEquals("3.50", result);
		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToUndoMul() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");
		stackMachineService.mul();

		String result = stackMachineService.undo();

		// Top element
		assertEquals("3.50", result);
		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToUndoNeg() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");
		stackMachineService.neg();

		String result = stackMachineService.undo();

		// Top element
		assertEquals("3.50", result);
		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToUndoInv() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");
		stackMachineService.inv();

		String result = stackMachineService.undo();

		// Top element
		assertEquals("3.50", result);
		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToUndoAtTheBeginning() {
		String result = stackMachineService.undo();

		// Top element
		assertEquals("EMPTY", result);
		// Current stack
		assertEquals(0, currentStack.size());
		// Backup stack
		assertEquals(0, backupStack.size());
	}
}
