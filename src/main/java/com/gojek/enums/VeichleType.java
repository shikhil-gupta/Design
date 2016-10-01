package com.gojek.enums;

import com.gojek.exception.CustomParkingException;

public enum VeichleType {
	TWOWHEELER("twowheeler"), FOURWHEELER("fourwheeler");

	private String veichleType;

	private VeichleType(String veichleType) {
		this.veichleType = veichleType;
	}

	public static VeichleType getValueOf(String veichleType) throws CustomParkingException {
		veichleType = veichleType.toLowerCase();
		for (VeichleType type : values()) {
			if (type.veichleType.equals(veichleType)) {
				return type;
			}
		}
		throw new CustomParkingException(
				"CustomParkingException : Enums class veichle does not contains" + veichleType + "Veichle");

	}
}
