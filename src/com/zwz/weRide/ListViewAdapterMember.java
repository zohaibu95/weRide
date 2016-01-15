package com.zwz.weRide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
@SuppressLint("ViewHolder")
public class ListViewAdapterMember extends BaseAdapter {
 
    Context context;
    String[] Name;
    String[] Phone;
    LayoutInflater inflater;
 
    public ListViewAdapterMember(Context context, String[] Name, String[] Phone) {
        this.context = context;
        this.Name = Name;
        this.Phone = Phone;
    }
 
    public int getCount() {
        return Name.length;
    }
 
    public Object getItem(int position) {
        return null;
    }
 
    public long getItemId(int position) {
        return 0;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
 
        TextView txtname;
        TextView txtphone;
 
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        View itemView = inflater.inflate(R.layout.listveiw_carpoolmember, parent, false);
 
        txtname = (TextView) itemView.findViewById(R.id.membername);
        txtphone = (TextView) itemView.findViewById(R.id.memberphone);
       
 
        txtname.setText(Name[position]);
        txtphone.setText(Phone[position]);
 
       
 
        return itemView;
    }
}