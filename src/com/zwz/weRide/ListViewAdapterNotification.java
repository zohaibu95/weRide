package com.zwz.weRide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapterNotification extends BaseAdapter {
    Context context;
    String[] Notification;
    LayoutInflater inflater;
 
    public ListViewAdapterNotification(Context context, String[] Notification) {
        this.context = context;
        this.Notification = Notification;
    }
 
    public int getCount() {
        return Notification.length;
    }
 
    public Object getItem(int position) {
        return null;
    }
 
    public long getItemId(int position) {
        return 0;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
 
        TextView txtnotification;
      
 
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        View itemView = inflater.inflate(R.layout.listview_notification, parent, false);
 
        txtnotification = (TextView) itemView.findViewById(R.id.notification);
       
 
        txtnotification.setText(Notification[position]);
 
       
 
        return itemView;
    }

}
