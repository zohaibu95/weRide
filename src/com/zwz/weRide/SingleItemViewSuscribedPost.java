package com.zwz.weRide;

import java.util.ArrayList;

import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;
import com.zwz.models.CarPool;
import com.zwz.models.User;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class SingleItemViewSuscribedPost extends Activity {
    TextView txtTimeDeparture;
    TextView txtStartPoint;
    TextView txtDestinationPoint;
    TextView txtPosterName;
    TextView txtPosterPhone;
    TextView txtDesciption;
    int[] route_ids;
    String[] time_departure;
    String[] startPoint;
    String[] destinationPoint;
    int position;
    Button comment,leaveCarPool;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemveiwsubscribedpost);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
        Intent i = getIntent();
        position = i.getExtras().getInt("position");
        time_departure = i.getStringArrayExtra("time_departure");
        startPoint = i.getStringArrayExtra("startPoint");
        destinationPoint = i.getStringArrayExtra("destinationPoint");
        
        route_ids = i.getIntArrayExtra("route_ids");
        
      /*  DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        final  CarPool carpool = db.getCarPoolDetails(route_ids[position]);
        User user = db.getPosterDetails(carpool.getPosterId());
        db.close();
        */
        ArrayList<Object> objList = Controller.getController().SingleItemViewPost(route_ids[position], getApplicationContext());
        User user = (User)objList.get(0);
        final CarPool carpool =(CarPool)objList.get(1);
        
        
        comment = (Button)findViewById(R.id.subscribepostcommentbutton);
        leaveCarPool = (Button)findViewById(R.id.Button01);
        txtTimeDeparture = (TextView) findViewById(R.id.rank);
        txtStartPoint = (TextView) findViewById(R.id.country);
        txtDestinationPoint = (TextView) findViewById(R.id.population);
        txtPosterName  =  (TextView) findViewById(R.id.postername);
        txtPosterPhone  =  (TextView) findViewById(R.id.posterphone);
        txtDesciption  =  (TextView) findViewById(R.id.description);
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
				Intent i = new Intent(SingleItemViewSuscribedPost.this,PostComments.class);
 				i.putExtra("chatroom_id", String.valueOf(carpool.getChatRoomId()));
 				Log.i("ChatRoomId", String.valueOf(carpool.getChatRoomId()));
 				startActivity(i);
			}
		
		});
        
        leaveCarPool.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean status = Controller.getController().leaveCarPool(carpool, getApplicationContext());
				if (status){
					Toast.makeText(getApplicationContext(), "You have been removed from the carpool" , Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
        
    }
    
}