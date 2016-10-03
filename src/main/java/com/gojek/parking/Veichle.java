package com.gojek.parking;

import com.gojek.enums.Colour;
import com.gojek.enums.VeichleType;

import lombok.Data;

/**
 * Abstract class to represent the veichle. Extend this class depending upon the
 * type of veichle.
 * 
 * @author shikhill.gupta
 *
 */
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
