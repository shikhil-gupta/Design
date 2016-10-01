package com.gojek.app;

import java.io.BufferedReader;
import java.io.PrintWriter;

import com.gojek.exception.CustomParkingException;
import com.gojek.parking.ParkingLot;

/**
 * ParkingSystemApp client which will issue ticket to veichle and interact with customer.
 * @author shikhill.gupta
 *
 */
public class ParkingSystemApp 
{
	boolean exit;
	boolean inputMode;
	BufferedReader reader;
	PrintWriter out;
	ParkingLot parking;
	
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    
    private void initializeApp(String[] args) throws CustomParkingException {
    	
    	
    }
    
    private void startApp() throws CustomParkingException {
    	
    }
    
    private void closeApp() {
    	
    }
    
    private void parkCar(String[] commands) throws CustomParkingException {
    	
    }
    private void vacateParkingSlot(String[] commands) throws CustomParkingException {
    	
    }
    
    private void parkingLotStatus(String[] commands) throws CustomParkingException {
    	
    }
    private void regNoOfCarsWithColour(String[] commands) throws CustomParkingException {
    	
    }
    private void slotNoOfCarsWithColour(String[] commands) throws CustomParkingException {
    	
    }
    private void slotNoOfGivenRegNo(String[] commands) throws CustomParkingException {
    	
    }   
}


