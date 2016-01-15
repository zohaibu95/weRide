package com.zwz.weRide;

import java.util.ArrayList;

import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;
import com.zwz.models.CarPool;
import com.zwz.models.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class SingleItemViewRequestNotification extends Activity {
    // Declare Variables
    TextView txtnotification;
    TextView txtcity;
    TextView txtphone;
    int position;
    int[] passengerID;
    Button acceptRequest, rejectRequest;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemviewrequestnotification);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
        Intent i = getIntent();
        position = i.getExtras().getInt("position");
        passengerID = i.getExtras().getIntArray("passengerID");
        // Locate the TextViews in singleitemview.xml.getInt("passengerID");
        
        User user = Controller.getController().GetPassengerDetails(passengerID[position], getApplicationContext());
        txtnotification = (TextView) findViewById(R.id.name);
        txtcity = (TextView) findViewById(R.id.city);
        txtphone = (TextView) findViewById(R.id.phone);
        txtnotification.setText(String.valueOf(user.getFirstName() + " " + user.getLastName()));
        txtcity.setText(user.getCity());
        txtphone.setText(user.getPhoneNum());
        
        acceptRequest = (Button)findViewById(R.id.requestcarpool);
        rejectRequest = (Button)findViewById(R.id.Button01);
        
        acceptRequest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				int seats = Controller.getController().GetNoSeats(passengerID[position], getApplicationContext());
				if(seats > 0)
				{
					boolean result = Controller.getController().AcceptRequest(seats,passengerID[position], getApplicationContext());
					if(result == true)
					{
						Toast.makeText(getApplicationContext(), "Request has been accepted", Toast.LENGTH_LONG).show();
						finish();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Request can't be accepted", Toast.LENGTH_LONG).show();
						finish();
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Seats not available", Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
        
        rejectRequest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean result = Controller.getController().RejectRequest(passengerID[position], getApplicationContext());
				if(result == true)
				{
					Toast.makeText(getApplicationContext(), "Request has been Rejected", Toast.LENGTH_LONG).show();
					finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Request can't be Rejected", Toast.LENGTH_LONG).show();
					finish();
				}
			}
		} );
    }
/*    public void printDialogue(String heading, String body){
		new AlertDialog.Builder(this)
		    .setTitle(heading)
		    .setMessage(body)
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	DatabaseHandler db2 = new DatabaseHandler(getApplicationContext());
		        	CarPool carpool = db2.getCarPoolObjectFromPassengerID(passengerID[position]);
		        	boolean result = db2.deleteRequest(carpool.getcID(),passengerID [position]);
					db2.close();
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
	 }*/
}