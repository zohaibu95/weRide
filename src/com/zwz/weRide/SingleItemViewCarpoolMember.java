package com.zwz.weRide;

import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;
import com.zwz.models.CarPool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class SingleItemViewCarpoolMember extends Activity {
    // Declare Variables
    TextView txtName;
    TextView txtPhone;
    TextView txtCity;
    String[] Name;
    String[] Phone;
    String[] City;
    int position;
    Button removeMember;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemveiwcarpoolmember);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
        Intent i = getIntent();
        position = i.getExtras().getInt("position");
        Name= i.getStringArrayExtra("Name");
        Phone = i.getStringArrayExtra("Phone");
        final int[] userID = i.getIntArrayExtra("user_id");
        final int carpoolID = i.getIntExtra("carpool_id", 0);
        txtName = (TextView) findViewById(R.id.membername);
        txtPhone = (TextView) findViewById(R.id.memberphone);
        removeMember = (Button) findViewById(R.id.removemembercarpool);
        txtName.setText(Name[position]);
        txtPhone.setText(Phone[position]);
        removeMember.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean status = Controller.getController().SingleItemViewCarpoolMemberRemoveMember(userID[position], carpoolID, getApplicationContext());
				if (status){
					Toast.makeText(getApplicationContext(), "Member has been removed from your carpool", Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
    }
}