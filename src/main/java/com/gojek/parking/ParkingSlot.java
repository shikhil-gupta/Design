package com.gojek.parking;

import lombok.Data;

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
