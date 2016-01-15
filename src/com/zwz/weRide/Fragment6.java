package com.zwz.weRide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;

public class Fragment6 extends SherlockFragment {
	Button ChangePass;
	EditText oldPass, newPass, cnfrmPass;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment6, container, false);
		oldPass = (EditText)rootView.findViewById(R.id.editText3);
		newPass = (EditText)rootView.findViewById(R.id.editText4);
		cnfrmPass = (EditText)rootView.findViewById(R.id.EditText01);
		ChangePass = (Button )rootView.findViewById(R.id.button1);
		ChangePass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password = Controller.getController().Fragment6GetPassword(getActivity());
				if (password.equals(oldPass.getText().toString())){
					String pass = newPass.getText().toString();
					String cnfirmPassword = cnfrmPass.getText().toString();
					if (pass.equals(cnfirmPassword)){
						boolean status = Controller.getController().Fragment6SetPassword(pass, getActivity());
						if (status){
							printDialogue("Congratulations", "Your password has been updated successfully\nPlease logout and login with a new password");
							
						}
					}else{
						printDialogue("Error", "Password must be same in both fields");
					}
				}else{
					printDialogue("Error", "Invalid old password");
				}
			}
		});
		
		return rootView;
	}
	
	public void printDialogue(String heading, String body){
		new AlertDialog.Builder(getActivity())
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