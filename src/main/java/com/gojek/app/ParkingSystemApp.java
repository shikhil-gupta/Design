package com.gojek.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gojek.enums.Colour;
import com.gojek.exception.CustomParkingException;
import com.gojek.parking.ParkingLot;
import com.gojek.parking.ParkingSlot;

import lombok.Data;

/**
 * ParkingSystemApp client which will issue ticket to veichle and interact with
 * customer.
 * 
 * @author shikhill.gupta
 *
 */

@Data
public class ParkingSystemApp {
	/**
	 * flag to check status of ParkingSystemApp is running or not.
	 */
	private boolean exit;
	/**
	 * flag to see the is parking system app is being using by providing the
	 * file of commands.
	 */
	private boolean isFileMode;

	/**
	 * BufferedReader stream to read the input from file in case isFileMode is
	 * true or to read from console.
	 */
	private BufferedReader inputReader;

	/**
	 * PrintWriter, writer to write the output to the file in case of isFileMode
	 * is true.
	 */
	private PrintWriter outputWriter;
	/**
	 * Instance of parking lot class.
	 */
	private ParkingLot parking;

	/**
	 * path of input file path to read the command from given file.
	 */
	private String inputFilePath;

	/**
	 * path to write the output to file.
	 */
	private String outputFilePath;

	/**
	 * Map to validate the input user against the expected input.
	 */
	private static Map<String, Integer> hashMap;

	/**
	 * flag to see is parking system is initialized successfully or not.
	 */
	private Boolean isParkingSystemAppInitialized = false;

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

	public ParkingSystemApp(String inputFilePath, String outputFilePath) {
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		isFileMode = true;
	}

	public ParkingSystemApp() {
		isFileMode = false;
	}

	public static void main(String[] args) {
		ParkingSystemApp app = null;
		try {
			app = getParkingSystemInstance(args);
			app.initializeApp();
			app.startApp();
		} catch (CustomParkingException ex) {
			app.writeOutput(Arrays
					.asList("Exception message " + ex.getMessage() + "\n Exception stack Trace" + ex.getStackTrace()));
		} catch (Throwable ex) {
			app.writeOutput(Arrays
					.asList("Exception message " + ex.getMessage() + "\n Exception stack Trace" + ex.getStackTrace()));
		} finally {
			app.closeApp();
		}
	}

	private void writeOutput(List<String> outputList) {
		for (String output : outputList) {
			if (isFileMode) {
				outputWriter.println(output);
				outputWriter.flush();
			} else {
				System.out.println(output);
			}
		}
	}

	/**
	 * Function to initialize parking system app before issuing parking slot
	 * ticket to client.
	 * 
	 * @throws CustomParkingException,
	 *             throw an exception if something goes wrong while
	 *             initialization.
	 */
	public void initializeApp() throws CustomParkingException {

		if (isParkingSystemAppInitialized) {
			return;
		}
		try {
			parking = new ParkingLot();
			exit = false;
			if (isFileMode) {
				inputReader = new BufferedReader(new FileReader(inputFilePath));
				outputWriter = new PrintWriter(new FileOutputStream(outputFilePath));
			} else {
				inputReader = new BufferedReader(new InputStreamReader(System.in));
			}
			isParkingSystemAppInitialized = true;
		} catch (FileNotFoundException ex) {
			throw new CustomParkingException("File not found exception occured message " + ex.getMessage() + " "
					+ "Exception stack trace " + ex.getStackTrace());
		} catch (SecurityException ex) {
			throw new CustomParkingException("security manager exception message " + ex.getMessage() + " "
					+ "Exception stack trace " + ex.getStackTrace());
		}

	}

