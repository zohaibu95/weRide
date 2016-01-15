package com.zwz.models;

public class PassengerRequest {
	private int pID;
	private int carpoolID;
	private int passengerID;
	private String passengerStartPoint;
	private int acceptRequestID;
	public PassengerRequest(){
		pID = carpoolID = passengerID = acceptRequestID = -1;
		passengerStartPoint = "";
	}
	public int getpID() {
		return pID;
	}
	public void setpID(int pID) {
		this.pID = pID;
	}
	public int getCarpoolID() {
		return carpoolID;
	}
	public void setCarpoolID(int carpoolID) {
		this.carpoolID = carpoolID;
	}
	public int getPassengerID() {
		return passengerID;
	}
	public void setPassengerID(int passengerID) {
		this.passengerID = passengerID;
	}
	public String getPassengerStartPoint() {
		return passengerStartPoint;
	}
	public void setPassengerStartPoint(String passengerStartPoint) {
		this.passengerStartPoint = passengerStartPoint;
	}
	public int getAcceptRequestID() {
		return acceptRequestID;
	}
	public void setAcceptRequestID(int acceptRequestID) {
		this.acceptRequestID = acceptRequestID;
	}
	
}
