package com.zwz.weRide;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;
import com.zwz.models.PassengerRequest;
import com.zwz.models.Route;

public class Fragment3 extends SherlockFragment {
	ListView list;
    ListViewAdapterNotification adapter;
    String[] Notification;
   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment3, container, false);
		ArrayList<PassengerRequest> plist = Controller.getController().Fragment3(getActivity().getApplicationContext());
	        Notification = new String[plist.size()];
	        int[] pID = new int[plist.size()];
	        int i=0;
	        for(PassengerRequest p: plist){
	        	int cID = p.getCarpoolID();
	        	pID[i] = p.getPassengerID();
	        	Log.i("carpool id", String.valueOf(cID));
	        	Route r = Controller.getController().Fragment3Route(cID, getActivity().getApplicationContext());
	        	Notification[i] = "Request for dest.(" + r.getArrivalLoc() +")";
	        	
	        	i++;
	        }
	        final int[] pid = pID;
	        
	        list = (ListView) rootView.findViewById(R.id.listviewnotifications);
	        adapter = new ListViewAdapterNotification(getActivity().getApplicationContext(),Notification);
	        list.setAdapter(adapter);
	        list.setOnItemClickListener(new OnItemClickListener() {
	        		
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                Intent i = new Intent(getActivity(), SingleItemViewRequestNotification.class);
	                i.putExtra("passengerID", pid);
	                i.putExtra("position", position);
	                startActivity(i);
	            
	            }
	 
	        });
		return rootView;
	}
	
}
