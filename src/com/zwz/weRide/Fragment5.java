package com.zwz.weRide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.zwz.DataBaseHandler.DatabaseHandler;

public class Fragment5 extends SherlockFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
		db.offlineUser();
		Toast.makeText(getActivity(), "You have been logout successfully", Toast.LENGTH_LONG).show();
		getActivity().finish();
		return null;
	}
}