	/**
	 * Call this function to issue parking slot ticket to client.
	 * 
	 * @throws CustomParkingException
	 */
	public void startApp() throws CustomParkingException {

		try {
			String inputString = inputReader.readLine();
			while (!exit && inputString != null) {
				inputString = inputString.trim();
				String[] expression = inputString.split("\\s+");
				List<String> resultList = null;
				String output = "";
				switch (expression[0].toLowerCase()) {
				case "create_parking_lot":
					output = createParkingLot(inputString);
					resultList = Arrays.asList(output);
					break;
				case "park":
					output = parkVeichle(inputString);
					resultList = Arrays.asList(output);
					break;
				case "leave":
					output = vacateSlot(inputString);
					resultList = Arrays.asList(output);
					break;
				case "status":
					resultList = occupiedSlots(inputString);
					break;
				case "registration_numbers_for_cars_with_colour":
					output = listOfRegNoOfGivenCarColour(inputString);
					resultList = Arrays.asList(output);
					break;
				case "slot_numbers_for_cars_with_colour":
					output = getSlotNoOfGivenCarColour(inputString);
					resultList = Arrays.asList(output);
					break;
				case "slot_number_for_registration_number":
					output = getSlotNoOfGivenCarReg(inputString);
					resultList = Arrays.asList(output);
					break;
				case "exit":
					exit = true;
					resultList = Arrays.asList();
					break;
				default:
					throw new CustomParkingException("Invalid input exeception");
				}
				writeOutput(resultList);
				inputString = inputReader.readLine();
			}
		} catch (IOException ex) {
			throw new CustomParkingException("IOException while reading from stream");
		}
	}

	/**
	 * Function to close the parking system app to stop it.
	 */

	public void closeApp() {
		if (isFileMode) {
			outputWriter.println("ParkingSystemApp Terminated");
			outputWriter.close();
		} else {
			System.out.print("ParkingSystemApp Terminated");
		}
		parking.close();
		exit = true;
		isParkingSystemAppInitialized = false;
		try {
			inputReader.close();
		} catch (IOException ex) {
			System.out.print("IOException occured while closing buffered reader stream");
		}
	}

	/**
	 * Function to initialize the parking lot capacity before issue parking slot
	 * to veichle.
	 * 
	 * @param inputCommand,
	 *            user command to initialize the parking lot.
	 * @return String representation of output.
	 * @throws CustomParkingException
	 */
	public String createParkingLot(String inputCommand) throws CustomParkingException {
		String result = "";
		String[] expression = getParsedUserInputArray(inputCommand);
		int totalSlot = Integer.parseInt(expression[1]);
		Boolean isInitialized = parking.initializeParkingLot(totalSlot);
		if (isInitialized) {
			result = "Created a parking lot with " + totalSlot + " slots";
		} else {
			throw new CustomParkingException(
					"CustomParkingException : Exception while executing command " + inputCommand);
		}
		return result;
	}

	/**
	 * Function to issue parking lot ticket to given veichle based on RegNo and
	 * colour.
	 * 
	 * @param inputCommand,
	 *            user input command.
	 * @return String representation of allocated slot to veichle.
	 * @throws CustomParkingException,
	 *             throw an exception if given veichle is already parked in
	 *             parking lot.
	 */
	public String parkVeichle(String inputCommand) throws CustomParkingException {
		String result = "";
		String[] expression = getParsedUserInputArray(inputCommand);
		String regNo = expression[1].trim();
		Colour colour = Colour.getValueOf(expression[2]);
		Integer allocatedSlot = parking.allocateSlotToVeichle(regNo, colour);
		if (allocatedSlot <= 0) {
			result = "Sorry, parking lot is full";
		} else {
			result = "Allocated slot number: " + allocatedSlot;
		}
		return result;
	}

	/**
	 * Function to vacate the given slot from parking lot.
	 * 
	 * @param inputCommand,
	 *            user input command to vacate the given slot.
	 * @return String representation of output.
	 * @throws CustomParkingException,
	 *             throw an exception if slot could not deallocated.
	 */

	public String vacateSlot(String inputCommand) throws CustomParkingException {
		String result = "";
		String[] expression = getParsedUserInputArray(inputCommand);
		int slot = Integer.parseInt(expression[1]);
		Boolean isSlotDeallocatedSuccessfully = parking.deallocateSlot(slot);
		if (isSlotDeallocatedSuccessfully) {
			result = "Slot number " + slot + " is free";
		} else {
			throw new CustomParkingException("could not deallocate slot no " + slot);
		}
		return result;
	}

	/**
	 * Return the Veichle information like slot, regNo and colour of all
	 * occupied slot in parking lot.
	 * 
	 * @param inputCommand,
	 *            user input,
	 * @return list of string.
	 * @throws CustomParkingException
	 */

