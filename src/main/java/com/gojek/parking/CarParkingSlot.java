package com.gojek.parking;

import lombok.Data;

@Data
public class CarParkingSlot extends ParkingSlot {

	public CarParkingSlot(int slotNo, Veichle veichle) {
		super(Boolean.TRUE, slotNo, veichle);
	}
}
