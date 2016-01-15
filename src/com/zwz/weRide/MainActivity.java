package com.zwz.weRide;

import java.io.IOException;
import java.util.ArrayList;

import com.zwz.models.CarPool;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button login;
	Button register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_screen);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
		login = (Button)findViewById(R.id.loginbutton);
		register = (Button)findViewById(R.id.registerbutton);
		
		
		
		 login.setOnClickListener(new View.OnClickListener() {
  			@Override
  			public void onClick(View v)
  			{
  				Intent i = new Intent(MainActivity.this,LoginActivity.class);
  				startActivity(i);
  				//finish();
  			}
  		
  		});
		 
		 register.setOnClickListener(new View.OnClickListener() {
				
	  			@Override
	  			public void onClick(View v)
	  			{
	  				Intent i = new Intent(MainActivity.this,RegisterActivity.class);
	  				startActivity(i);
	  				//finish();
	  			}
	  		
	  		});
		 
	}

}
