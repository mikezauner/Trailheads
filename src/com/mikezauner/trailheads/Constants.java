package com.mikezauner.trailheads;

public class Constants {
    public static String DATABASE_NAME = "TrailHeads.sqlite";
    public static int DATABASE_VERSION = 2;
    
    public static String KEY_NAME = "name";
    public static String KEY_DIFFICULTY= "difficulty";
    public static String KEY_ID = "id";
    public static String KEY_DESCRIPTION = "description";
    public static String KEY_COORDS = "coords";
    public static String KEY_LENGTH = "length";
    public static String KEY_FACILITIES = "facilities";
    public static String KEY_PERMIT = "permit";
	
    public static long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    public static long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
}
