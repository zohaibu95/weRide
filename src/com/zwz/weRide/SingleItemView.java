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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class SingleItemView extends Activity {
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
    int position;
   Button joinBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
        Intent i = getIntent();
       
        position = i.getExtras().getInt("position");
        Log.i("Postion", String.valueOf(position));
        time_departure = i.getStringArrayExtra("time_departure");
        
        startPoint = i.getStringArrayExtra("startPoint");
    
        destinationPoint = i.getStringArrayExtra("destinationPoint");
        
        route_ids = i.getIntArrayExtra("route_ids");
     
       /* DatabaseHandler db = new DatabaseHandler(getApplicationContext());
       final  CarPool carpool = db.getCarPoolDetails(route_ids[position]);
        User user = db.getPosterDetails(carpool.getPosterId());
        db.close();*/
        
        ArrayList<Object> objList = Controller.getController().SingleItemViewPost(route_ids[position], getApplicationContext());
        User user = (User)objList.get(0);
        final CarPool carpool =(CarPool)objList.get(1);
        
        txtTimeDeparture = (TextView) findViewById(R.id.rank);
        txtStartPoint = (TextView) findViewById(R.id.country);
        txtDestinationPoint = (TextView) findViewById(R.id.population);
        txtPosterName  =  (TextView) findViewById(R.id.postername);
        txtPosterPhone  =  (TextView) findViewById(R.id.posterphone);
        txtDesciption  =  (TextView) findViewById(R.id.description);
        joinBtn = (Button)findViewById(R.id.requestcarpool);
        txtTimeDeparture.setText(time_departure[position]);
        txtStartPoint.setText(startPoint[position]);
        txtDestinationPoint.setText(destinationPoint[position]);
        txtPosterName.setText(user.getFirstName() + "  " + user.getLastName());
        txtPosterPhone.setText(user.getPhoneNum());
        txtDesciption.setText(carpool.getDescription());
        joinBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = Controller.getController().joinCarpool(carpool, getApplicationContext());
				printDialogue("Alert", str);
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