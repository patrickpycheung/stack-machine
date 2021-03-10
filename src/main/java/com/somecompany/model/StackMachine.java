package com.somecompany.model;

import java.util.Stack;

import lombok.Data;

/**
 * Model of a stack machine.
 * 
 * @author patrick
 * 
 */
@Data
public class StackMachine {

	private Stack<Double> currentStack;

	// For backup purpose for "UNDO" scenario
	private Stack<Double> backupStack;
}
