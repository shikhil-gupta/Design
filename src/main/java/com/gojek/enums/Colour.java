package com.gojek.enums;

import com.gojek.exception.CustomParkingException;

public enum Colour {
	WHITE("white"), BLUE("blue"), BLACK("black"), RED("red");
	
	private String colourType;
	
	private Colour(String colourType) {
		this.colourType = colourType;
	}
	
	public static Colour getValueOf(String colourType) throws CustomParkingException {
		colourType = colourType.toLowerCase();
		for(Colour colour : values()) {
			if(colour.colourType.equals(colourType)) {
				return colour;
			}
		}
		throw new CustomParkingException("CustomParkingException : Enums class colour does not contains"
				+ colourType + "colour");
		
	}
}
