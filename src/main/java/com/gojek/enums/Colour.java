package com.gojek.enums;

import com.gojek.exception.CustomParkingException;

/**
 * Enum class to represent the type of veichle colour. please add the new type
 * of veichle colour before adding it.
 * 
 * @author shikhill.gupta
 *
 */
public enum Colour {
	WHITE("white"), BLUE("blue"), BLACK("black"), RED("red"), NOTVALIDCOLOUR("notvalidcolour");

	private String colourType;

	private Colour(String colourType) {
		this.colourType = colourType;
	}

	public static Colour getValueOf(String colourType) throws CustomParkingException {
		colourType = colourType.toLowerCase();
		for (Colour colour : values()) {
			if (colour.colourType.equals(colourType)) {
				return colour;
			}
		}
		throw new CustomParkingException("CustomParkingException : Not a valid colour " + colourType
				+ ", please add this new colour in to Enum Colour class");

	}
}
