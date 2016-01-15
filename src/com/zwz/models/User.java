package com.zwz.models;

public class User {
	private int uID;
	private String firstName;
	private String lastName;
	private String emailAddr;
	private String userName;
	private String password;
	private String phoneNum;
	private String address;
	private String country;
	private String city;
	
	public User(){
		firstName = lastName = emailAddr = userName = password = phoneNum = address = country = city = "";
		uID = -1;
	}
	
	public void setDetails(String fName, String lName, String emailAddr, String userName, String password, String phoneNum, String address, String city , String country){
		this.firstName = fName;
		this.lastName = lName;
		this.emailAddr = emailAddr;
		this.userName = userName;
		this.password = password;
		this.phoneNum = phoneNum;
		this.address = address;
		this.city = city;
		this.country = country;
	}

	public int getuID() {
		return uID;
	}

	public void setuID(int uID) {
		this.uID = uID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
}
