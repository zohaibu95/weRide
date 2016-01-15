
package com.zwz.weRide;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockFragment;
import com.zwz.controller.Controller;
import com.zwz.models.Route;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Fragment1 extends SherlockFragment {
    String[] time_departure;
    String[] startPoint;
    String[] destinationPoint;
    int[] route_ids;
    ListView list;
    ListViewAdapter adapter;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container,
                false);
        ArrayList<Route>route = Controller.getController().Fragment1(getActivity().getApplicationContext());
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
  
        list = (ListView) rootView.findViewById(R.id.listview);

        adapter = new ListViewAdapter(getActivity(), time_departure, startPoint, destinationPoint);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        
        list.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
               
                Intent i = new Intent(getActivity(), SingleItemView.class);
                i.putExtra("route_ids", route_ids);
                i.putExtra("time_departure", time_departure);
                i.putExtra("startPoint", startPoint);
                i.putExtra("destinationPoint", destinationPoint);
                i.putExtra("position", position);
               Log.i("Postion:", String.valueOf(position));
                startActivity(i);
            }
 
        });
      
     
        return rootView;
    }
 
}