	public List<String> occupiedSlots(String inputCommand) throws CustomParkingException {
		String outputHeaderStr = "Slot No." + "\t" + "Registration No" + "\t" + "Colour";
		List<String> result = new ArrayList<String>();
		result.add(outputHeaderStr);
		getParsedUserInputArray(inputCommand);
		List<ParkingSlot> list = parking.detailsOfOccupiedSlots();
		for (ParkingSlot slot : list) {
			String outputResult = slot.getSlotNumber() + "\t" + slot.getVeichile().getRegNo().toUpperCase() + "\t"
					+ slot.getVeichile().getColour().toString();
			result.add(outputResult);
		}
		return result;
	}

	/**
	 * Return the list of all regNo whose colour is given as input.
	 * 
	 * @param inputCommand,
	 *            user input.
	 * @return String instance of all regNo parked in parking lot of specified
	 *         colour.
	 * @throws CustomParkingException,
	 *             throw an exception if user input is not valid.
	 */
	public String listOfRegNoOfGivenCarColour(String inputCommand) throws CustomParkingException {
		String result = "";
		String[] expression = getParsedUserInputArray(inputCommand);
		List<String> regNoList = parking.getAllRegNoOfGivenColourCar(Colour.getValueOf(expression[1]));
		for (int i = 0; i < regNoList.size(); i++) {
			if (i == regNoList.size() - 1) {
				result += regNoList.get(i).toUpperCase();
			} else {
				result += regNoList.get(i).toUpperCase() + ", ";
			}
		}
		if (regNoList.size() <= 0) {
			throw new CustomParkingException("No car exist in parking lot of colour " + expression[1]);
		}

		return result;
	}

	/**
	 * Return the slots of all veichle whose colour is given as input.
	 * 
	 * @param inputCommand,
	 *            user input.
	 * @return String instance of all slot.
	 * @throws CustomParkingException,
	 *             throw an exception in case of invalid input.
	 */
	public String getSlotNoOfGivenCarColour(String inputCommand) throws CustomParkingException {
		String result = "";
		String[] expression = getParsedUserInputArray(inputCommand);
		List<Integer> slotList = parking.getSlotNoOfGivenColourCar(Colour.getValueOf(expression[1]));
		for (int i = 0; i < slotList.size(); i++) {
			if (i == slotList.size() - 1) {
				result += slotList.get(i);
			} else {
				result += slotList.get(i) + ", ";
			}
		}
		if (slotList.size() <= 0) {
			throw new CustomParkingException("No car exist in parking lot of colour " + expression[1]);
		}
		return result;
	}

	/**
	 * Return the slot of veichle whose regNo is given.
	 * 
	 * @param inputCommand,
	 *            user input command.
	 * @return String instance of slot.
	 * @throws CustomParkingException,
	 *             throw an exception in case regNo is not valid.
	 */
	public String getSlotNoOfGivenCarReg(String inputCommand) throws CustomParkingException {
		String result = "";
		String[] expression = getParsedUserInputArray(inputCommand);
		String regNo = expression[1];
		Integer slot = parking.getSlotNoOfGivenRegNo(regNo);
		if (slot > 0) {
			result += slot;
		} else {
			result = "Not found";
		}
		return result;
	}

	/**
	 * Validate the user input command against the predefined commands.
	 * 
	 * @param inputs,
	 *            user inputs.
	 * @throws CustomParkingException,
	 *             throw an exception in case wrong user input.
	 */
	private static void validateCommands(String[] inputs) throws CustomParkingException {

		String command = inputs[0].toLowerCase();
		if (!hashMap.containsKey(command) || !hashMap.get(command).equals(inputs.length)) {
			throw new CustomParkingException("Invalid input exception");
		}

	}

	private String[] getParsedUserInputArray(String inputCommand) throws CustomParkingException {
		String input = inputCommand.trim();
		String[] expression = input.split("\\s+");
		validateCommands(expression);
		return expression;
	}

	/**
	 * Static function to get the instance of ParkingSystemApp based on the user
	 * input.
	 * 
	 * @param args,
	 *            user input.
	 * @return ParkingSystemApp instance.
	 * @throws CustomParkingException,
	 *             throw exception in case of user input is not valid
	 */

	private static ParkingSystemApp getParkingSystemInstance(String[] args) throws CustomParkingException {
		if (args.length == 4) {
			return new ParkingSystemApp(args[1], args[3]);
		} else if (args.length == 1) {
			return new ParkingSystemApp();
		}
		throw new CustomParkingException(
				"Invalid argument count exception occured while" + "initializing the parkingSystemApp instance");
	}
}
