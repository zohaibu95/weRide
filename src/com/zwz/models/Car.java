package com.zwz.models;

public class Car {
	private int availableSeats;
	private String carType;
	public Car(){
		availableSeats= -1;
		carType = "";
	}
	
	public void setCarDetails(int availSeats, String carType){
		this.availableSeats = availSeats;
		this.carType = carType;
	}
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	
	
}
