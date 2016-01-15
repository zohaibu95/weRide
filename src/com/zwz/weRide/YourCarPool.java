package com.zwz.weRide;

import java.util.ArrayList;

import com.zwz.controller.Controller;
import com.zwz.models.Route;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class YourCarPool extends Activity {
	String[] time_departure;
    String[] startPoint;
    String[] destinationPoint;
    int[] route_ids;
    ListView list;
    ListViewAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yourcarpool);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
		ArrayList<Route>route = Controller.getController().FragmentTab2(getApplicationContext());
	  	   //ArrayList<User> userDetails = new ArrayList<User>();
	  		time_departure = new String[route.size()];
	  		startPoint = new String[route.size()];
	  		destinationPoint = new String[route.size()];
	  		route_ids = new int[route.size()];
	  		
	  		for (int i=0; i<time_departure.length; i++){
	  			time_departure[i] = route.get(i).getLeavingDate() + "   " + route.get(i).getLeavingTime() ;
	  		}
	  		
	  		for (int j=0; j<startPoint.length; j++){
	  			startPoint[j] = route.get(j).getDestinationLoc();
	  		}
	  		
	  		for(int l=0; l<destinationPoint.length; l++){
	  			destinationPoint[l] = route.get(l).getArrivalLoc();
	  		}
	  		
	  		for(int l=0; l<route_ids.length; l++){
	  			route_ids[l] = route.get(l).getRouteID();
	  		}
	       
	        // Locate the ListView in fragmenttab1.xml
	        list = (ListView)findViewById(R.id.listViewyourcarpool);
	 
	        // Pass results to ListViewAdapter Class
	        adapter = new ListViewAdapter(getApplicationContext(), time_departure, startPoint, destinationPoint);
	               // flag);
	        // Binds the Adapter to the ListView
	        adapter.notifyDataSetChanged();
	        list.setAdapter(adapter);
	        list.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                Intent i = new Intent(getApplicationContext(), SingleItemViewPublishedPost.class);
	                i.putExtra("route_ids", route_ids);
	                i.putExtra("time_departure", time_departure);
	                i.putExtra("startPoint", startPoint);
	                i.putExtra("destinationPoint", destinationPoint);
	                i.putExtra("position", position);
	                startActivity(i);
	            }
	 
	        });
	}

}
