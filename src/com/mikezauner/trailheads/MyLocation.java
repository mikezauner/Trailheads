package com.mikezauner.trailheads;

import java.text.DecimalFormat;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class MyLocation {
	static Context mContext;
    static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	LocationManager locationManager;
	Location location;
	MyLocationListener myLL = new MyLocationListener();
	SharedPreferences prefs;
	public MyLocation(Context mContext) {
		MyLocation.mContext = mContext;
	}
	public Location myLocation() {
		Log.v("STARTLOC", Context.LOCATION_SERVICE);
		locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		String provider = prefs.getString("location_method", null);
		Log.v("PROVIDER", provider);
	    final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

	    if (!gpsEnabled) {
	        Log.v("GPS", "Falling back to network...");
	        SharedPreferences.Editor prefEditor = prefs.edit();
	        prefEditor.putString("location_method", "network");
	        prefEditor.commit();
	        provider = prefs.getString("location_method", null);
	    }
	    location = locationManager.getLastKnownLocation(provider);
    	locationManager.requestLocationUpdates(
    			                provider,
    			                MINIMUM_TIME_BETWEEN_UPDATES,
    			                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
    			                myLL
    			        );
		return location;
	}

	public void stopService() {
	    // code to stop updating server
		locationManager.removeUpdates(myLL);
		//locationManager = null;
	}
	public String CalculateDistance(String dest, Location myLocation) {
		String[] coords = dest.split(",");
		double latitude = Double.parseDouble(coords[0]);
		double longitude = Double.parseDouble(coords[1]);
		Location destination = new Location(dest);
		float distance;
		destination.setLatitude(latitude);
		destination.setLongitude(longitude);
		distance = myLocation.distanceTo(destination);
		distance = distance / 1000;
		String toMiles = (new DecimalFormat("#.##").format(distance * 0.621371192));
		return toMiles;
	}
    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            
        }
        public void onStatusChanged(String s, int i, Bundle b) {

        }
        public void onProviderDisabled(String s) {

        }
        public void onProviderEnabled(String s) {

        }
    }
}
