package com.gojek.parking;

import com.gojek.enums.Colour;
import com.gojek.enums.VeichleType;

import lombok.Data;

@Data
public abstract class Veichle {

	String regNo;
	Colour colour;
	VeichleType veichleType;

	public Veichle() {

	}

	public Veichle(String regNo, Colour colour, VeichleType veichleType) {
		this.regNo = regNo;
		this.colour = colour;
		this.veichleType = veichleType;
	}

}
