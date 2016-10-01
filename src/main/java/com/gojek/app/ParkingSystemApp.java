package com.gojek.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.gojek.enums.Colour;
import com.gojek.exception.CustomParkingException;
import com.gojek.parking.ParkingLot;

/**
 * ParkingSystemApp client which will issue ticket to veichle and interact with
 * customer.
 * 
 * @author shikhill.gupta
 *
 */
public class ParkingSystemApp {
	boolean exit;
	boolean isFileMode;
	BufferedReader reader;
	PrintWriter out;
	ParkingLot parking;
	static Map<String, Integer> hashMap;

	static {
		hashMap = new HashMap<String, Integer>();
		hashMap.put("create_parking_lot", 2);
		hashMap.put("park", 3);
		hashMap.put("leave", 2);
		hashMap.put("status", 1);
		hashMap.put("registration_numbers_for_cars_with_colour", 2);
		hashMap.put("slot_numbers_for_cars_with_colour", 2);
		hashMap.put("slot_number_for_registration_number", 2);
	}

	public static void main(String[] args) {
		ParkingSystemApp app = new ParkingSystemApp();
		try {
			app.initializeApp(args);
			app.startApp();

		} catch (CustomParkingException ex) {
			app.writeOutput("Exception message " + ex.getMessage() + "\n Exception stack Trace" + ex.getStackTrace());
			app.writeOutput("\n");
		} catch (RuntimeException ex) {
			app.writeOutput("Exception message " + ex.getMessage() + "\n Exception stack Trace" + ex.getStackTrace());
			app.writeOutput("\n");
		} finally {
			app.closeApp();
		}
	}

	private void writeOutput(String output) {
		if (isFileMode) {
			out.print(output);
			out.flush();
		} else {
			System.out.println(output);
		}
	}

	private void initializeApp(String[] args) throws CustomParkingException {

		try {
			parking = new ParkingLot();
			exit = false;
			switch (args.length) {
			case 4:
				reader = new BufferedReader(new FileReader(args[1]));
				out = new PrintWriter(new FileOutputStream(args[3]));
				isFileMode = true;
				break;
			case 1:
				reader = new BufferedReader(new InputStreamReader(System.in));
				break;
			default:
				throw new CustomParkingException("Invalid argument exception");
			}
		} catch (FileNotFoundException ex) {
			throw new CustomParkingException("File not found exception occured message " + ex.getMessage() + " "
					+ "Exception stack trace " + ex.getStackTrace());
		} catch (SecurityException ex) {
			throw new CustomParkingException("security manager exception message " + ex.getMessage() + " "
					+ "Exception stack trace " + ex.getStackTrace());
		}

	}

	private void startApp() throws CustomParkingException {
		while (!exit) {
			CustomParkingException exception = null;
			String result = "";
			try {
				exception = null;
				result = "";
				String inputString = reader.readLine();
				inputString = inputString.trim();
				String[] expression = inputString.split("\\s+");
				validateCommands(expression);
				switch (expression[0].toLowerCase()) {
				case "create_parking_lot":
					try {
						int totalSlot = Integer.parseInt(expression[1]);
						result = parking.initializeParkingLot(totalSlot);
					} catch (NumberFormatException ex) {
						exception = new CustomParkingException("Invalid argument exception occured " + ex.getMessage()
								+ "Stack trace " + ex.getStackTrace());
					}
					break;
				case "park":
					String regNo = expression[1];
					Colour colour = Colour.getValueOf(expression[2].toLowerCase());
					result = parking.allocateSlotToVeichle(regNo, colour);
					break;
				case "leave":
					try {
						int slot = Integer.parseInt(expression[1]);
						result = parking.deallocateSlot(slot);
					} catch (NumberFormatException ex) {
						exception = new CustomParkingException("Invalid argument exception occured " + ex.getMessage()
								+ "Stack trace " + ex.getStackTrace());
					}
					break;
				case "status":
					result = parking.detailsOfOccupiedSlots();
					break;
				case "registration_numbers_for_cars_with_colour":
					colour = Colour.getValueOf(expression[1].toLowerCase());
					result = parking.getAllRegNoOfGivenColourCar(colour);
					break;
				case "slot_numbers_for_cars_with_colour":
					colour = Colour.getValueOf(expression[1].toLowerCase());
					result = parking.getSlotNoOfGivenColourCar(colour);
					break;
				case "slot_number_for_registration_number":
					result = parking.getSlotNoOfGivenRegNo(expression[1]);
					break;
				default:
					break;
				}
			} catch (IOException ex) {
				exit = true;
				exception = new CustomParkingException("IOException while reading from stream");
			}
			if (exception != null) {
				throw exception;
			}
			writeOutput(result);
		}
	}

	private void closeApp() {
		if (isFileMode) {
			out.println("ParkingSystemApp Terminated");
			out.close();
		} else {
			System.out.print("ParkingSystemApp Terminated");
		}
		parking.close();
		exit = true;
		try {
			reader.close();
		} catch (IOException ex) {
			System.out.print("IOException occured while closing buffered reader stream");
		}

	}

	public static void validateCommands(String[] expresion) throws CustomParkingException {

		String command = expresion[0].toLowerCase();
		if (!hashMap.containsKey(command) || !hashMap.get(command).equals(expresion.length)) {
			throw new CustomParkingException("Invalid input exception");
		}

	}
}
