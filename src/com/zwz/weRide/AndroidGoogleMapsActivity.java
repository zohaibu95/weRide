package com.zwz.weRide;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zwz.weRide.R;
import com.zwz.weRide.LatLngInterpolator.LinearFixed;

public class AndroidGoogleMapsActivity extends FragmentActivity implements OnMarkerClickListener {

	// Google Map
	private GoogleMap googleMap;
	private GPSTracker gps;
	private String selectedLocationAddress="";
	Marker marker;
	Button btn;
	String title = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
		btn = (Button)findViewById(R.id.button2);
		title = getIntent().getStringExtra("title");
		try {
			// Loading map
			initilizeMap();
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.getUiSettings().setRotateGesturesEnabled(false);
			googleMap.getUiSettings().setTiltGesturesEnabled(true);
			googleMap.getUiSettings().setZoomControlsEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			centerMapOnMyLocation();
			//gps = new GPSTracker(getApplicationContext());
			//Location loc = gps.getLocation();
			googleMap.setOnMapClickListener(new OnMapClickListener() {

		        @Override
		        public void onMapClick(LatLng latlng) {
		            // TODO Auto-generated method stub
		        	
		            if (marker != null) {
		                marker.remove();
		            }
		            
		            marker = googleMap.addMarker(new MarkerOptions().title(title)
		                    .position(latlng)
		                    .icon(BitmapDescriptorFactory
		                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		           selectedLocationAddress = getCompleteAddressString(latlng.latitude, latlng.longitude);
		           Log.i("Address",selectedLocationAddress);
		       }
		   });
			
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(selectedLocationAddress.length() != 0){
						Intent returnIntent = new Intent();
						returnIntent.putExtra("result",selectedLocationAddress);
						setResult(Activity.RESULT_OK,returnIntent);
						finish();
					}else{
						printDialogue("Error", "Please select the locations");
					}
					
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// initilizeMap();
	}

	private void initilizeMap() {

		googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment)).getMap();

		// check if map is created successfully or not
		if (googleMap == null) {
			Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
		}

		(findViewById(R.id.mapFragment)).getViewTreeObserver().addOnGlobalLayoutListener(
				new android.view.ViewTreeObserver.OnGlobalLayoutListener() {

					@SuppressLint("NewApi") @Override
					public void onGlobalLayout() {
						// gets called after layout has been done but before
						// display
						// so we can get the height then hide the view
						if (android.os.Build.VERSION.SDK_INT >= 16) {
							(findViewById(R.id.mapFragment)).getViewTreeObserver().removeOnGlobalLayoutListener(this);
						} else {
							(findViewById(R.id.mapFragment)).getViewTreeObserver().removeGlobalOnLayoutListener(this);
						}
						//setCustomMarkerOnePosition();
						//setCustomMarkerTwoPosition();
						// plotMarkers(markerList);
					}
				});
	}



	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	 private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
         String strAdd = "";
         Geocoder geocoder = new Geocoder(this, Locale.getDefault());
         try {
             List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
             if (addresses != null) {
                 Address returnedAddress = addresses.get(0);
                 StringBuilder strReturnedAddress = new StringBuilder("");

                 for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                     strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ");
                 }
                 strAdd = strReturnedAddress.toString();
                 Log.w("My Current loction address", "" + strReturnedAddress.toString());
             } else {
                 Log.w("My Current loction address", "No Address returned!");
             }
         } catch (Exception e) {
             e.printStackTrace();
             Log.w("My Current loction address", "Canont get Address!");
         }
         return strAdd;
     }
	
	 public void printDialogue(String heading, String body){
			new AlertDialog.Builder(AndroidGoogleMapsActivity.this)
			    .setTitle(heading)
			    .setMessage(body)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
		 }

		private void centerMapOnMyLocation() {

		    googleMap.setMyLocationEnabled(true);

		  gps = new GPSTracker(getApplicationContext());
			Location loc = gps.getLocation();
		    LatLng myLocation = null;
		    if (loc != null) {
		        myLocation = new LatLng(loc.getLatitude(),
		                loc.getLongitude());
		    }
		    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,16f));
		}
}
