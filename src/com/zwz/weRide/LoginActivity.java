package com.zwz.weRide;

import com.google.android.gms.maps.model.LatLng;
import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	Button login;
	EditText userName,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
		login = (Button)findViewById(R.id.userloginbutton);
		userName = (EditText)findViewById(R.id.editText1);
		password = (EditText)findViewById(R.id.editText2);
		login.setOnClickListener(new View.OnClickListener() {
			
  			@Override
  			public void onClick(View v)
  			{
  				String username = userName.getText().toString();
  				String pass = password.getText().toString();
  				boolean status = Controller.getController().LoginActivity(username, pass, getApplicationContext());
  				if (status){
  					
	  				Intent i = new Intent(LoginActivity.this,UserMainActivity.class);
	  				Toast.makeText(getApplicationContext(),"Welcome " + username, Toast.LENGTH_LONG).show();
	  				startActivity(i);
	  				finish();
  				}else{
  					printDialogue("Error", "Invalid Username or Password");
  				}
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