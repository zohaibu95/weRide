package com.zwz.weRide;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.zwz.DataBaseHandler.DatabaseHandler;
import com.zwz.controller.Controller;
import com.zwz.models.Comments;

public class PostComments extends Activity {

	String[] PosterName;
	String[] Comment;
	ListView list;
    ListViewAdapterComment adapter;
    Button CommentPostButton;
    TextView CommentText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.postcomments);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0d87b3")));
		String temp = getIntent().getStringExtra("chatroom_id");
		final int chatroomID = Integer.parseInt(temp);
		Log.i("ChatRoomId", String.valueOf(chatroomID));
		
		ArrayList<Comments> commentlist = Controller.getController().GetComments(chatroomID, getApplicationContext());
		PosterName = new String[commentlist.size()];
		Comment = new String[commentlist.size()];
		
		for (int j=0; j<commentlist.size();j++) 
		{
			//Comments comments = new Comments();
			Controller.getController().setComment(commentlist.get(j)); //= commentlist.get(j);
			Comment[j] = Controller.getController().getComment().getComment();
			PosterName[j] = Controller.getController().GetUserName(Controller.getController().getComment().getUserID(),getApplicationContext() );
			
		}
 
        list = (ListView) findViewById(R.id.listViewpostcomment);
        
        adapter = new ListViewAdapterComment(getApplicationContext(), PosterName, Comment);
        
        list.setAdapter(adapter);
        CommentPostButton = (Button)findViewById(R.id.button1);
        CommentPostButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				CommentText = (EditText)findViewById(R.id.editText1);
				String NewComment = CommentText.getText().toString();
				boolean status = Controller.getController().PostComment(chatroomID, NewComment, getApplicationContext());
  				if(status == true)
  				{
  					Toast.makeText(getApplicationContext(),"Your Comment Added", Toast.LENGTH_LONG).show();
  				}
  				Intent intent = getIntent();
  				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
  				finish();
  			    startActivity(intent);
			}
		});
		
        
	}
	
	
}
