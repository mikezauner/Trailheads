package com.mikezauner.trailheads;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Details extends Activity {
//    private static final int NEXT_ID = Menu.FIRST;
    private Cursor TrailCursor;
	private DatabaseHandler mDbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Intent intent = getIntent();
    	Bundle bundle = intent.getExtras();
    	int id = bundle.getInt("position");
    	setContentView(R.layout.activity_details);
        mDbHelper = new DatabaseHandler(this);
        mDbHelper.open();
        displayNext(id);
        final Button button = (Button) findViewById(R.id.navigate);
        final String Coords = TrailCursor.getString(TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_COORDS));
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+Coords));
            	startActivity(i);
            }
        });
    }
/*    @Override
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
*/
    public boolean displayNext(int id) {
    	Log.v("displayNext", ""+id);
    	TrailCursor = mDbHelper.getTrail(id);
    	mDbHelper.close();
    	TextView name = (TextView)findViewById(R.id.name);
    	TextView facilities = (TextView)findViewById(R.id.facilities);
    	TextView difficulty = (TextView)findViewById(R.id.difficulty);
    	TextView distance = (TextView)findViewById(R.id.length);
    	TextView description = (TextView)findViewById(R.id.description);
        name.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_NAME)));
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
}
