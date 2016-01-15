package com.zwz.weRide;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.zwz.controller.Controller;

public class FragmentTab1 extends SherlockFragment{
	//EditText deptTime,deptDate,startPoint,destination,availSeats,description;
	//Button post;
	//RadioGroup radio;
	//String carType="Taxi";
	Button btn1,btn2;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragmenttab1, container, false);
		btn1 = (Button)view.findViewById(R.id.button1);
		btn2 = (Button)view.findViewById(R.id.Button01);
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity().getApplicationContext(),CreateCarpool.class);
				startActivity(i);
			}
		});
		
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity().getApplicationContext(),YourCarPool.class);
				startActivity(i);
			}
		});
		/*deptTime = (EditText)view.findViewById(R.id.EditText03);
		deptDate = (EditText)view.findViewById(R.id.EditText04);
		startPoint = (EditText)view.findViewById(R.id.EditText02);
		destination = (EditText)view.findViewById(R.id.EditText01);
		availSeats = (EditText)view.findViewById(R.id.EditText05);
		description = (EditText)view.findViewById(R.id.EditText06);
		post = (Button)view.findViewById(R.id.button1);
		radio = (RadioGroup)view.findViewById(R.id.radioGroup1);
		
		startPoint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity().getApplicationContext(),AndroidGoogleMapsActivity.class);
				getActivity().startActivityForResult(i, 1);
				//startActivity(i);
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
		         mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
		            DatePickerDialog mDatePicker=new DatePickerDialog(getActivity(), new OnDateSetListener() {                  
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
  					boolean status = Controller.getController().FragmentTab1(description.getText().toString(), getActivity());
  					if (status){
  						printDialogue("Congratulations", "Your post has been posted successfully");
  					}else{
  						printDialogue("Error", "Post cannot be post due to unknown reason");
  					}
  					
  				}
			}
		});*/
		return view;
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
