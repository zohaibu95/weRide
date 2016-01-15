package com.zwz.controller;

import java.util.ArrayList;

import android.content.Context;

import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.models.Car;
import com.zwz.models.CarPool;
import com.zwz.models.Comments;
import com.zwz.models.PassengerRequest;
import com.zwz.models.Route;
import com.zwz.models.User;

public class Controller 
{
	private static Controller controller = null;
	Route route;
	Car car;
	CarPool carpool;
	Comments comment;
	PassengerRequest passengerrequest;
	User user;

	public Route getRoute() {
		return route;
	}

	public void setRoute(String dest, String arr , String time , String date , int seats) {
		this.route = new Route(); 
		this.route.setRoute(dest, arr, time, date, seats);
	}

	public Car getCar() {
		return car;
	}

	public void setCar(int availSeats, String carType ) {
		this.car = new Car();
		this.car.setCarDetails(availSeats, carType);
	}

	public CarPool getCarpool() {
		return carpool;
	}

	public void setCarpool(CarPool carpool) {
		this.carpool = carpool;
	}

	public Comments getComment() {
		return comment;
	}

	public void setComment(Comments comment) {
		this.comment = new Comments(); 
		this.comment = comment;
	}

	public PassengerRequest getPassengerrequest() {
		return passengerrequest;
	}

	public void setPassengerrequest(PassengerRequest passengerrequest) {
		this.passengerrequest = passengerrequest;
	}

	public User getUser() {
		return user;
	}

	public void setUser(String fName, String lName, String emailAddr, String userName, String password, String phoneNum, String address, String city , String country) {
		this.user = new User();
		this.user.setDetails(fName, lName, emailAddr, userName, password, phoneNum, address, city, country);;
	}

	public static Controller getController() {
		if(controller == null){
			controller = new Controller();
			return controller;
		}
		return controller;
	}

	public static void setController(Controller controller) {
		Controller.controller = controller;
	}

	private Controller(){
		controller = null;
	}
	
	public ArrayList<User> CarpoolMemberList(int cID , Context context){
		
		DatabaseHandler db = new DatabaseHandler(context);
		ArrayList<User> acceptedUsers = db.viewAllAcceptedCarpoolMembers(cID);
		db.close();
		return acceptedUsers;
	}
	
	public ArrayList<Route> Fragment1(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
  		ArrayList<Route>route = db.getAllRoutes();
  		db.close();
  		return route;
	}
	
	public ArrayList<PassengerRequest> Fragment3(Context context){
		 DatabaseHandler db = new DatabaseHandler(context);
	     ArrayList<PassengerRequest> plist = db.getNotification();
	     db.close();
	     return plist;
	}
	public Route Fragment3Route(int cID,Context context){
		 DatabaseHandler db = new DatabaseHandler(context);
		 Route r = db.getRouteByCarPoolID(cID);
	     db.close();
	     return r;
	}
	public String Fragment6GetPassword(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		String password = db.getOldPassword();
		db.close();
		return password;
	}
	public boolean Fragment6SetPassword(String pass , Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		boolean check = db.updatePassword(pass);
		db.close();
		return check;
	}
	
	public ArrayList<Route> Fragment7(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
        ArrayList<CarPool> carpools = db.getSubscribedCarPool();
        ArrayList<Route> route = new ArrayList<Route>();
        for(int i=0; i<carpools.size(); i++){
        route.add(db.getRouteByCarPoolID(carpools.get(i).getcID())); 
        }
        return route;
	}
	
	public boolean FragmentTab1(String description, Context context){
		DatabaseHandler db1 = new DatabaseHandler(context);
		String city  = db1.getUserCity();
		boolean status = db1.createCarPool(this.route, this.car, city, description);
		db1.close();
		return status;
	}
	
	public ArrayList<Route> FragmentTab2(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
  		ArrayList<Route>route = db.getAllYourRoutes();
  		//ArrayList<CarPool> carpools = db.getAllCarPool();
  		db.close();
  		return route;
	}
	
