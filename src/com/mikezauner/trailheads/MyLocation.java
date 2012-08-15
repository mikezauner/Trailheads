package com.mikezauner.trailheads;

import java.text.DecimalFormat;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MyLocation {
	private static Context mContext;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	LocationManager locationManager;
	Location location;
	MyLocationListener myLL = new MyLocationListener();
	private SharedPreferences prefs;
	public MyLocation(Context mContext) {
		MyLocation.mContext = mContext;
	}
	public Location myLocation() {
		locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		String provider = prefs.getString("location_method", null);
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
