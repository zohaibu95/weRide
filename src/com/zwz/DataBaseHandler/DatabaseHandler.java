package com.zwz.DataBaseHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import com.zwz.models.Car;
import com.zwz.models.CarPool;
import com.zwz.models.Comments;
import com.zwz.models.PassengerRequest;
import com.zwz.models.Route;
import com.zwz.models.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
    private static String DB_PATH = "/data/data/com.zwz.weRide/databases/";
    private static String DB_NAME = "weride.sqlite";
    private SQLiteDatabase myDataBase; 
	private final Context myContext;
		public DatabaseHandler(Context context) {
	    	super(context, DB_NAME, null, 1);
	        this.myContext = context;
	 }	
	
	    public void createDataBase() throws IOException{
		  
	    	boolean dbExist = checkDataBase();
	    	SQLiteDatabase db_read = null;
	    	if(dbExist){
	    		//do nothing - database already exist
	    	}else{
	 
	    		db_read = this.getReadableDatabase();
	    		db_read.close();
	        	try {
	        		this.close();
	    			copyDataBase();
	 
	    		} catch (IOException e) {
	 
	        		throw new Error(e);
	 
	        	}
	    	}
	 
	    }
	  
	    private boolean checkDataBase(){
	    	 
	    	SQLiteDatabase checkDB = null;
	 
	    	try{
	    		String myPath = DB_PATH + DB_NAME;
	    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	
	    	}catch(SQLiteException e){
	 
	    		//database does't exist yet.
	 
	    	}
	 
	    	if(checkDB != null){
	 
	    		checkDB.close();
	 
	    	}
	 
	    	return checkDB != null ? true : false;
	    }
	    
	    private void copyDataBase() throws IOException{
	    	 
	    	InputStream myInput = myContext.getAssets().open(DB_NAME);
	 
	    	String outFileName = DB_PATH + DB_NAME;
	 
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);//
	    	}
	 
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	 
	    }
	    
	    public void openDataBase() throws SQLException{
	    	 
	    	//Open the database
	        String myPath = DB_PATH + DB_NAME;
	    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	 
	    }
	    	   
	    @Override
		public synchronized void close() {
	 
	    	    if(myDataBase != null)
	    		    myDataBase.close();
	 
	    	    super.close();
	 
		}
	 
		@Override
		public void onCreate(SQLiteDatabase db) {
	 
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	 
		}
		
		public boolean isUserExist(String userName){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT user_name FROM tbl_user WHERE user_name='"+userName+"'";
			Cursor cursor = db.rawQuery(query, null);
			int len = cursor.getCount();
			db.close();
			if (len == 0)
				return false;
			else
				return true;
		}
		
		public boolean addUser(User u) {
		if (isUserExist(u.getUserName())){
			return false;
			}else{
				SQLiteDatabase db = this.getWritableDatabase();
				String query = "SELECT max(_id) FROM tbl_user";
				Cursor cursor = db.rawQuery(query, null);
				int _id = cursor.moveToFirst()?cursor.getInt(0):-1;
				if (_id != -1){
					ContentValues values = new ContentValues();
					values.put("_id", _id+1);
					values.put("user_name", u.getUserName());
					values.put("user_password", u.getPassword());
					values.put("user_phoneno",  u.getPhoneNum());
					values.put("user_email",u.getEmailAddr());
					values.put("user_city",u.getCity());
					values.put("user_address",u.getAddress());
					values.put("user_country",u.getCountry());
					String fullName = u.getFirstName() + " " + u.getLastName();
					values.put("user_fullname",fullName);
					db.insert("tbl_user",null,values);
					db.close();
					SQLiteDatabase db1 = this.getWritableDatabase();
					query = "SELECT max(_id) FROM tbl_login";
					Cursor cursor1 = db1.rawQuery(query, null);
					int login_id = cursor1.moveToFirst()?cursor1.getInt(0):-1;
					if (login_id != -1){
						ContentValues value1 = new ContentValues();
						value1.put("_id", login_id+1);
						value1.put("user_id",_id+1);
						value1.put("status", 1);
						db1.insert("tbl_login", null, value1);
						db1.close();
					}
					return true;
				}else{
					return false;
				}
		}
		}
		
		public boolean offlineUser(){
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete("tbl_login", "status=1",null);
			db.close();
			return true;
		}
	
		public boolean verifyUser(String userName, String password){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT _id FROM tbl_user WHERE user_name='"+userName+"' AND user_password='"+password+"'";
			Cursor cursor = db.rawQuery(query, null);
			int len = cursor.getCount();
			int _id = cursor.moveToFirst()?cursor.getInt(0):-1;
			ContentValues value = new ContentValues();
			if (_id != -1){
				value.put("_id", 1);
				value.put("user_id", _id);
				value.put("status", 1);
				db.insert("tbl_login",null,value);
			}
			db.close();
			if (len == 0)
				return false;
			else
				return true;
		}
		
		public int addRoute(Route route){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT max(_id) FROM tbl_route";
			Cursor cursor = db.rawQuery(query, null);
			int route_id = cursor.moveToFirst()?cursor.getInt(0):-1;
			if (route_id != -1){
				ContentValues values = new ContentValues();
				values.put("_id", route_id+1);
				values.put("start_point", route.getDestinationLoc());
				values.put("destination_point", route.getArrivalLoc());
				values.put("departure_time",  route.getLeavingTime());
				values.put("departure_date",route.getLeavingDate());
				db.insert("tbl_route",null,values);		
				db.close();
				return route_id+1;
			}else{
				return -1;
			}
		}
		
		public boolean addCar(Car car, int carPoolID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT max(_id) FROM tbl_car";
			Cursor cursor = db.rawQuery(query, null);
			int car_id = cursor.moveToFirst()?cursor.getInt(0):-1;
			if (car_id != -1){
				ContentValues values = new ContentValues();
				values.put("_id", car_id+1);
				values.put("available_seats", car.getAvailableSeats());
				values.put("car_type", car.getCarType());
				values.put("carpool_id",  carPoolID);
				db.insert("tbl_car",null,values);		
				db.close();
				return true;
			}else{
				return false;
			}
		}
		
		public int addPoster( int userID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT _id,user_id FROM tbl_poster where user_id="+userID;
			Cursor c = db.rawQuery(query, null);
			int alreadyPosterID = c.moveToFirst()?c.getInt(0):-1;
			if (c.getCount() > 0){
				return alreadyPosterID;
			}else{			
				query = "SELECT max(_id) FROM tbl_poster";
				Cursor cursor = db.rawQuery(query, null);
				int poster_id = cursor.moveToFirst()?cursor.getInt(0):-1;
				if (poster_id != -1){
					ContentValues values = new ContentValues();
					values.put("_id", poster_id+1);
					values.put("user_id",  userID);
					db.insert("tbl_poster",null,values);		
					db.close();
					return poster_id+1;
				}else{
					return -1;
				}
			}
		}
		
		public int addChatroom(){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT max(_id) FROM tbl_chatroom";
			Cursor cursor = db.rawQuery(query, null);
			int chatroom_id = cursor.moveToFirst()?cursor.getInt(0):-1;
			if (chatroom_id != -1){
				ContentValues values = new ContentValues();
				values.put("_id", chatroom_id+1);
				db.insert("tbl_chatroom",null,values);		
				db.close();
				return chatroom_id+1;
			}else{
				return chatroom_id;
			}
		}
		
		public int getLoginUserID(){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT user_id FROM tbl_login WHERE status=1";
			Cursor cursor = db.rawQuery(query, null);
			int userID = cursor.moveToFirst()?cursor.getInt(0):-1;
			db.close();
			if (userID != -1)
				return userID;
			else
				return -1;
		}
		
		public String getUserCity(){
			int loginUserID = this.getLoginUserID();
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT user_city FROM tbl_user WHERE _id="+loginUserID;
			Cursor c = db.rawQuery(query, null);
			String city = c.moveToFirst()?c.getString(0):"-1";
			db.close();
			return city;
		}
		
		public boolean createCarPool(Route r, Car c, String city, String description){
			int loggedInUserID = this.getLoginUserID();
			if (loggedInUserID == -1)
				return false;
			else{
				int posterID = this.addPoster(loggedInUserID);
				if (posterID == -1)
					Log.i("Error in poster ID", "ERRROR in getting poster ID ");
				else{
					int routeId = this.addRoute(r);
					if (routeId != -1){
						SQLiteDatabase db2 = this.getWritableDatabase();
						String query = "SELECT max(_id) FROM tbl_carpool";
						Cursor cursor = db2.rawQuery(query, null);
						int carpool_id = cursor.moveToFirst()?cursor.getInt(0):-1;
						boolean status = this.addCar(c ,carpool_id+1);
						db2.close();
						if (!status){
							Log.i("Error","Could not insert car");
							//db.close();
						}else{
							int chatRoomID = this.addChatroom();
							SQLiteDatabase db3 = this.getWritableDatabase();
							//String query1 = "INSERT into tbl_carpool VALUES("+carpool_id+","+posterID+","+routeId+","+chatRoomID+",'"+city+"','"+description+"')";
							ContentValues value = new ContentValues();
							value.put("_id", carpool_id+1);
							value.put("poster_id", posterID);
							value.put("route_id",routeId);
							value.put("chatroom_id", chatRoomID);
							value.put("city", city);
							value.put("description", description);
							db3.insert("tbl_carpool", null, value);
							db3.close();
							return true;
						}
					}else{
						Log.i("Error in route ID", "ERRROR in getting route ID ");
					
					}
				}
			}
			
			return false;
		}

		public ArrayList<CarPool> getAllCarPool(){
			String userCity = this.getUserCity();
			int userID = this.getLoginUserID();
			int pID = this.addPoster(userID);
			if (!userCity.equals("-1")){
				SQLiteDatabase db = this.getWritableDatabase();
				String query = "SELECT * FROM tbl_carpool WHERE city='"+userCity+"' AND poster_id != "+pID;
				Cursor c = db.rawQuery(query, null);
				ArrayList<CarPool> list  = new ArrayList<CarPool>();
			    if (c.moveToFirst()) {
			        do {
			        		CarPool carpool = new CarPool();
			        		carpool.setcID(c.getInt(0));
			        		carpool.setPosterId(c.getInt(1));
			        		carpool.setRouteId(c.getInt(2));
			        		carpool.setChatRoomId(c.getInt(3));
			        		carpool.setCity(c.getString(4));
			        		carpool.setDescription(c.getString(5));
			        		list.add(carpool);
			        } while (c.moveToNext());
			    }
			    db.close();
			    return list;
			}else{
				Log.i("Error in city","Cannot get city");
				return null;
			}
		}
		
		public ArrayList<CarPool> getAllYourCarPool(){
			//String userCity = this.getUserCity();
			
			int userID = this.getLoginUserID();
			int posterID = this.addPoster(userID);
			if (posterID != -1){
				SQLiteDatabase db = this.getWritableDatabase();
				
				String query = "SELECT * FROM tbl_carpool WHERE poster_id='"+posterID+"'";
				Cursor c = db.rawQuery(query, null);
				ArrayList<CarPool> list  = new ArrayList<CarPool>();
			    if (c.moveToFirst()) {
			        do {
			        		CarPool carpool = new CarPool();
			        		carpool.setcID(c.getInt(0));
			        		carpool.setPosterId(c.getInt(1));
			        		carpool.setRouteId(c.getInt(2));
			        		carpool.setChatRoomId(c.getInt(3));
			        		carpool.setCity(c.getString(4));
			        		carpool.setDescription(c.getString(5));
			        		list.add(carpool);
			        } while (c.moveToNext());
			    }
			    db.close();
			    return list;
			}else{
				Log.i("Error in city","Cannot get city");
				return null;
			}
		}
		
		public Route getRoute(int routeID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT * FROM tbl_route WHERE _id=" + routeID;
			Cursor c = db.rawQuery(query, null);
			Route route = new Route();
			 if (c.moveToFirst()) {
				 route.setRouteID(c.getInt(0));
				 route.setDestinationLoc(c.getString(1));
				 route.setArrivalLoc(c.getString(2));
				 route.setLeavingTime(c.getString(3));
				 route.setLeavingDate(c.getString(4));
			  }
			 db.close();
			 return route;
		}
		
		public ArrayList<Route> getAllRoutes(){
			ArrayList<CarPool> car = new ArrayList<CarPool>();
			car = this.getAllCarPool();
			ArrayList<Integer>routeId = new ArrayList<Integer>();
			for(CarPool c : car){
				routeId.add(c.getRouteId());
			}
			ArrayList<Route> routes = new ArrayList<Route>();
			for (int i=0; i<routeId.size(); i++){
				routes.add(this.getRoute(routeId.get(i)));
			}
			return routes;
		}
		
		public ArrayList<Route> getAllYourRoutes(){
			ArrayList<CarPool> car = new ArrayList<CarPool>();
			car = this.getAllYourCarPool();
			ArrayList<Integer>routeId = new ArrayList<Integer>();
			for(CarPool c : car){
				routeId.add(c.getRouteId());
			}
			ArrayList<Route> routes = new ArrayList<Route>();
			for (int i=0; i<routeId.size(); i++){
				routes.add(this.getRoute(routeId.get(i)));
			}
			return routes;
		}
		
		public User getPosterDetails(int posterID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT user_id FROM tbl_poster WHERE _id="+posterID;
			Cursor c = db.rawQuery(query, null);
			int userID = c.moveToFirst()?c.getInt(0):-1;
			query = "SELECT * FROM tbl_user WHERE _id="+userID;
			Cursor c1 = db.rawQuery(query, null);
			User u = new User();
			if (c1.moveToFirst()){
				u.setuID(c1.getInt(0));
				u.setUserName(c1.getString(1));
				u.setPassword(c1.getString(2));
				u.setPhoneNum(c1.getString(3));
				u.setEmailAddr(c1.getString(4));
				u.setCity(c1.getString(5));
				u.setAddress(c1.getString(6));
				u.setCountry(c1.getString(7));
				String fullName = c1.getString(8);
				String[] tokens = fullName.split(" ");
				u.setFirstName(tokens[0]);
				u.setLastName(tokens[1]);
			}
			db.close();
			return u;
		}

		public CarPool getCarPoolDetails(int routeID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT * FROM tbl_carpool WHERE route_id="+routeID;
			Cursor c = db.rawQuery(query, null);
			CarPool carpool = new CarPool();
			if (c.moveToFirst()){
				carpool.setcID(c.getInt(0));
				carpool.setPosterId(c.getInt(1));
				carpool.setRouteId(c.getInt(2));
				carpool.setChatRoomId(c.getInt(3));
				carpool.setCity(c.getString(4));
				carpool.setDescription(c.getString(5));
			}
			db.close();
			return carpool;
		}
		
		public int addPassenger( ){
			int userID = this.getLoginUserID();
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT _id,user_id FROM tbl_passenger where user_id="+userID;
			Cursor c = db.rawQuery(query, null);
			int alreadyPassengerID = c.moveToFirst()?c.getInt(0):-1;
			if (c.getCount() > 0){
				return alreadyPassengerID;
			}else{			
				query = "SELECT max(_id) FROM tbl_passenger";
				Cursor cursor = db.rawQuery(query, null);
				int p_id = cursor.moveToFirst()?cursor.getInt(0):-1;
				if (p_id != -1){
					ContentValues values = new ContentValues();
					values.put("_id", p_id+1);
					values.put("user_id",  userID);
					db.insert("tbl_passenger",null,values);		
					db.close();
					return p_id+1;
				}else{
					return -1;
				}
			}
		}
		
		public boolean addPessengerRequest(int carPoolID, int pessengerID){
			boolean result = this.isPassengerAlreadyRequested(carPoolID, pessengerID);
			if( result == false)
			{
				SQLiteDatabase db = this.getWritableDatabase();
				String query = "SELECT max(_id) FROM tbl_passenger_request";
				Cursor cursor = db.rawQuery(query, null);
				int _id = cursor.moveToFirst()?cursor.getInt(0):-1;
				if (_id != -1){
					ContentValues values = new ContentValues();
					values.put("_id", _id+1);
					values.put("carpool_id", carPoolID);
					values.put("passenger_id", pessengerID);
					values.put("passenger_startpoint", "not");
					db.insert("tbl_passenger_request", null, values);
					db.close();
					return true;
				}else{
					return false;
				}
			}
			else
			{return false;}
		}
		
		public ArrayList<PassengerRequest> getNotification(){
			
			ArrayList<CarPool> carpools = this.getAllYourCarPool();
			ArrayList<PassengerRequest> passengerList = new ArrayList<PassengerRequest>();
			for(CarPool c : carpools){
				SQLiteDatabase db = this.getWritableDatabase();
				String query = "SELECT * FROM tbl_passenger_request WHERE carpool_id="+c.getcID();
				Cursor cursor = db.rawQuery(query, null);
				if(cursor.getCount() > 0){
					
					if (cursor.moveToFirst()){
						do{
							PassengerRequest p = new PassengerRequest();
							p.setpID(cursor.getInt(0));
							p.setCarpoolID(cursor.getInt(1));
							p.setPassengerID(cursor.getInt(2));
							passengerList.add(p);
							Log.i("get notification","inserted");
						} while (cursor.moveToNext());
						
					}else{
						Log.i("get notification","not inserted");
					}
				}else{
					Log.i("get notification","in else condition");
				}
				db.close();
			}
		
			return passengerList;
		}
		
		public Route getRouteByCarPoolID(int cID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT route_id FROM tbl_carpool WHERE _id="+cID;
			Cursor c = db.rawQuery(query, null);
			if (c.getCount() > 0){
				int routeID = c.moveToFirst()?c.getInt(0):-1;
				db.close();
				SQLiteDatabase db1 = this.getWritableDatabase();
				query = "SELECT * FROM tbl_route WHERE _id="+routeID;
				Cursor c1 = db1.rawQuery(query, null);
				if (c1.getCount() > 0){
					Route route = new Route();
					if (c1.moveToFirst()){
						route.setRouteID(c1.getInt(0));
						route.setDestinationLoc(c1.getString(1));
						route.setArrivalLoc(c1.getString(2));
						route.setLeavingTime(c1.getString(3));
						route.setLeavingDate(c1.getString(4));	
						Log.i("get route","setting route");
					}
					
					db1.close();
					return route;
				}
			}
			return null;	
		}
		
		public User getUserByPassengerID(int pID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT user_id FROM tbl_passenger WHERE _id="+pID;
			Cursor c = db.rawQuery(query, null);
			int userID = c.moveToFirst()?c.getInt(0):-1;
			db.close();
			SQLiteDatabase db1 = this.getWritableDatabase();
			query = "SELECT * FROM tbl_user WHERE _id="+userID;
			Cursor c1 = db1.rawQuery(query, null);
			User u = new User();
			if (c1.moveToFirst()){
				u.setuID(c1.getInt(0));
				u.setUserName(c1.getString(1));
				u.setPassword(c1.getString(2));
				u.setPhoneNum(c1.getString(3));
				u.setEmailAddr(c1.getString(4));
				u.setCity(c1.getString(5));
				u.setAddress(c1.getString(6));
				u.setCountry(c1.getString(7));
				String fullName = c1.getString(8);
				String[] tokens = fullName.split(" ");
				u.setFirstName(tokens[0]);
				u.setLastName(tokens[1]);
			}
			db1.close();
			return u;
			
		}

		public ArrayList<Comments> getComments(int chatroom_id)
		{
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT * FROM tbl_comment where chatroom_id="+chatroom_id;
			Cursor c = db.rawQuery(query, null);
			ArrayList<Comments> commentlist = new ArrayList<Comments>();
			if (c.moveToFirst()){
				do {
					Comments ccomment = new Comments();
					ccomment.setCommId(c.getInt(0));
					ccomment.setChatRoomID(c.getInt(1));
					ccomment.setUserID(c.getInt(2));
					ccomment.setComment(c.getString(3));
					commentlist.add(ccomment);
				 } while (c.moveToNext());
			}
			db.close();
			return commentlist;
			
		}

		public String getUserName(int UserID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT user_name FROM tbl_user WHERE _id="+UserID;
			Cursor c = db.rawQuery(query, null);
			String Name = c.moveToFirst()?c.getString(0):"-1";
			db.close();
			return Name;
		}

		public boolean addComment(int chatroom_id , String Comment)
		{
			int userID = this.getLoginUserID();
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT max(_id) FROM tbl_comment";
			Cursor cursor = db.rawQuery(query, null);
			int c_id = cursor.moveToFirst()?cursor.getInt(0):-1;
			if (c_id != -1){
				ContentValues values = new ContentValues();
				values.put("_id", c_id+1);
				values.put("chatroom_id", chatroom_id);
				values.put("user_id",  userID);
				values.put("comment", Comment);
				db.insert("tbl_comment",null,values);		
				db.close();
				return true;
			}else{
				return false;
			}
		}
		
		public User getPassengerDetails(int passengerID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT user_id FROM tbl_passenger WHERE _id="+passengerID;
			Cursor c = db.rawQuery(query, null);
			int userID = c.moveToFirst()?c.getInt(0):-1;
			query = "SELECT * FROM tbl_user WHERE _id="+userID;
			Cursor c1 = db.rawQuery(query, null);
			User u = new User();
			if (c1.moveToFirst()){
				u.setuID(c1.getInt(0));
				u.setUserName(c1.getString(1));
				u.setPassword(c1.getString(2));
				u.setPhoneNum(c1.getString(3));
				u.setEmailAddr(c1.getString(4));
				u.setCity(c1.getString(5));
				u.setAddress(c1.getString(6));
				u.setCountry(c1.getString(7));
				String fullName = c1.getString(8);
				String[] tokens = fullName.split(" ");
				u.setFirstName(tokens[0]);
				u.setLastName(tokens[1]);
			}
			db.close();
			return u;
		}

		public CarPool getCarPoolObjectFromPassengerID(int passengerID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT carpool_id FROM tbl_passenger_request WHERE passenger_id="+passengerID;
			Cursor c = db.rawQuery(query, null);
			int cID = c.moveToFirst()?c.getInt(0):-1;
			db.close();
			if (cID != -1)
			{
				SQLiteDatabase db1 = this.getWritableDatabase();
				query = "SELECT * FROM tbl_carpool WHERE _id="+cID;
				Cursor cursor = db1.rawQuery(query, null);
				CarPool carpool = new CarPool();
				if (cursor.moveToFirst()){
					carpool.setcID(cursor.getInt(0));
					carpool.setPosterId(cursor.getInt(1));
					carpool.setRouteId(cursor.getInt(2));
					carpool.setChatRoomId(cursor.getInt(3));
					carpool.setCity(cursor.getString(4));
					carpool.setDescription(cursor.getString(5));
				}
				db1.close();
				return carpool;
			}else{
				Log.i("Carpool_Id Error","Cannot get it");
				return null;
			}
			
		}
		
		public boolean addAcceptedRequest(int passengerID, int cID){
			boolean result = this.isPassengerAlreadyAccepted(cID, passengerID);
			if(result == false)
				{
				SQLiteDatabase db = this.getWritableDatabase();
				String query = "SELECT max(_id) FROM tbl_accepted_request";
				Cursor cursor = db.rawQuery(query, null);
				int _id = cursor.moveToFirst()?cursor.getInt(0):-1;
				if (_id != -1){
					ContentValues values = new ContentValues();
					values.put("_id", _id+1);
					values.put("poster_id", passengerID); //assumption due to error in DB in tbl_accepted_request->PosterID == PassengerID
					values.put("carpool_id",cID);
					db.insert("tbl_accepted_request",null,values);
					db.close();
					return true;
				}
				return false;
			}
			else
			{
				return false;
			}
		}
		
		public boolean deleteRequest(int cID, int pID){
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete("tbl_passenger_request", "carpool_id="+cID+" AND passenger_id="+pID, null);
			db.close();
			return true;
		}
		
		public ArrayList<User> viewAllAcceptedCarpoolMembers(int cID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT poster_id FROM tbl_accepted_request WHERE carpool_id="+cID;
			Cursor c = db.rawQuery(query, null);
			ArrayList<Integer>passengerID = new ArrayList<Integer>();
			if (c.moveToFirst()){
				do{
					passengerID.add(c.getInt(0));
				}while(c.moveToNext());
			}
			db.close();
			ArrayList<User> acceptedUsers = new ArrayList<User>();
			for (int i=0; i<passengerID.size(); i++){
				acceptedUsers.add(this.getUserByPassengerID(passengerID.get(i)));
			}
			db.close();
			return acceptedUsers;
		}
		
		public int getPassengerId(int userID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT _id FROM tbl_passenger WHERE user_id="+userID;
			Cursor c = db.rawQuery(query, null);
			int pID =  c.moveToFirst()?c.getInt(0):-1;
			db.close();
			return pID;
		}
		
		public boolean deleteAlreadyAcceptedMember(int userID , int cID){
			int pID = this.getPassengerId(userID);
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete("tbl_accepted_request", "poster_id="+pID+" AND carpool_id="+cID, null);
			db.close();
			return true;
		}

		public CarPool getCarPoolFromCarPoolID(int cID){
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT * FROM tbl_carpool WHERE _id="+cID;
			Cursor c = db.rawQuery(query, null);
			CarPool carpool = new CarPool();
			if (c.moveToFirst()){
				carpool.setcID(c.getInt(0));
				carpool.setPosterId(c.getInt(1));
				carpool.setRouteId(c.getInt(2));
				carpool.setChatRoomId(c.getInt(3));
				carpool.setCity(c.getString(4));
				carpool.setDescription(c.getString(5));
			}
			db.close();
			return carpool;
		}
		
		public ArrayList<CarPool> getSubscribedCarPool(){
			int currLoginUserID = this.getLoginUserID();
			int passengerID = this.getPassengerId(currLoginUserID);
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT carpool_id FROM tbl_accepted_request WHERE poster_id="+passengerID;
			Cursor c = db.rawQuery(query, null);
			ArrayList<Integer> carpoolsID = new ArrayList<Integer>();
			if(c.moveToFirst()){
				do{
					carpoolsID.add(c.getInt(0));
				}while(c.moveToNext());
			}
			db.close();
			ArrayList<CarPool> carpools = new ArrayList<CarPool>();
			for(int i=0; i<carpoolsID.size(); i++){
				carpools.add(this.getCarPoolFromCarPoolID(carpoolsID.get(i)));
			}
			return carpools;
		}

		public boolean deleteCarpool(CarPool carpool){
			SQLiteDatabase db = this.getWritableDatabase();
			int routeId = carpool.getRouteId();
			int chatRoomId = carpool.getChatRoomId();
			int cID = carpool.getcID();
			db.delete("tbl_route", "_id="+routeId, null);
			db.delete("tbl_car", "carpool_id="+cID, null);
			db.delete("tbl_comment", "chatroom_id="+chatRoomId, null);
			db.delete("tbl_chatroom", "_id="+chatRoomId, null);
			db.delete("tbl_passenger_request", "carpool_id="+cID, null);
			db.delete("tbl_accepted_request", "carpool_id="+cID, null);
			db.delete("tbl_carpool", "_id="+cID, null);
			db.close();
			return true;
		}

		public boolean deleteCarpoolMember(int cID){
			int userID = this.getLoginUserID();
			int pID = this.getPassengerId(userID);
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete("tbl_accepted_request", "poster_id="+pID + " AND carpool_id="+cID, null);
			db.close();
			return true;
		}

		public String getOldPassword(){
			int userID = this.getLoginUserID();
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT user_password FROM tbl_user WHERE _id="+userID;
			Cursor c = db.rawQuery(query, null);
			String password = c.moveToFirst()?c.getString(0):"-1";
			db.close();
			return password;
		}
		
		public boolean updatePassword(String password){
			
			int userID = this.getLoginUserID();
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("user_password", password);
			db.update("tbl_user", values, "_id="+userID, null);
			db.close();
			return true;
		}
		
		public boolean isSessionExist(){
			SQLiteDatabase db = this.getReadableDatabase();
			String query = "SELECT * FROM tbl_login";
			Cursor c = db.rawQuery(query, null);
			int noOfRows = c.getCount();
			db.close();
			if (noOfRows > 0)
				return true;
			else
				return false;
		}

		public boolean isPassengerAlreadyAccepted(int cID, int PassengerID)
		{
			SQLiteDatabase db = this.getReadableDatabase();
			String query = "SELECT * FROM tbl_accepted_request WHERE poster_id="+PassengerID+"  AND carpool_id="+cID;
			Cursor c = db.rawQuery(query, null);
			int noOfRows = c.getCount();
			db.close();
			if(noOfRows > 0)
			{ 
				return true;
			}
			else
			{
				return false;
			}
			
		}
		
		public boolean isPassengerAlreadyRequested(int cID, int PassengerID)
		{
			SQLiteDatabase db = this.getReadableDatabase();
			String query = "SELECT * FROM tbl_passenger_request WHERE passenger_id="+PassengerID+"  AND carpool_id="+cID;
			Cursor c = db.rawQuery(query, null);
			int noOfRows = c.getCount();
			db.close();
			if(noOfRows > 0)
			{ 
				return true;
			}
			else
			{
				return false;
			}
			
		}

		public boolean updateNoOfSeats(CarPool cID , int seats)
		{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("available_seats", seats);
			db.update("tbl_car", values, "carpool_id="+cID.getcID(), null);
			db.close();
			return true;
		}
		
		public int getNoOfSeats(CarPool cID)
		{
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "SELECT available_seats FROM tbl_car WHERE carpool_id="+cID.getcID();
			Cursor c = db.rawQuery(query, null);
			int seats = c.moveToFirst()?c.getInt(0):-1;
			db.close();
			return seats;
		}

}
