package com.zwz.models;

public class Route {
	private int routeID;
	private String destinationLoc;
	private String arrivalLoc;
	private String leavingTime;
	private String leavingDate;
	private int availSeats;
	public Route(){
		destinationLoc = arrivalLoc = leavingTime = leavingDate = "";
		availSeats = -1;
		routeID = -1;
	}
	public void setRoute(String dest, String arr, String time, String date, int seats){
		this.destinationLoc = dest;
		this.arrivalLoc = arr;
		this.leavingTime = time;
		this.leavingDate = date;
		this.availSeats = seats;
	}
	public int getRouteID() {
		return routeID;
	}
	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	public String getDestinationLoc() {
		return destinationLoc;
	}
	public void setDestinationLoc(String destinationLoc) {
		this.destinationLoc = destinationLoc;
	}
	public String getArrivalLoc() {
		return arrivalLoc;
	}
	public void setArrivalLoc(String arrivalLoc) {
		this.arrivalLoc = arrivalLoc;
	}
	public String getLeavingTime() {
		return leavingTime;
	}
	public void setLeavingTime(String leavingTime) {
		this.leavingTime = leavingTime;
	}
	public String getLeavingDate() {
		return leavingDate;
	}
	public void setLeavingDate(String leavingDate) {
		this.leavingDate = leavingDate;
	}
	public int getAvailSeats() {
		return availSeats;
	}
	public void setAvailSeats(int availSeats) {
		this.availSeats = availSeats;
	}
	
}
