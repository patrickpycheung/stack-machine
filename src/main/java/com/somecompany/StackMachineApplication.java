package com.somecompany;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.somecompany.model.Command;
import com.somecompany.service.StackMachineService;
import com.somecompany.service.ValidationService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class StackMachineApplication implements CommandLineRunner {

	@Autowired
	private ValidationService validationService;

	@Autowired
	private StackMachineService stackMachineService;

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(StackMachineApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (env.getActiveProfiles().length == 0 || !env.getActiveProfiles()[0].equals("test")) {
			// Using non-test Spring profile

			// Instruct user to perform input
			System.out.println("==============================");
			System.out.println("Welcome to stack machine application!");
			System.out.println("Below are the possible operations:");
			System.out.println("PUSH <xyz> or <xyz>");
			System.out.println("POP");
			System.out.println("CLEAR");
			System.out.println("ADD");
			System.out.println("MUL");
			System.out.println("NEG");
			System.out.println("INV");
			System.out.println("UNDO");
			System.out.println("PRINT");
			System.out.println("QUIT");
			System.out.println("==============================");

			while (true) {
				System.out.println("Please enter your command:");

				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String usrInput = br.readLine();

					try {
						// Validate the raw user input
						validationService.validateUsrInput(usrInput);

						String[] usrInputArr = usrInput.split(" ");

						String command = usrInputArr[0];

						// Check if it is "direct push" (i.e. Just provide the value to push without the "PUSH" word)

						try {
							// Check is double
							Double.valueOf(command);

							System.out.println("Top element: " + stackMachineService.push(command));
						} catch (NumberFormatException exception) {
							// Not "direct push"
							// Just continue to see which command it is
						}

						command = command.toUpperCase();

						if (command.equals(Command.PUSH.name())) {
							// PUSH command

							System.out.println("Top element: " + stackMachineService.push(usrInputArr[1]));
						} else if (command.equals(Command.POP.name())) {
							// POP command

							String result = stackMachineService.pop();

							if (result.equals("EMPTY")) {
								// Stack is empty

								System.out.println("There are now no elements in the stack.");
							} else {
								// Stack is not empty

								// Print top element
								System.out.println("Top element: " + result);
							}
						} else if (command.equals(Command.CLEAR.name())) {
							// CLEAR command

							stackMachineService.clear();
						} else if (command.equals(Command.ADD.name())) {
							// ADD command

							System.out.println("Top element: " + stackMachineService.add());
						} else if (command.equals(Command.MUL.name())) {
							// MUL command

							System.out.println("Top element: " + stackMachineService.mul());
						} else if (command.equals(Command.NEG.name())) {
							// NEG command

							System.out.println("Top element: " + stackMachineService.neg());
						} else if (command.equals(Command.INV.name())) {
							// INV command

							DecimalFormat df = new DecimalFormat("0.00");
							System.out.println(df.format(stackMachineService.inv()));
						} else if (command.equals(Command.UNDO.name())) {
							// UNDO command

							stackMachineService.undo();
						} else if (command.equals(Command.PRINT.name())) {
							// PRINT command

							System.out.println(stackMachineService.print());
						} else if (command.equals(Command.QUIT.name())) {
							// QUIT command

							System.out.println("Bye!");
							break;
						}
					} catch (IllegalArgumentException exception) {
						System.out.println(exception.getMessage());
					}

				} catch (IOException exception) {
					log.error("Error: IOException has occurred!");
				}
			}
		}
	}
}
