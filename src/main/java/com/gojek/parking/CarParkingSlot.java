package com.gojek.parking;

/**
 * Class to define the Parking slot for car only.
 * 
 * @author shikhill.gupta
 *
 */
public class CarParkingSlot extends ParkingSlot {

	public CarParkingSlot(int slotNo, Veichle veichle) {
		super(Boolean.TRUE, slotNo, veichle);
	}
}
