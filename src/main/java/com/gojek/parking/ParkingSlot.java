package com.gojek.parking;

import lombok.Data;

@Data
public class ParkingSlot {
	
	private boolean isOccupied;    
    private int slotNumber;
    Veichle veichile;
	
}
