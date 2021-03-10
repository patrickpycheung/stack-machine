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
	public void shouldBeAbleToUndoPush() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");

		stackMachineService.undo();

		// Current stack
		assertEquals(1, currentStack.size());
		assertEquals(1.5, currentStack.pop());
		// Backup stack
		assertEquals(1, backupStack.size());
		assertEquals(1.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToUndoPop() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");
		stackMachineService.pop();

		stackMachineService.undo();

		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleUndoClear() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");
		stackMachineService.clear();

		stackMachineService.undo();

		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToUndoAdd() {
		stackMachineService.push("1.5");
		stackMachineService.push("3.5");
		stackMachineService.add();

		stackMachineService.undo();

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

		stackMachineService.undo();

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

		stackMachineService.undo();

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

		stackMachineService.undo();

		// Current stack
		assertEquals(2, currentStack.size());
		assertEquals(3.5, currentStack.pop());
		// Backup stack
		assertEquals(2, backupStack.size());
		assertEquals(3.5, backupStack.pop());
	}

	@Test
	public void shouldBeAbleToUndoAtTheBeginning() {
		stackMachineService.undo();

		// Current stack
		assertEquals(0, currentStack.size());
		// Backup stack
		assertEquals(0, backupStack.size());
	}
}
