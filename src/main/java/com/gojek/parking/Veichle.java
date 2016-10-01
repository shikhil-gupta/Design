package com.gojek.parking;

import com.gojek.enums.Colour;

import lombok.Data;

@Data
public abstract class Veichle {
	
	String regNo;
	Colour colour;

}
