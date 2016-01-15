package com.zwz.weRide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
@SuppressLint("ViewHolder")
public class ListViewAdapterComment extends BaseAdapter {
 
    Context context;
    String[] PosterName;
    String[] Comment;
    LayoutInflater inflater;
 
    public ListViewAdapterComment(Context context, String[] PosterName, String[] Comment) {
        this.context = context;
        this.PosterName = PosterName;
        this.Comment = Comment;
    }
 
    public int getCount() {
        return PosterName.length;
    }
 
    public Object getItem(int position) {
        return null;
    }
 
    public long getItemId(int position) {
        return 0;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
 
        TextView txtname;
        TextView txtcomment;
 
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        View itemView = inflater.inflate(R.layout.listview_comment, parent, false);
 
        txtname = (TextView) itemView.findViewById(R.id.name);
        txtcomment = (TextView) itemView.findViewById(R.id.comment);
        txtname.setText(PosterName[position]);
        txtcomment.setText(Comment[position]);
 
        return itemView;
    }
}