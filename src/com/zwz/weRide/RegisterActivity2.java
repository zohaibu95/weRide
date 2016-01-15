package com.zwz.weRide;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zwz.controller.Controller;
import com.zwz.models.User;

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
import android.widget.Toast;

public class RegisterActivity2 extends Activity {
	private Button register,goBack;
	private EditText phoneNum,emailAddr,address,city,country;
	private String firstName, lastName, userName, password, phoneNumStr, emailAdr, addressStr, cityStr,countryStr;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration1);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
			phoneNum = (EditText)findViewById(R.id.EditText03);
			emailAddr = (EditText)findViewById(R.id.EditText02);
			address = (EditText)findViewById(R.id.editText1);
			city = (EditText)findViewById(R.id.EditText01);
			country = (EditText)findViewById(R.id.editText2);
			register = (Button)findViewById(R.id.button1);
			goBack = (Button)findViewById(R.id.goBack);
			firstName = getIntent().getStringExtra("firstname");
			lastName = getIntent().getStringExtra("lastname");
			userName = getIntent().getStringExtra("username");
			password = getIntent().getStringExtra("password");
			register.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (phoneNum.getText().toString().isEmpty() || emailAddr.getText().toString().isEmpty() 
	  						|| address.getText().toString().isEmpty()  ||city.getText().toString().isEmpty() 
	  						|| country.getText().toString().isEmpty()){
	  					printDialogue("Error", "Please fill all the fields");
	  				}else if(!isEmailValid(emailAddr.getText().toString())){
	  					printDialogue("Error", "Invalid Email Address");
	  				}
					else{
	  					//User user = new User();
						Controller.getController().setUser(firstName, lastName, emailAddr.getText().toString(), userName, password, phoneNum.getText().toString(), address.getText().toString(), city.getText().toString(), country.getText().toString());
	  					//user.setDetails(firstName, lastName, emailAddr.getText().toString(), userName, password, phoneNum.getText().toString(), address.getText().toString(), city.getText().toString(), country.getText().toString());
	  					boolean status = Controller.getController().RegisterUser(getApplicationContext());
	  					if (status){
	  						Toast.makeText(getApplicationContext(), "Congratulations! you have been registered successfully", Toast.LENGTH_SHORT).show();
	  						Toast.makeText(getApplicationContext(), "Welcome " + firstName + " " + lastName, Toast.LENGTH_LONG).show();
	  						Intent i = new Intent(RegisterActivity2.this,UserMainActivity.class);
	  						startActivity(i);
	  						finish();
	  					}else{
	  						printDialogue("Error", "UserName/Email already exists");
	  					}
	  				}
					
				}
			});
			
			goBack.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent i = new Intent(RegisterActivity2.this,RegisterActivity.class);
					startActivity(i);
					finish();
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
		

		 /*private boolean checkEmail(String email) {
		     email
			 return 
		    }*/
		
		public boolean isEmailValid(String email)
	    {
	         String regExpn =
	             "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
	                 +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
	                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
	                   +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
	                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
	                   +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

	     CharSequence inputStr = email;

	     Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
	     Matcher matcher = pattern.matcher(inputStr);

	     if(matcher.matches())
	        return true;
	     else
	        return false;
	}
		/*private boolean checkEmail(String email){
		   return (email.contains("@") && (email.lastIndexOf(".")) != -1)? true : false;
		}*/
}
