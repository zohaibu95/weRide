
package com.zwz.weRide;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;
import com.zwz.models.CarPool;
import com.zwz.models.Route;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Fragment7 extends SherlockFragment {
    String[] time_departure;
    String[] startPoint;
    String[] destinationPoint;
    int[] route_ids;
    ListView list;
    ListViewAdapter adapter;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment7, container,
                false);
 
        ArrayList<Route> route = Controller.getController().Fragment7(getActivity().getApplicationContext());
        time_departure = new String[route.size()];
        startPoint = new String[route.size()];
        destinationPoint = new String[route.size()];
        route_ids = new int[route.size()];
        for(int i=0; i<route.size(); i++){
        	time_departure[i] = route.get(i).getLeavingTime();
        	startPoint[i] = route.get(i).getDestinationLoc();
        	destinationPoint[i] = route.get(i).getArrivalLoc();
        	route_ids[i] = route.get(i).getRouteID();
        }
       
        list = (ListView) rootView.findViewById(R.id.listviewsubscribedpost);
        adapter = new ListViewAdapter(getActivity(), time_departure, startPoint, destinationPoint);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Intent i = new Intent(getActivity(), SingleItemViewSuscribedPost.class);
                i.putExtra("route_ids", route_ids);
                i.putExtra("time_departure", time_departure);
                i.putExtra("startPoint", startPoint);
                i.putExtra("destinationPoint", destinationPoint);
                i.putExtra("position", position);
                startActivity(i);
            }
 
        });
        return rootView;
    }
 
}
