package com.mikezauner.trailheads;


import java.util.ArrayList;
import android.location.Location;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class TrailHeads extends ListActivity {
    //private static final int NEXT_ID = Menu.FIRST;
	private DatabaseHandler mDbHelper;
	MyLocation myLocation;
	Location location;
	TrailListData list;
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }
	@Override
	protected void onResume() {
		super.onResume();
    	setContentView(R.layout.activity_trailheads);
    	myLocation = new MyLocation(this);
    	location = myLocation.myLocation();
    	mDbHelper = new DatabaseHandler(this);
        mDbHelper.open();
    	ArrayList<TrailListData> m_list = new ArrayList<TrailListData>();
    	Cursor names = null;
    	try {
            names = mDbHelper.getAllTrails();
    	}
    	catch (Exception e) {
    		Log.v("EXCEPTION", ""+e);
    		
    	}
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
        	names.moveToNext();
        }
		// connecting the list adapter to this ListActivity
        final ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(new CustomList(this, m_list));
        mDbHelper.close();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		position++;
		Intent intent = new Intent(this.getApplicationContext(), Details.class);
	     // Stop GPS
        myLocation.stopService();		
		mDbHelper.close();
		intent.putExtra("position", position);
		startActivity(intent);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent settings = new Intent(this.getApplicationContext(), Preferences.class);
		     // Stop GPS
	        myLocation.stopService();
			startActivity(settings);
			return true;
		case R.id.menu_about:
			Intent about = new Intent(this.getApplicationContext(), About.class);
		     // Stop GPS
	        myLocation.stopService();
	        startActivity(about);
			return true;
		case R.id.menu_addmap:
			Intent submit = new Intent(this.getApplicationContext(), Submit.class);
		     // Stop GPS
	        myLocation.stopService();
			startActivity(submit);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
    @Override
    protected void onStop(){
       super.onStop();
// Stop GPS
       myLocation.stopService();
// Change preferences
       SharedPreferences settings = getSharedPreferences("MyParams", 0);
       SharedPreferences.Editor editor = settings.edit();
       editor.putBoolean("booleanParam", true);
// Save changes
       editor.commit();       
// Disconnect from the DB.
       mDbHelper.close();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
// Stop GPS
        myLocation.stopService();
// Change preferences
        SharedPreferences settings = getSharedPreferences("MyParams", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("booleanParam", true);
// Save changes
        editor.commit();
// Disconnect from the DB.
        mDbHelper.close();
    }
    @Override
    protected void onPause() {
        super.onPause(); 
// Stop GPS
//        myLocation.stopService();
     // Change preferences
        SharedPreferences settings = getSharedPreferences("MyParams", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("booleanParam", true);
// Save changes
        editor.commit();
// Disconnect from the DB.
        mDbHelper.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_trailheads, menu);
        return true;
    }
}
