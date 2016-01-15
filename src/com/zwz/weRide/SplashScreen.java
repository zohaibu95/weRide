package com.zwz.weRide;

import java.io.IOException;

import com.zwz.DataBaseHandler.DatabaseHandler;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends Activity {
	final String PREFS_NAME = "MyPrefsFile";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		if (settings.getBoolean("my_first_time", true)) {
		    Log.d("Comments", "First time");
		    DatabaseHandler db1 = new DatabaseHandler(getApplicationContext());
			try {
				db1.createDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db1.close();
		    settings.edit().putBoolean("my_first_time", false).commit(); 
		}
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		boolean isSession = db.isSessionExist();
		if (isSession){
			Intent i = new Intent(this,UserMainActivity.class);
			startActivity(i);
			finish();
		}else{
			setContentView(R.layout.splash);
			new Handler().postDelayed(new Runnable(){
	
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent i = new Intent(SplashScreen.this,MainActivity.class);
					startActivity(i);
					finish();
				}
				
			}, 2000);
		}
	}

}
