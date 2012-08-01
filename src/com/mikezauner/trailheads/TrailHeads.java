package com.mikezauner.trailheads;


import java.util.ArrayList;

import android.location.Location;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class TrailHeads extends ListActivity {
    private static final int NEXT_ID = Menu.FIRST;
	private DatabaseHandler mDbHelper;
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_trailheads);
//        this.m_list = new CustomList(this, R.layout.row, null);
    	MyLocation myLocation = new MyLocation(this);
    	Location location = myLocation.myLocation();
    	mDbHelper = new DatabaseHandler(this);
        mDbHelper.open();
    	ArrayList<TrailListData> m_list = new ArrayList<TrailListData>();
        Cursor names = mDbHelper.getAllTrails();
        ArrayList<String> mNameList = new ArrayList<String>();
        names.moveToFirst();
        while (!names.isAfterLast()) {
        	// The top line is just the name.
        	String name = names.getString(names.getColumnIndexOrThrow(DatabaseHandler.KEY_NAME));
        	// Calculating the bottom line isn't so easy...
        	// First, we need the TH location.
        	String Coords = names.getString(names.getColumnIndexOrThrow(DatabaseHandler.KEY_COORDS));
        	// Then calculate the distance, and set to cList.bottom.
        	String dist = myLocation.CalculateDistance(Coords, location);
        	TrailListData list = new TrailListData();
        	list.setName(name);
        	list.setDistance(dist);
        	m_list.add(list);
        	
        	// DEPRECATED!  TO BE REMOVED!!!
        	mNameList.add(names.getString(names.getColumnIndexOrThrow(DatabaseHandler.KEY_NAME)));
        	names.moveToNext();
        }
		// connecting the list adapter to this ListActivity
        final ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(new CustomList(this, m_list));
    }
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		position++;
		Intent intent = new Intent(this.getApplicationContext(), Details.class);
		mDbHelper.close();
		intent.putExtra("position", position);
		startActivity(intent);
	}

//	public void onResume(Bundle savedInstanceState) {
//		onCreate(savedInstanceState);
//	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, NEXT_ID, 0, R.string.menuNext);
        return true;
    }
}
