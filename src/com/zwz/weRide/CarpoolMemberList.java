package com.zwz.weRide;

import java.util.ArrayList;

import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;
import com.zwz.models.User;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CarpoolMemberList extends Activity {

	String[] Name;
	String[] Phone;
	int[] userId;
	ListView list;
    ListViewAdapterMember adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carpoolmemberlist);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
		final int cID = getIntent().getIntExtra("carpool_id", 0);
		ArrayList<User> acceptedUsers = Controller.getController().CarpoolMemberList(cID, getApplicationContext());
		
		Name = new String[acceptedUsers.size()];
		Phone = new String[acceptedUsers.size()];
		userId = new int[acceptedUsers.size()];
		for(int i=0; i<Name.length; i++)
			Name[i] = acceptedUsers.get(i).getFirstName() + " " + acceptedUsers.get(i).getLastName();
		for(int j=0; j<Phone.length; j++)
			Phone[j] = acceptedUsers.get(j).getPhoneNum();
		for(int j=0; j<userId.length; j++)
			userId[j] = acceptedUsers.get(j).getuID();
		
		
        list = (ListView) findViewById(R.id.listviewcarpoolmember);
        adapter = new ListViewAdapterMember(getApplicationContext(), Name, Phone);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
        	 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // Send single item click data to SingleItemView Class
                Intent i = new Intent(getApplicationContext(), SingleItemViewCarpoolMember.class);
                i.putExtra("Name", Name);
                i.putExtra("Phone", Phone);
                i.putExtra("carpool_id", cID);
                i.putExtra("user_id", userId );
                i.putExtra("position", position);
                startActivity(i);
                finish();
            }
 
        });
		
	}
	
}
