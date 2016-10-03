package com.gojek.parking;

import lombok.Data;

/**
 * Abstract class for Parking slot. Extend this class depending upon the type of
 * veichle can accommodate in that parking slot.
 * 
 * @author shikhill.gupta
 *
 */
@Data
public abstract class ParkingSlot {

	private Boolean isOccupied = false;
	private Integer slotNumber;
	Veichle veichile;

	public ParkingSlot() {

	}

	public ParkingSlot(Boolean isOccupied, int slotNo, Veichle veichile) {
		this.isOccupied = isOccupied;
		this.slotNumber = slotNo;
		this.veichile = veichile;
	}

}
