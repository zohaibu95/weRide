package com.zwz.weRide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ListViewAdapter extends BaseAdapter {
 
    Context context;
    String[] time_departure;
    String[] startPoint;
    String[] destinationPoint;
    
    LayoutInflater inflater;
 
    public ListViewAdapter(Context context, String[] time_departure, String[] startPoint,
            String[] destinationPoint) {
        this.context = context;
        this.time_departure = time_departure;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
    }
 
    public int getCount() {
        return time_departure.length;
    }
 
    public Object getItem(int position) {
        return null;
    }
 
    public long getItemId(int position) {
        return 0;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
 
        TextView txttime;
        TextView txtcountry;
        TextView txtpopulation;
      
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
 
        txttime = (TextView) itemView.findViewById(R.id.rank);
        txtcountry = (TextView) itemView.findViewById(R.id.country);
        txtpopulation = (TextView) itemView.findViewById(R.id.population);
 
        txttime.setText(time_departure[position]);
        txtcountry.setText(startPoint[position]);
        txtpopulation.setText(destinationPoint[position]);
 
        
 
        return itemView;
    }
}