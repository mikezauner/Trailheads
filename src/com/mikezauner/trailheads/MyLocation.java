package com.mikezauner.trailheads;

import java.text.DecimalFormat;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class MyLocation {
	Context mContext;
	public MyLocation(Context mContext) {
		this.mContext = mContext;
	}
	public Location myLocation() {
		String context = mContext.LOCATION_SERVICE;
		Criteria criteria = new Criteria();
		LocationManager locationManager = (LocationManager) mContext.getSystemService(context);
    	String provider = locationManager.getBestProvider(criteria, false);
    	Location location = locationManager.getLastKnownLocation(provider);
    	Log.v("PROVIDER", provider);
    	return location;
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
}
