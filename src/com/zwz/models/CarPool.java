package com.zwz.models;

public class CarPool {
	private int cID;
	public int getcID() {
		return cID;
	}
	public void setcID(int cID) {
		this.cID = cID;
	}
	private int posterId;
	private int routeId;
	private int chatRoomId;
	private String city;
	private String description;
	public CarPool(){
		posterId = routeId= chatRoomId = -1;
		city = description = "";
		cID = -1;
	}
	public void setCarPoolDetails(int posterID, int routeID, int chatRoomID, String city, String description){
		this.posterId = posterID;
		this.routeId = routeID;
		this.chatRoomId = chatRoomID;
		this.city = city;
		this.description = description;
		
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPosterId() {
		return posterId;
	}
	public void setPosterId(int posterId) {
		this.posterId = posterId;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public int getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(int chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
