package com.gojek.parking;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.gojek.enums.Colour;
import com.gojek.exception.CustomParkingException;

import lombok.Data;

@Data
public class ParkingLot {

	PriorityQueue<Integer> freeSlotQueue;
	Map<String, ParkingSlot> hashMap;
	int availableSlot;
	boolean isParkingLotInitialized;
	int totalSlot;

	public String initializeParkingLot(int totalSlot) throws CustomParkingException {
		String result = "";
		this.totalSlot = totalSlot;
		this.availableSlot = totalSlot;
		try {
			freeSlotQueue = new PriorityQueue<Integer>(totalSlot);
		} catch (IllegalArgumentException ex) {
			throw new CustomParkingException(
					"Exception message " + ex.getMessage() + "Exception stack trace " + ex.getStackTrace());
		}
		for (int i = 1; i <= totalSlot; i++) {
			freeSlotQueue.add(i);
		}
		hashMap = new HashMap<String, ParkingSlot>();
		isParkingLotInitialized = true;
		result = "Created a parking lot with " + this.totalSlot + " slots\n";
		return result;
	}

	public String allocateSlotToVeichle(String regNo, Colour colour) {
		String result = "";
		if (availableSlot <= 0 || freeSlotQueue.isEmpty() || hashMap.containsKey(regNo)) {
			result = hashMap.containsKey(regNo) ? "Car with regNo " + regNo + "already parked in parking lot\n"
					: "Sorry, parking lot is full\n";
			return result;
		}
		Integer availableSlot = freeSlotQueue.remove();
		ParkingSlot slot = new CarParkingSlot(availableSlot, new Car(regNo, colour));
		hashMap.put(regNo, slot);
		availableSlot--;
		result = "Allocated slot number: " + availableSlot + "\n";
		return result;
	}

	public String deallocateSlot(int slotNo) {
		String result = "";
		if (slotNo <= 0 || slotNo > totalSlot || freeSlotQueue.contains(slotNo)) {
			result = freeSlotQueue.contains(slotNo) ? "Slot " + slotNo + " is already free"
					: "Slot " + slotNo + " does not exist in parking lot\n";
			return result;
		}
		String regNo = "";
		for (Map.Entry<String, ParkingSlot> pair : hashMap.entrySet()) {
			ParkingSlot parkingSlot = pair.getValue();
			if (parkingSlot.getSlotNumber().equals(slotNo)) {
				regNo = pair.getKey();
				break;
			}
		}
		if (!regNo.equals("")) {
			hashMap.remove(regNo);
			freeSlotQueue.add(slotNo);
			availableSlot++;
			result = "Slot number" + slotNo + "is free";
		}

		return result;
	}

	public String detailsOfOccupiedSlots() {
		String result = "";
		for (Map.Entry<String, ParkingSlot> pair : hashMap.entrySet()) {
			Veichle veichle = pair.getValue().getVeichile();
			result += pair.getValue().getSlotNumber() + "\t" + pair.getKey() + "\t" + veichle.getColour() + "\n";
		}
		return result;
	}

	public String getAllRegNoOfGivenColourCar(Colour colour) {
		String result = "";
		for (Map.Entry<String, ParkingSlot> pair : hashMap.entrySet()) {
			Veichle veichle = pair.getValue().getVeichile();
			if (veichle.getColour().equals(colour)) {
				result += pair.getKey() + " ,";
			}
		}
		return result;
	}

	public String getSlotNoOfGivenColourCar(Colour colour) {
		String result = "";
		for (Map.Entry<String, ParkingSlot> pair : hashMap.entrySet()) {
			Veichle veichle = pair.getValue().getVeichile();
			if (veichle.getColour().equals(colour)) {
				result += pair.getValue().getSlotNumber() + " ,";
			}
		}
		return result;
	}

	public String getSlotNoOfGivenRegNo(String regNo) {
		String result = "";
		if (hashMap.containsKey(regNo)) {
			result = hashMap.get(regNo).getSlotNumber().toString();
		} else {
			result = "No car exist in parking lot with regNo " + regNo;
		}
		return result;
	}

	public void close() {
		freeSlotQueue = null;
		hashMap = null;
		availableSlot = -1;
		isParkingLotInitialized = false;
		totalSlot = -1;
	}

}
