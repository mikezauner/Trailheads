package com.mikezauner.trailheads;

import java.util.ArrayList;

import com.radioactiveyak.location_best_practices.PlacesConstants;
import com.radioactiveyak.location_best_practices.receivers.LocationChangedReceiver;
import com.radioactiveyak.location_best_practices.receivers.NewCheckinReceiver;
import com.radioactiveyak.location_best_practices.receivers.PassiveLocationChangedReceiver;
import com.radioactiveyak.location_best_practices.services.EclairPlacesUpdateService;
import com.radioactiveyak.location_best_practices.services.PlacesUpdateService;
import com.radioactiveyak.location_best_practices.utils.PlatformSpecificImplementationFactory;
import com.radioactiveyak.location_best_practices.utils.base.ILastLocationFinder;
import com.radioactiveyak.location_best_practices.utils.base.IStrictMode;
import com.radioactiveyak.location_best_practices.utils.base.LocationUpdateRequester;
import com.radioactiveyak.location_best_practices.utils.base.SharedPreferenceSaver;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class TrailHeads extends ListActivity {
    //private static final int NEXT_ID = Menu.FIRST;
	  protected static String TAG = "PlaceActivity";
	  private DatabaseHandler mDbHelper;
	private long distance = Constants.MINIMUM_DISTANCE_CHANGE_FOR_UPDATES;
	private long time = Constants.MINIMUM_TIME_BETWEEN_UPDATES;
	protected Criteria criteria;
	protected IntentFilter newCheckinFilter;
	protected ComponentName newCheckinReceiverName;
	protected ILastLocationFinder lastLocationFinder;
	protected LocationUpdateRequester locationUpdateRequester;
	protected PendingIntent locationListenerPendingIntent;
	protected PendingIntent locationListenerPassivePendingIntent;
	MyLocation myLocation;
	LocationManager locationManager;
	Location location;
	TrailListData list;
	
	@Override
	protected void onResume() {
		super.onResume();
    	setContentView(R.layout.activity_trailheads);
    	
    	/*progressBar = ProgressDialog.show(this,  "",  "Acquiring Location", true, false);
    	new Thread(){
    		@Override
    	    public void run() {
                Looper.prepare();
                
    	        myLocation = new MyLocation(context);
    	        location = myLocation.myLocation();
    	        while (location == null) {}
    	        Looper.loop();
                // After receiving first GPS Fix dismiss the Progress Dialog
                //progressBar.dismiss();
                //return;
    	        myLocation.stopService();
    	    }
    	}.start();
    	progressBar.dismiss();*/
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
        	String name = names.getString(names.getColumnIndexOrThrow(Constants.KEY_NAME));
        	// Calculating the bottom line isn't so easy...
        	// First, we need the TH location.
        	String Coords = names.getString(names.getColumnIndexOrThrow(Constants.KEY_COORDS));
        	String dist = null;
        	// Then calculate the distance, and set to cList.bottom.
        	try {
        	dist = myLocation.CalculateDistance(Coords, location);
        	}
        	catch (Exception e) {
        		Log.v("DISTANCE", ""+e);
        	}
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
    
    public void startLocationService() {
    	// Get a handle to the Fragments
    	//    placeListFragment = (PlaceListFragment)getSupportFragmentManager().findFragmentById(R.id.list_fragment);
    	//    checkinFragment = (CheckinFragment)getSupportFragmentManager().findFragmentById(R.id.checkin_fragment);
    	    
    	    // Get references to the managers
    	    PackageManager packageManager = getPackageManager();
    	    NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    	    locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	    
    	    // Get a reference to the Shared Preferences and a Shared Preference Editor.
    	    SharedPreferences prefs = getSharedPreferences(PlacesConstants.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
    	    Editor prefsEditor = prefs.edit();  
    	    
    	    // Instantiate a SharedPreferenceSaver class based on the available platform version.
    	    // This will be used to save shared preferences
    	    SharedPreferenceSaver sharedPreferenceSaver = PlatformSpecificImplementationFactory.getSharedPreferenceSaver(this);
    	           
    	    // Save that we've been run once.
    	    prefsEditor.putBoolean(PlacesConstants.SP_KEY_RUN_ONCE, true);
    	    sharedPreferenceSaver.savePreferences(prefsEditor, false);
    	    
    	    // Specify the Criteria to use when requesting location updates while the application is Active
    	    criteria = new Criteria();
    	    if (PlacesConstants.USE_GPS_WHEN_ACTIVITY_VISIBLE)
    	      criteria.setAccuracy(Criteria.ACCURACY_FINE);
    	    else
    	      criteria.setPowerRequirement(Criteria.POWER_LOW);
    	    
    	    // Setup the location update Pending Intents
    	    Intent activeIntent = new Intent(this, LocationChangedReceiver.class);
    	    locationListenerPendingIntent = PendingIntent.getBroadcast(this, 0, activeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    	    Intent passiveIntent = new Intent(this, PassiveLocationChangedReceiver.class);
    	    locationListenerPassivePendingIntent = PendingIntent.getBroadcast(this, 0, passiveIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    	    // Instantiate a LastLocationFinder class.
    	    // This will be used to find the last known location when the application starts.
    	    lastLocationFinder = PlatformSpecificImplementationFactory.getLastLocationFinder(this);
			lastLocationFinder.setChangedLocationListener(oneShotLastLocationUpdateListener);
    	    
    	    // Instantiate a Location Update Requester class based on the available platform version.
    	    // This will be used to request location updates.
    	    locationUpdateRequester = PlatformSpecificImplementationFactory.getLocationUpdateRequester(locationManager);
      }
    /**
     * One-off location listener that receives updates from the {@link LastLocationFinder}.
     * This is triggered where the last known location is outside the bounds of our maximum
     * distance and latency.
     */
    protected LocationListener oneShotLastLocationUpdateListener = new LocationListener() {
      public void onLocationChanged(Location l) {

      }
     
      public void onProviderDisabled(String provider) {}
      public void onStatusChanged(String provider, int status, Bundle extras) {}
      public void onProviderEnabled(String provider) {}
    };
}