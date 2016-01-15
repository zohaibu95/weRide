package com.zwz.models;

public class Comments {
	private int chatRoomID;
	private int commId;
	public int getCommId() {
		return commId;
	}
	public void setCommId(int commId) {
		this.commId = commId;
	}
	private int userID;
	private String comment;
	public Comments(){
		chatRoomID = userID = -1;
		comment = "";
	}
	public void setComment(int chatRoomId, int userId, String comment){
		this.chatRoomID = chatRoomId;
		this.userID = userId;
		this.comment = comment;
	}
	public int getChatRoomID() {
		return chatRoomID;
	}
	public void setChatRoomID(int chatRoomID) {
		this.chatRoomID = chatRoomID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
