package com.gojek.parking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import com.gojek.enums.Colour;
import com.gojek.exception.CustomParkingException;

import lombok.Data;

/**
 * Parking lot class which contains details regarding which slot is free and
 * Occupied.
 * 
 * @author shikhill.gupta
 *
 */

@Data
public class ParkingLot {

	/**
	 * Min heap to get nearest free slot.
	 */
	PriorityQueue<Integer> freeSlotQueue;
	/**
	 * Map to RegNo to ParkingSlot
	 */
	Map<String, ParkingSlot> regNoToParkingSlotMap;
	/**
	 * Tree map which sort the occupied slot in increasing order.
	 */
	TreeMap<Integer, ParkingSlot> slotNoToParkingSlotMap;

	/**
	 * count which tell available at any time.
	 */
	int availableSlot;

	/**
	 * Boolean to track the parking lot is initialized or not.
	 */
	Boolean isParkingLotInitialized = false;

	/**
	 * Count of total capacity of parking lot.
	 */
	int totalSlot;

	/**
	 * Function to initialize the all parking lot variable.
	 * 
	 * @param totalSlot
	 * @return {@link Boolean} flag to determine the initialized status of
	 *         parking lot.
	 * @throws CustomParkingException
	 */
	public Boolean initializeParkingLot(int totalSlot) throws CustomParkingException {
		this.totalSlot = totalSlot;
		this.availableSlot = totalSlot;
		try {
			freeSlotQueue = new PriorityQueue<Integer>(totalSlot);
		} catch (IllegalArgumentException ex) {
			throw new CustomParkingException("CustomParkingException : Exception message Illegal argument totalSlot "
					+ totalSlot + " " + ex.getMessage() + "Exception stack trace " + ex.getStackTrace());
		}
		for (int i = 1; i <= totalSlot; i++) {
			freeSlotQueue.add(i);
		}
		regNoToParkingSlotMap = new HashMap<String, ParkingSlot>();
		slotNoToParkingSlotMap = new TreeMap<Integer, ParkingSlot>();
		isParkingLotInitialized = true;
		return isParkingLotInitialized;
	}

	/**
	 * Allocate veichle to nearest empty slot based on regNo and colour of car.
	 * 
	 * @param regNo
	 * @param colour
	 * @return {@link Integer} allocated slot.
	 * @throws CustomParkingException
	 */

	public Integer allocateSlotToVeichle(String regNo, Colour colour) throws CustomParkingException {

		checkIsParkingLotInitialized();
		regNo = regNo.toLowerCase();
		if (availableSlot <= 0 || freeSlotQueue.isEmpty() || regNoToParkingSlotMap.containsKey(regNo)) {
			if (regNoToParkingSlotMap.containsKey(regNo)) {
				throw new CustomParkingException(
						"veichle with registration no: " + regNo.toUpperCase() + " already exist in the parking lot");
			}
			return -1;
		}
		Integer freeSlot = freeSlotQueue.remove();
		ParkingSlot slot = new CarParkingSlot(freeSlot, new Car(regNo, colour));
		regNoToParkingSlotMap.put(regNo, slot);
		slotNoToParkingSlotMap.put(freeSlot, slot);
		availableSlot--;
		return freeSlot;

	}

	/**
	 * Function to deallocate the given slot.
	 * 
	 * @param slotNo
	 * @return {@link Boolean} flag to determine that slot is successfully
	 *         vacated or not.
	 * @throws CustomParkingException
	 */
	public Boolean deallocateSlot(int slotNo) throws CustomParkingException {
		checkIsParkingLotInitialized();
		if (slotNo <= 0 || slotNo > totalSlot || freeSlotQueue.contains(slotNo)) {
			throw freeSlotQueue.contains(slotNo) ? new CustomParkingException("Slot " + slotNo + " is already free")
					: new CustomParkingException("Slot " + slotNo + " does not exist in parking lot");
		}
		Boolean hasDeallocated = false;
		ParkingSlot slotToBeVaccate = null;
		for (Map.Entry<String, ParkingSlot> pair : regNoToParkingSlotMap.entrySet()) {
			ParkingSlot parkingSlot = pair.getValue();
			if (parkingSlot.getSlotNumber().equals(slotNo)) {
				hasDeallocated = true;
				freeSlotQueue.add(slotNo);
				availableSlot++;
				slotToBeVaccate = parkingSlot;
				break;
			}
		}

		if (slotToBeVaccate != null) {
			regNoToParkingSlotMap.remove(slotToBeVaccate.getVeichile().getRegNo());
			slotNoToParkingSlotMap.remove(slotToBeVaccate.getSlotNumber());
		}
		return hasDeallocated;
	}

