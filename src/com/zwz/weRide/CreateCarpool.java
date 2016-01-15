package com.zwz.weRide;

import java.util.Calendar;

import com.zwz.controller.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;

public class CreateCarpool extends Activity {
	EditText deptTime,deptDate,startPoint,destination,availSeats,description;
	Button post;
	RadioGroup radio;
	String carType="Taxi";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createcarpool);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
		//getActionBar().setHomeButtonEnabled(true);
		deptTime = (EditText)findViewById(R.id.EditText03);
		deptDate = (EditText)findViewById(R.id.EditText04);
		startPoint = (EditText)findViewById(R.id.EditText02);
		destination = (EditText)findViewById(R.id.EditText01);
		availSeats = (EditText)findViewById(R.id.EditText05);
		description = (EditText)findViewById(R.id.EditText06);
		post = (Button)findViewById(R.id.button1);
		radio = (RadioGroup)findViewById(R.id.radioGroup1);
		
		startPoint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),AndroidGoogleMapsActivity.class);
				i.putExtra("title", "Starting point");
				startActivityForResult(i, 1);
				
			}
		});
		
		destination.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),AndroidGoogleMapsActivity.class);
				i.putExtra("title", "Destination point");
				startActivityForResult(i, 2);
				
			}
		});
		
		radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
					if(checkedId == R.id.radio0)
						carType = "Taxi";
					else if (checkedId == R.id.radio1)
						carType = "Personal Car";
			}
		});
		deptTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Calendar mcurrentTime = Calendar.getInstance();
		         int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		         int minute = mcurrentTime.get(Calendar.MINUTE);
				 TimePickerDialog mTimePicker;
		         mTimePicker = new TimePickerDialog(CreateCarpool.this, new TimePickerDialog.OnTimeSetListener() {
		        		@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// TODO Auto-generated method stub
							deptTime.setText(hourOfDay + ":" + minute);
						}
		            }, hour, minute, true);
		            mTimePicker.setTitle("Select time");
		            mTimePicker.show();
		            
			}
		});
		
		deptDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  	Calendar mcurrentDate=Calendar.getInstance();
		           int  mYear=mcurrentDate.get(Calendar.YEAR);
		           int  mMonth=mcurrentDate.get(Calendar.MONTH);
		           int  mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
		            DatePickerDialog mDatePicker=new DatePickerDialog(CreateCarpool.this, new OnDateSetListener() {                  
		                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
		                	String date = selectedday + "/" + selectedmonth + "/" + selectedyear;
		                	deptDate.setText(date);
		                }
		            },mYear, mMonth, mDay);
		            mDatePicker.setTitle("Select date");                
		            mDatePicker.show();  
			}
		});
		
		post.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (deptTime.getText().toString().isEmpty() || deptDate.getText().toString().isEmpty() 
  						|| startPoint.getText().toString().isEmpty()  || destination.getText().toString().isEmpty() 
  						|| availSeats.getText().toString().isEmpty() || description.getText().toString().isEmpty()){
  					printDialogue("Error", "Please fill all the fields");
  				}else{
  					//cont.createRoute(s, d);
  					//cont.createCar(....);
  					//status = cont.addCarPool();
  					//Route r = new Route();
  					//r.setRoute( startPoint.getText().toString(), destination.getText().toString(), deptTime.getText().toString(),deptDate.getText().toString(), Integer.parseInt(availSeats.getText().toString()));
  					Controller.getController().setRoute( startPoint.getText().toString(), destination.getText().toString(), deptTime.getText().toString(),deptDate.getText().toString(), Integer.parseInt(availSeats.getText().toString()));
  					Controller.getController().setCar(Integer.parseInt(availSeats.getText().toString()), carType);
  					//Car c = new Car();
  					//c.setCarDetails(Integer.parseInt(availSeats.getText().toString()), carType);
  					boolean status = Controller.getController().FragmentTab1(description.getText().toString(), getApplicationContext());
  					if (status){
  						printDialogue("Congratulations", "Your post has been posted successfully");
  						
  					}else{
  						printDialogue("Error", "Post cannot be post due to unknown reason");
  					}
  					
  				}
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == Activity.RESULT_OK){
	            String result=data.getStringExtra("result");
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i<result.length(); i++){
	            	if(result.charAt(i) != '\n')
	            		sb.append(result.charAt(i));
	            }
	            startPoint.setText(sb.toString());
	            Log.i("Location", result);
	        }
	        if (resultCode == Activity.RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	    if (requestCode == 2) {
	        if(resultCode == Activity.RESULT_OK){
	            String result=data.getStringExtra("result");
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i<result.length(); i++){
	            	if(result.charAt(i) != '\n')
	            		sb.append(result.charAt(i));
	            }
	            destination.setText(sb.toString());
	            Log.i("Location", result);
	        }
	        if (resultCode == Activity.RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}
	 public void printDialogue(String heading, String body){
			new AlertDialog.Builder(CreateCarpool.this)
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
