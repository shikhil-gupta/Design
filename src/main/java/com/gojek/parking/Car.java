package com.gojek.parking;

import com.gojek.enums.Colour;
import com.gojek.enums.VeichleType;

import lombok.Data;

@Data
public class Car extends Veichle {
	public Car(String regNo, Colour colour) {
		super(regNo, colour, VeichleType.FOURWHEELER);
	}

}