	/**
	 * Return the list of all occupied pakring slots.
	 * 
	 * @return {@link List} of Parking Slot.
	 * @throws CustomParkingException
	 */
	public List<ParkingSlot> detailsOfOccupiedSlots() throws CustomParkingException {
		checkIsParkingLotInitialized();
		List<ParkingSlot> ocuppiedSlots = new ArrayList<ParkingSlot>();
		for (Map.Entry<Integer, ParkingSlot> pair : slotNoToParkingSlotMap.entrySet()) {
			ocuppiedSlots.add(pair.getValue());
		}
		return ocuppiedSlots;
	}

	/**
	 * Return the list of regNo of all car based on the colour.
	 * 
	 * @param colour
	 * @return {@link List} of regNo.
	 * @throws CustomParkingException
	 */
	public List<String> getAllRegNoOfGivenColourCar(Colour colour) throws CustomParkingException {

		checkIsParkingLotInitialized();
		List<String> listOfregNo = new ArrayList<String>();
		for (Map.Entry<Integer, ParkingSlot> pair : slotNoToParkingSlotMap.entrySet()) {
			Veichle veichle = pair.getValue().getVeichile();
			if (veichle.getColour().equals(colour)) {
				listOfregNo.add(veichle.getRegNo());
			}
		}
		return listOfregNo;
	}

	/**
	 * Return the slot no of all veichle parked in parking slot based on colour
	 * input.
	 * 
	 * @param colour
	 * @return
	 * @throws CustomParkingException
	 */
	public List<Integer> getSlotNoOfGivenColourCar(Colour colour) throws CustomParkingException {

		checkIsParkingLotInitialized();
		List<Integer> slotList = new ArrayList<Integer>();
		for (Map.Entry<Integer, ParkingSlot> pair : slotNoToParkingSlotMap.entrySet()) {
			Veichle veichle = pair.getValue().getVeichile();
			if (veichle.getColour().equals(colour)) {
				slotList.add(pair.getValue().getSlotNumber());
			}
		}
		return slotList;
	}

	/**
	 * Return slot no based on the given regNo.
	 * 
	 * @param regNo
	 * @return
	 * @throws CustomParkingException
	 */
	public Integer getSlotNoOfGivenRegNo(String regNo) throws CustomParkingException {

		checkIsParkingLotInitialized();
		regNo = regNo.toLowerCase();
		if (regNoToParkingSlotMap.containsKey(regNo)) {
			return regNoToParkingSlotMap.get(regNo).getSlotNumber();
		} else {
			return -1;
		}
	}

	/**
	 * Return Boolean to determine status of given slot.
	 * 
	 * @param slotNo
	 * @return
	 * @throws CustomParkingException
	 */
	public Boolean isGivenSlotFree(int slotNo) throws CustomParkingException {

		checkIsParkingLotInitialized();
		if (freeSlotQueue.contains(slotNo)) {
			return true;
		}
		return false;
	}

	public void close() {
		freeSlotQueue = null;
		regNoToParkingSlotMap = null;
		slotNoToParkingSlotMap = null;
		availableSlot = -1;
		isParkingLotInitialized = false;
		totalSlot = -1;
	}

	private void checkIsParkingLotInitialized() throws CustomParkingException {
		if (!isParkingLotInitialized) {
			throw new CustomParkingException("Parking lot is not initialized yet . please initialize before using it");
		}
	}

}
