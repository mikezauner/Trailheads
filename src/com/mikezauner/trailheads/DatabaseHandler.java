package com.mikezauner.trailheads;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "TrailHeads.sql";
    
    // TrailHeads table name
    private static final String TABLE_TRAILS = "trails";
    
    // TrailHeads column names
    private static final String KEY_NAME = "name";
    private static final String KEY_COORDS = "coords";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_LENGTH = "length";
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String CREATE_TRAILS_TABLE = "CREATE TABLE " + TABLE_TRAILS + "(" + KEY_NAME + " varchar[30], " + KEY_COORDS
    			+ " varchar[30], " + KEY_DIFFICULTY + " smallint, " + KEY_DESCRIPTION + " varchar[200], " + KEY_FACILITIES
    			+ " varchar[30], " + KEY_LENGTH + " integer);";
    	db.execSQL(CREATE_TRAILS_TABLE);
    }
    
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	// Drop old tables if they're there.
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAILS);
    	
    	// Insert new!
    	onCreate(db);
    }
    
}
