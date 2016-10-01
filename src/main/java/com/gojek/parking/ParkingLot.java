package com.gojek.parking;

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
		return result;
	}
	
	public String allocateSlotToVeichle(String regNo, Colour colour) throws CustomParkingException {
		String result = "";
		return result;
	}
	
	public String deallocateSlot(int slotNo) throws CustomParkingException {
		String result = "";
		return result;
	}
	public String detailsOfOccupiedSlots(int slotNo) throws CustomParkingException {
		String result = "";
		return result;
	}
	public String getAllRegNoOfGivenColourCar(Colour colour) throws CustomParkingException {
		String result = "";
		return result;
	}
	public String getSlotNoOfGivenColourCar(Colour colour) throws CustomParkingException {
		String result = "";
		return result;
	}
	public String getSlotNoOfGivenRegNo(String regNo) throws CustomParkingException {
		String result = "";
		return result;
	}
	
	
	
}
