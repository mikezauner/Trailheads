package com.mikezauner.trailheads;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;


public class TrailHeads extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
//				try {
					DatabaseHandler db = new DatabaseHandler(null);
					db.openDataBase();
					i++;
					Trail trail = db.getTrailByID(i);
					name.setText(trail.getName());
					coords.setText(trail.getCoords());
					difficulty.setText(trail.getDifficulty());
					description.setText(trail.getDescription());
					facilities.setText(trail.getFacilities());
					length.setText(trail.getDistance());
//		        }
//				catch(Exception e) {
//					Log.v("EXCEPTION: ", "exception: " + e + " i: " + i);
//				}				
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
//				try {
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
//				}
//				catch(Exception e) {
//					Log.v("EXCEPTION ", "exception: " + e + " i: " + i);
//				}
			}
		});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_trailheads, menu);
        return true;
    }
    
    
}
