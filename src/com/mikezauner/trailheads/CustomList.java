package com.mikezauner.trailheads;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomList extends BaseAdapter {
	private static ArrayList<TrailListData> searchArrayList;

	Context _context;
	private LayoutInflater mInflater;

	public CustomList(Context context, ArrayList<TrailListData> results) {
	       searchArrayList = results;
	       mInflater = LayoutInflater.from(context);
	}

	public int getCount(){
		return searchArrayList.size();
	}
	 
	public long getItemId(int position){
		return position;
	}

	public Object getItem(int position){
		return searchArrayList.get(position);
	} 

	//this function will be called to draw each row 
	//for the listview
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
	    if (convertView == null) {
	    	convertView = mInflater.inflate(R.layout.row, null);
	        holder = new ViewHolder();
	        holder.txtName = (TextView) convertView.findViewById(R.id.toptext);
	        holder.txtDistance = (TextView) convertView.findViewById(R.id.bottomtext);
	 
	        convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	 
	        holder.txtName.setText("Name: " + searchArrayList.get(position).getName());
	        holder.txtDistance.setText("Flying Distance: " + searchArrayList.get(position).getDistance() + " miles");
	 
	        return convertView;
		}
	 
	    static class ViewHolder {
	        TextView txtName;
	        TextView txtDistance;
	    }
}