package com.zwz.weRide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	Button next;
	EditText firstName,lastName,userName,password,confirmPassword;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
		firstName = (EditText)findViewById(R.id.editText1);
		lastName = (EditText)findViewById(R.id.EditText01);
		userName = (EditText)findViewById(R.id.editText2);
		password = (EditText)findViewById(R.id.editText3);
		confirmPassword = (EditText)findViewById(R.id.editText4);
		next = (Button)findViewById(R.id.nextbutton);
		next.setOnClickListener(new View.OnClickListener() {
			
  			@Override
  			public void onClick(View v)
  			{
  				
  				if (firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() 
  						|| userName.getText().toString().isEmpty()  || password.getText().toString().isEmpty() 
  						|| confirmPassword.getText().toString().isEmpty() ){
  					printDialogue("Error", "Please fill all the fields");
  				}else if (!password.getText().toString().equals(confirmPassword.getText().toString())){
  					printDialogue("Error", "Password fields does not match");
  				}else{
  					Intent i = new Intent(RegisterActivity.this,RegisterActivity2.class);
  					i.putExtra("firstname", firstName.getText().toString());
  					i.putExtra("lastname", lastName.getText().toString());
  					i.putExtra("username", userName.getText().toString());
  					i.putExtra("password", password.getText().toString());
  					startActivity(i);
  					finish();
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
