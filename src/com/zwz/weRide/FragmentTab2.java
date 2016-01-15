package com.zwz.weRide;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;
import com.zwz.models.Route;

public class FragmentTab2 extends SherlockFragment {
    String[] time_departure;
    String[] startPoint;
    String[] destinationPoint;
    int[] route_ids;
    ListView list;
    ListViewAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab2.xml
		View view = inflater.inflate(R.layout.fragmenttab2, container, false);
		ArrayList<Route>route = Controller.getController().FragmentTab2(getActivity().getApplicationContext());
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
        list = (ListView) view.findViewById(R.id.listViewyourcarpool);
 
        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(getActivity(), time_departure, startPoint, destinationPoint);
               // flag);
        // Binds the Adapter to the ListView
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Intent i = new Intent(getActivity(), SingleItemViewPublishedPost.class);
                i.putExtra("route_ids", route_ids);
                i.putExtra("time_departure", time_departure);
                i.putExtra("startPoint", startPoint);
                i.putExtra("destinationPoint", destinationPoint);
                i.putExtra("position", position);
                startActivity(i);
            }
 
        });
		return view;
	}
}
