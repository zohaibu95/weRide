package com.zwz.weRide;

import java.util.ArrayList;

//import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;
import com.zwz.models.CarPool;
import com.zwz.models.User;

import android.app.Activity;
import android.app.AlertDialog;
//import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class SingleItemViewPublishedPost extends Activity {
    // Declare Variables
	 TextView txtTimeDeparture;
	    TextView txtStartPoint;
	    TextView txtDestinationPoint;
	    TextView txtPosterName;
	    TextView txtPosterPhone;
	    TextView txtDesciption;
	    String[] time_departure;
	    String[] startPoint;
	    String[] destinationPoint;
	    int[] route_ids;
   // int[] flag;
    int position;
    Button comment;
    Button members,deleteCarPool;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemviewpublishedpost);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
        Intent i = getIntent();
        position = i.getExtras().getInt("position");
        
        time_departure = i.getStringArrayExtra("time_departure");
        
        startPoint = i.getStringArrayExtra("startPoint");
    
        destinationPoint = i.getStringArrayExtra("destinationPoint");
        
        route_ids = i.getIntArrayExtra("route_ids");
     
        ArrayList<Object> objList = Controller.getController().SingleItemViewPost(route_ids[position], getApplicationContext());
        Object obj1= objList.get(0);
        Object obj2= objList.get(1);
        User user = (User)obj1;
        final CarPool carpool =(CarPool)obj2; 
        txtTimeDeparture = (TextView) findViewById(R.id.rank);
        txtStartPoint = (TextView) findViewById(R.id.country);
        txtDestinationPoint = (TextView) findViewById(R.id.population);
        txtPosterName  =  (TextView) findViewById(R.id.postername);
        txtPosterPhone  =  (TextView) findViewById(R.id.posterphone);
        txtDesciption  =  (TextView) findViewById(R.id.description);
        comment = (Button)findViewById(R.id.publishedpostcommentbutton);
        members = (Button)findViewById(R.id.carpoolmemberbutton);
        deleteCarPool = (Button)findViewById(R.id.Button03);
        txtTimeDeparture.setText(time_departure[position]);
        txtStartPoint.setText(startPoint[position]);
        txtDestinationPoint.setText(destinationPoint[position]);
        txtPosterName.setText(user.getFirstName() + "  " + user.getLastName());
        txtPosterPhone.setText(user.getPhoneNum());
        txtDesciption.setText(carpool.getDescription());
        comment.setOnClickListener(new View.OnClickListener() {
			
     			@Override
     			public void onClick(View v)
     			{
     				Intent i = new Intent(SingleItemViewPublishedPost.this,PostComments.class);
     				i.putExtra("chatroom_id", String.valueOf(carpool.getChatRoomId()));
     				Log.i("ChatRoomId", String.valueOf(carpool.getChatRoomId()));
     				startActivity(i);
     			}
     		
     		});
        members.setOnClickListener(new View.OnClickListener() {
			
 			@Override
 			public void onClick(View v)
 			{
 				Intent i = new Intent(SingleItemViewPublishedPost.this,CarpoolMemberList.class);
 				i.putExtra("carpool_id", carpool.getcID());
 				startActivity(i);
 			}
 		
 		});
        
        deleteCarPool.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean status = Controller.getController().deleteCarpool(carpool, getApplicationContext());
				if (status){
					printDialogue("Congratulations", "Your carpool has been deleted successfully");
					
				}else{
					printDialogue("Error", "Can't Delete Your Carpool");
				}
			/*	android.app.Fragment frg = null;
				frg = getFragmentManager().findFragmentByTag("FragmentTab2");
				final FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.detach(frg);
				ft.attach(frg);
				ft.commit();*/
				finish();
			}
		});
    }
    public void printDialogue(String heading, String body){
		new AlertDialog.Builder(this)
		    .setTitle(heading)
		    .setMessage(body)
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
	 }
}