	public boolean LoginActivity(String username, String pass, Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		boolean status = db.verifyUser(username, pass);
		db.close();
		return status;
	}
	public ArrayList<Comments> GetComments(int chatroomID , Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		ArrayList<Comments> commentlist = db.getComments(chatroomID);
		db.close();
  		return commentlist;
	}
	
	public String GetUserName(int UserID , Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		String name = db.getUserName(UserID);
		db.close();
		return name;
	}
	
	public boolean PostComment(int chatroomID, String NewComment, Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		boolean status = db.addComment(chatroomID, NewComment);
		db.close();
		return status;
	}
	
	public boolean RegisterUser( Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		boolean status = db.addUser(this.user);
		db.close();
		return status;
	}
	
	public boolean SingleItemViewCarpoolMemberRemoveMember(int userID, int carpoolID,Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		CarPool carpool = db.getCarPoolFromCarPoolID(carpoolID);
		int seats = db.getNoOfSeats(carpool);
		boolean result = db.updateNoOfSeats(carpool, (seats+1));
		boolean status = db.deleteAlreadyAcceptedMember(userID, carpoolID);
		db.close();
		return status;
	}
	
	public ArrayList<Object> SingleItemViewPost(int routeId ,Context context){
		DatabaseHandler db = new DatabaseHandler(context);
	    final  CarPool carpool = db.getCarPoolDetails(routeId);
	    User user = db.getPosterDetails(carpool.getPosterId());
	    db.close();
	    Object obj = (Object)carpool;
	    Object obj2 = (Object)user;
	    ArrayList<Object> objList = new ArrayList<Object>();
	    objList.add(obj2);
	    objList.add(obj);
	    return objList;
	}
	
	public boolean deleteCarpool(CarPool carpool,Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		boolean status = db.deleteCarpool(carpool);
		db.close();
		return status;
	}
	
	public boolean leaveCarPool(CarPool carpool, Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int seats = db.getNoOfSeats(carpool);
		boolean result = db.updateNoOfSeats(carpool, (seats+1));
		boolean status = db.deleteCarpoolMember(carpool.getcID());
		db.close();
		return status;
	}
	
	public String  joinCarpool(CarPool carpool , Context context)
	{
		DatabaseHandler db = new DatabaseHandler(context);
		int seats = db.getNoOfSeats(carpool);
		if(seats > 0)
		{
			int passengerID = db.addPassenger();
			boolean result = db.isPassengerAlreadyAccepted(carpool.getcID(), passengerID);
			if(result == false)
			{
				boolean status = db.addPessengerRequest(carpool.getcID(), passengerID);
				if(status){
					db.close();
					return "Your request has been sent to car pool owner for approval";
				}else{
					db.close();
					return " Your request is already sent";
				}
				
			}
			else{
				db.close();
				return " Your request is already accepted for this carpool";
			}
		}
		else
		{
			db.close();
			return " No seats in this carpool";
		}
	}
	
	public User GetPassengerDetails(int passengerID,Context context ){
		DatabaseHandler db = new DatabaseHandler(context);
        User user = db.getPassengerDetails(passengerID);
        db.close();
        return user;
	}
	
	public boolean RejectRequest(int passengerID,Context context){
		DatabaseHandler db3 = new DatabaseHandler(context);
		CarPool carpool = db3.getCarPoolObjectFromPassengerID(passengerID);
		boolean result = db3.deleteRequest(carpool.getcID(),passengerID);
		db3.close();
		return result;
	}
	
	public int GetNoSeats(int passengerID,Context context){
		DatabaseHandler db2 = new DatabaseHandler(context);
		CarPool carpool = db2.getCarPoolObjectFromPassengerID(passengerID);
		int seats = db2.getNoOfSeats(carpool);
		db2.close();
		return seats;
	}
	
	public boolean AcceptRequest(int seats ,int passengerID,Context context){
		DatabaseHandler db2 = new DatabaseHandler(context);
		CarPool carpool = db2.getCarPoolObjectFromPassengerID(passengerID);
		boolean result = db2.updateNoOfSeats(carpool, (seats-1));
		result = db2.addAcceptedRequest(passengerID, carpool.getcID());
		result = db2.deleteRequest(carpool.getcID(),passengerID );
		db2.close();
		return result;
	}
}