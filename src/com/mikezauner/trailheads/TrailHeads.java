package com.mikezauner.trailheads;


import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class TrailHeads extends Activity {
    private static final int NEXT_ID = Menu.FIRST;
//    Button BUTTON_NEXT = (Button)findViewById(R.id.nextButton);
//    Button BUTTON_PREV = (Button)findViewById(R.id.prevButton);
    private Cursor TrailCursor;
	private DatabaseHandler mDbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_trailheads);
        mDbHelper = new DatabaseHandler(this);
        mDbHelper.open();
        displayNext(1);
    }
    int id = 1;
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case NEXT_ID:
            	id++;
                displayNext(id);
                return true;
            
        }
        

        return super.onMenuItemSelected(featureId, item);
    } 
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, NEXT_ID, 0, R.string.menuNext);
        return true;
    }

    public boolean displayNext(int id) {
    	TrailCursor = mDbHelper.getTrail(id);
    	TextView name = (TextView)findViewById(R.id.name);
    	TextView coords = (TextView)findViewById(R.id.coords);
    	TextView facilities = (TextView)findViewById(R.id.facilities);
    	TextView difficulty = (TextView)findViewById(R.id.difficulty);
    	TextView distance = (TextView)findViewById(R.id.length);
    	TextView description = (TextView)findViewById(R.id.description);
       name.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_NAME)));
        coords.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_COORDS)));
        facilities.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_FACILITIES)));
        difficulty.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_DIFFICULTY)));
        distance.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_LENGTH)));
        description.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_DESCRIPTION)));
        return true;
    }
/*    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailheads);
        Button next = (Button)findViewById(R.id.nextButton);
        Button prev = (Button)findViewById(R.id.prevButton);
        next.setOnClickListener(new View.OnClickListener() {
            TextView name = (TextView)findViewById(R.id.name);
            TextView coords = (TextView)findViewById(R.id.coords);
            TextView difficulty = (TextView)findViewById(R.id.difficulty);
            TextView description = (TextView)findViewById(R.id.description);
            TextView facilities = (TextView)findViewById(R.id.facilities);
            TextView length = (TextView)findViewById(R.id.length);
            int i = 0;
			@Override
			public void onClick(View v) {
				try {
					DatabaseHandler db = new DatabaseHandler(null);
					try {
					db.openDataBase();
					}
					catch (Exception e) {
						Log.v("EXCEPTION", ""+e);
					}
					i++;
					List<Trail> trail = db.getAllTrails();
					for (Trail tr : trail) {
						String log = "Id: " + tr.getID() + " , Name: " + tr.getName();
						Log.d("Name: ", log);
					}
					Trail trail = db.getTrailByID(i);
					name.setText(trail.getName());
					coords.setText(trail.getCoords());
					difficulty.setText(trail.getDifficulty());
					description.setText(trail.getDescription());
					facilities.setText(trail.getFacilities());
					length.setText(trail.getDistance());
		        }
				catch(Exception e) {
					Log.v("EXCEPTION: ", "exception: " + e + " i: " + i);
				}				
			}
		});
        prev.setOnClickListener(new View.OnClickListener() {
            TextView name = (TextView)findViewById(R.id.name);
            TextView coords = (TextView)findViewById(R.id.coords);
            TextView difficulty = (TextView)findViewById(R.id.difficulty);
            TextView description = (TextView)findViewById(R.id.description);
            TextView facilities = (TextView)findViewById(R.id.facilities);
            TextView length = (TextView)findViewById(R.id.length);
            int i = 0;			
			@Override
			public void onClick(View v) {
				try {
					DatabaseHandler db = new DatabaseHandler(null);
					db.openDataBase();
					i--;
		        	Trail trail = db.getTrailByID(i);
		        	name.setText(trail.getName());
		        	coords.setText(trail.getCoords());
		        	difficulty.setText(trail.getDifficulty());
		        	description.setText(trail.getDescription());
		        	facilities.setText(trail.getFacilities());
		        	length.setText(trail.getDistance());
				}
				catch(Exception e) {
					Log.v("EXCEPTION ", "exception: " + e + " i: " + i);
				}
			}
		});
    } */
    
    
}
