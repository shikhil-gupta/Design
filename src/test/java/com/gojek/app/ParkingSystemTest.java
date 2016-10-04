package com.gojek.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.gojek.exception.CustomParkingException;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple ParkingSystem.
 */
public class ParkingSystemTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public ParkingSystemTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ParkingSystemTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	public void testValidParkingSystemApp() {
		String inputFilePath = "tstData/validTestFile.txt";
		String outputFilePath = "tstData/output.txt";
		ParkingSystemApp app = new ParkingSystemApp(inputFilePath, outputFilePath);
		BufferedReader outputReader = null;
		try {
			app.initializeApp();
			app.startApp();
			app.closeApp();
			outputReader = new BufferedReader(new FileReader(outputFilePath));
			String actualOutputStr = outputReader.readLine();

			while (actualOutputStr != null) {
				System.out.println(actualOutputStr);
				actualOutputStr = outputReader.readLine();
			}
			outputReader.close();
		} catch (CustomParkingException ex) {
			assertTrue(false);
			return;
		} catch (FileNotFoundException ex) {
			assertTrue(false);
			return;
		} catch (IOException e) {
			assertTrue(false);
			return;
		}

	}

	public void testValidParkingSystemUsingCommand() {
		try {
			ParkingSystemApp app = new ParkingSystemApp();
			Assert.assertEquals(Boolean.FALSE, app.getIsParkingSystemAppInitialized());
			app.initializeApp();
			Assert.assertEquals(Boolean.TRUE, app.getIsParkingSystemAppInitialized());
			String inputCommand = "create_parking_lot 6";
			String expectedOutput = "Created a parking lot with 6 slots";
			String actualOutput = app.createParkingLot(inputCommand);
			Assert.assertTrue(app.getParking().getIsParkingLotInitialized());
			Assert.assertEquals(actualOutput, expectedOutput);

			inputCommand = "park KA­-01-­HH-­1234 White";
			actualOutput = app.parkVeichle(inputCommand);
			expectedOutput = "Allocated slot number: 1";
			Assert.assertEquals(actualOutput, expectedOutput);
			Assert.assertEquals(Integer.valueOf(1), app.getParking().getSlotNoOfGivenRegNo("KA­-01-­HH-­1234"));
			Assert.assertFalse(app.getParking().isGivenSlotFree(1));

			inputCommand = "park KA­-01-­HH-­9999 White";
			actualOutput = app.parkVeichle(inputCommand);
			expectedOutput = "Allocated slot number: 2";
			Assert.assertEquals(actualOutput, expectedOutput);
			Assert.assertEquals(Integer.valueOf(2), app.getParking().getSlotNoOfGivenRegNo("KA­-01-­HH-­9999"));
			Assert.assertFalse(app.getParking().isGivenSlotFree(2));

			inputCommand = "park KA­-01-­BB-­0001 Black";
			actualOutput = app.parkVeichle(inputCommand);
			expectedOutput = "Allocated slot number: 3";
			Assert.assertEquals(actualOutput, expectedOutput);
			Assert.assertEquals(Integer.valueOf(3), app.getParking().getSlotNoOfGivenRegNo("KA­-01-­BB-­0001"));
			Assert.assertFalse(app.getParking().isGivenSlotFree(3));

			inputCommand = "park KA­-01-­HH-­7777 Red";
			actualOutput = app.parkVeichle(inputCommand);
			expectedOutput = "Allocated slot number: 4";
			Assert.assertEquals(actualOutput, expectedOutput);
			Assert.assertEquals(Integer.valueOf(4), app.getParking().getSlotNoOfGivenRegNo("KA­-01-­HH-­7777"));
			Assert.assertFalse(app.getParking().isGivenSlotFree(4));

			inputCommand = "park KA­-01-­HH-­2701 Blue";
			actualOutput = app.parkVeichle(inputCommand);
			expectedOutput = "Allocated slot number: 5";
			Assert.assertEquals(actualOutput, expectedOutput);
			Assert.assertEquals(Integer.valueOf(5), app.getParking().getSlotNoOfGivenRegNo("KA­-01-­HH-­2701"));
			Assert.assertFalse(app.getParking().isGivenSlotFree(5));

			inputCommand = "park KA-­01-­HH­-3141 Black";
			actualOutput = app.parkVeichle(inputCommand);
			expectedOutput = "Allocated slot number: 6";
			Assert.assertEquals(actualOutput, expectedOutput);
			Assert.assertEquals(Integer.valueOf(6), app.getParking().getSlotNoOfGivenRegNo("KA-­01-­HH­-3141"));
			Assert.assertFalse(app.getParking().isGivenSlotFree(6));

			inputCommand = "leave 4";
			actualOutput = app.vacateSlot(inputCommand);
			expectedOutput = "Slot number 4 is free";
			Assert.assertEquals(actualOutput, expectedOutput);
			Assert.assertTrue(app.getParking().isGivenSlotFree(4));

			inputCommand = "status";
			List<String> expectedOuputList = Arrays.asList("Slot No." + "\t" + "Registration No" + "\t" + "Colour",
					"1" + "\t" + "KA­-01-­HH-­1234" + "\t" + "WHITE", "2" + "\t" + "KA­-01-­HH-­9999" + "\t" + "WHITE",
					"3" + "\t" + "KA­-01-­BB-­0001" + "\t" + "BLACK", "5" + "\t" + "KA­-01-­HH-­2701" + "\t" + "BLUE",
					"6" + "\t" + "KA-­01-­HH­-3141" + "\t" + "BLACK");
			List<String> actualOutputList = app.occupiedSlots(inputCommand);
			Assert.assertEquals(actualOutputList.size(), expectedOuputList.size());
			for (String str : expectedOuputList) {
				if (!actualOutputList.contains(str)) {
					assertTrue(false);
				}
			}
			inputCommand = "park KA­-01-­P-­333 White";
			actualOutput = app.parkVeichle(inputCommand);
			expectedOutput = "Allocated slot number: 4";
			Assert.assertEquals(actualOutput, expectedOutput);
			Assert.assertEquals(Integer.valueOf(4), app.getParking().getSlotNoOfGivenRegNo("KA­-01-­P-­333"));
			Assert.assertFalse(app.getParking().isGivenSlotFree(4));

			inputCommand = "park DL­-12­-AA-­9999 White";
			actualOutput = app.parkVeichle(inputCommand);
			expectedOutput = "Sorry, parking lot is full";
			Assert.assertEquals(actualOutput, expectedOutput);
			Assert.assertEquals(0, app.getParking().getAvailableSlot());

			inputCommand = "registration_numbers_for_cars_with_colour White";
			actualOutput = app.listOfRegNoOfGivenCarColour(inputCommand);
			expectedOutput = "KA­-01-­HH-­1234" + ", " + "KA­-01-­HH-­9999" + ", " + "KA­-01-­P-­333";
			Assert.assertEquals(actualOutput, expectedOutput);

			inputCommand = "slot_numbers_for_cars_with_colour White";
			actualOutput = app.getSlotNoOfGivenCarColour(inputCommand);
			expectedOutput = "1" + ", " + "2" + ", " + "4";
			Assert.assertEquals(actualOutput, expectedOutput);

			inputCommand = "slot_number_for_registration_number KA-­01-­HH­-3141";
			actualOutput = app.getSlotNoOfGivenCarReg(inputCommand);
			expectedOutput = "6";
			Assert.assertEquals(actualOutput, expectedOutput);

			inputCommand = "slot_number_for_registration_number MH­-04-­AY-­1111";
			actualOutput = app.getSlotNoOfGivenCarReg(inputCommand);
			expectedOutput = "Not found";
			Assert.assertEquals(actualOutput, expectedOutput);

			app.closeApp();
			Assert.assertEquals(Boolean.FALSE, app.getIsParkingSystemAppInitialized());

		} catch (CustomParkingException ex) {
			assertTrue(false);
		}

	}

	public void testInvalidParkingSystemUsingCommand() {

		ParkingSystemApp app = new ParkingSystemApp();
		try {
			app.initializeApp();
			String invalidInput = "create_parking_lot -1";
			String actualOutput = app.createParkingLot(invalidInput);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}
		try {
			String invalidInput = "park";
			String actualOutput = app.createParkingLot(invalidInput);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}
		try {
			String invalidInput = "park KA­-01-­HH-­2701 Yellow";
			String actualOutput = app.parkVeichle(invalidInput);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}
		try {
			String invalidInput = "park KA­-01-­HH-­2701";
			String actualOutput = app.parkVeichle(invalidInput);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}

		try {
			String invalidInput = "leave";
			String actualOutput = app.vacateSlot(invalidInput);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}

		try {
			ParkingSystemApp app1 = new ParkingSystemApp();
			app1.initializeApp();
			String validInput = "create_parking_lot 1";
			app1.createParkingLot(validInput);

			String invalidInput1 = "leave 1";
			String actualOutput1 = app1.vacateSlot(invalidInput1);

			String invalidInput = "leave 4";
			String actualOutput = app1.vacateSlot(invalidInput);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}

		try {
			ParkingSystemApp app1 = new ParkingSystemApp();
			app1.initializeApp();
			String validInput = "create_parking_lot 1";
			app1.createParkingLot(validInput);

			String invalidInput1 = "leave -1";
			String actualOutput1 = app1.vacateSlot(invalidInput1);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}

		try {
			ParkingSystemApp app1 = new ParkingSystemApp();
			app1.initializeApp();
			String validInput = "create_parking_lot 1";
			app1.createParkingLot(validInput);

			String invalidInput1 = "leave 5";
			String actualOutput1 = app1.vacateSlot(invalidInput1);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}

		try {
			ParkingSystemApp app1 = new ParkingSystemApp();
			app1.initializeApp();
			String validInput = "create_parking_lot 1";
			app1.createParkingLot(validInput);

			String inputCommand = "park KA­-01-­P-­333 White";
			String actualOutput = app1.parkVeichle(inputCommand);

			String invalidInput1 = "leave 1";
			String actualOutput1 = app1.vacateSlot(invalidInput1);

			String invalidInput2 = "leave 4";
			String actualOutput2 = app1.vacateSlot(invalidInput2);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}

		try {
			ParkingSystemApp app1 = new ParkingSystemApp();
			app1.initializeApp();
			String validInput = "create_parking_lot 1";
			app1.createParkingLot(validInput);

			String inputCommand = "park KA­-01-­P-­333 White";
			String actualOutput = app1.parkVeichle(inputCommand);

			inputCommand = "registration_numbers_for_cars_with_colour Black";
			actualOutput = app1.listOfRegNoOfGivenCarColour(inputCommand);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}

		try {
			String invalidInput = "registration_numbers_for_cars_with_colour";
			String actualOutput = app.listOfRegNoOfGivenCarColour(invalidInput);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}
		
		try {
			ParkingSystemApp app1 = new ParkingSystemApp();
			app1.initializeApp();
			String validInput = "create_parking_lot 1";
			app1.createParkingLot(validInput);

			String inputCommand = "park KA­-01-­P-­333 White";
			String actualOutput = app1.parkVeichle(inputCommand);

			inputCommand = "park KA­-01-­P-­333 White";
			actualOutput = app1.parkVeichle(inputCommand);
			Assert.assertTrue(false);

		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}
		
		try {
			ParkingSystemApp app1 = new ParkingSystemApp();
			app1.initializeApp();
			String validInput = "create";
			app1.createParkingLot(validInput);

			String inputCommand = "park KA­-01-­P-­333 White";
			String actualOutput = app1.parkVeichle(inputCommand);

			inputCommand = "park KA­-01-­P-­333 White";
			actualOutput = app1.parkVeichle(inputCommand);
			Assert.assertTrue(false);
		} catch (CustomParkingException ex) {
			System.out.println(ex.getMessage());
			Assert.assertFalse(false);
		}
	}

	public void testInvalidParkingLot() {

	}

}
