package com.mikezauner.trailheads;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends Activity {
//    private static final int NEXT_ID = Menu.FIRST;
    private Cursor TrailCursor;
	private DatabaseHandler mDbHelper;
	private MyLocation myLocation;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_details);
    	Intent intent = getIntent();
    	Bundle bundle = intent.getExtras();
    	int id = bundle.getInt("position");
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
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(this.getApplicationContext(), Preferences.class);
			mDbHelper.close();
			startActivity(intent);
			return true;
		case R.id.menu_about:
			Intent about = new Intent(this.getApplicationContext(), About.class);
			startActivity(about);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }
    @Override
    protected void onResume() {
    	super.onResume();
    	onCreate(null);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    protected void onStop() {
	   super.onStop();
	   mDbHelper.close();
	   myLocation.stopService();
    }
   @Override
   protected void onDestroy() {
	   super.onDestroy();
	   mDbHelper.close();
	   myLocation.stopService();
   }
//   @Override
//   protected void onPause() {
//	   super.onPause();
//	   mDbHelper.close();
//	   myLocation.stopService();
//   }
   public boolean displayNext(int id) {
    	myLocation = new MyLocation(this);
    	Location location = myLocation.myLocation();
    	TrailCursor = mDbHelper.getTrail(id);
    	String Coords = TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_COORDS));
    	String dist = myLocation.CalculateDistance(Coords, location);
    	mDbHelper.close();
        int difficulty = TrailCursor.getInt(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_DIFFICULTY));
        int permit = TrailCursor.getInt(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_PERMIT));
        switch(difficulty) {
        case 1:
            ImageView image1 = (ImageView) findViewById(R.id.imageView1);
            ImageView image2 = (ImageView) findViewById(R.id.imageView2);
            ImageView image3 = (ImageView) findViewById(R.id.imageView3);
        	image1.setImageResource(R.drawable.ic_star);
        	image2.setImageResource(R.drawable.ic_estar);
        	image3.setImageResource(R.drawable.ic_estar);
        	break;
        case 2:
            ImageView image4 = (ImageView) findViewById(R.id.imageView1);
            ImageView image5 = (ImageView) findViewById(R.id.imageView2);
            ImageView image6 = (ImageView) findViewById(R.id.imageView3);
        	image4.setImageResource(R.drawable.ic_star);
        	image5.setImageResource(R.drawable.ic_star);
        	image6.setImageResource(R.drawable.ic_estar);
        	break;
        case 3:
            ImageView image7 = (ImageView) findViewById(R.id.imageView1);
            ImageView image8 = (ImageView) findViewById(R.id.imageView2);
            ImageView image9 = (ImageView) findViewById(R.id.imageView3);
        	image7.setImageResource(R.drawable.ic_star);
        	image8.setImageResource(R.drawable.ic_star);
        	image9.setImageResource(R.drawable.ic_star);
        	break;
        }
        ImageView image10 = (ImageView) findViewById(R.id.imageView4);
        if (permit == 0) {
        	image10.setImageResource(R.drawable.ic_check);
        }
        else {
        	image10.setImageResource(R.drawable.ic_exclamation);
        }
    	TextView name = (TextView)findViewById(R.id.name);
    	TextView distance = (TextView) findViewById(R.id.distance);
    	TextView facilities = (TextView)findViewById(R.id.facilities);
    	TextView length = (TextView)findViewById(R.id.length);
    	TextView description = (TextView)findViewById(R.id.description);
        name.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_NAME)));
        distance.setText(dist + " miles from here");
        facilities.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_FACILITIES)));
        length.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_LENGTH)) + " miles long");
        description.setText(TrailCursor.getString(
        		TrailCursor.getColumnIndexOrThrow(DatabaseHandler.KEY_DESCRIPTION)));
        return true;
    }
}
