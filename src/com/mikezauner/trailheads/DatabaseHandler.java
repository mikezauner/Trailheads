package com.mikezauner.trailheads;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Location
    private static final String DATABASE_PATH = "/data/data/com.mikezauner.trailheads/databases/";
	// Database Name
    private static final String DATABASE_NAME = "TrailHeads.sqlite";
    // TrailHeads table name
    private static final String TABLE_TRAILS = "trails";
    
    // TrailHeads column names
    private static final String KEY_NAME = "name";
    private static final String KEY_COORDS = "coords";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_LENGTH = "length";
    private static final String KEY_ID = "id";
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
    public SQLiteDatabase openDataBase() throws SQLException{
        String myPath = DATABASE_PATH + DATABASE_NAME;
        try {
        	SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(Exception e) {
        	Log.v("EXCEPTION: ", ""+e);
        }
		return null;
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String CREATE_TRAILS_TABLE = "CREATE TABLE " + TABLE_TRAILS + "(" + KEY_NAME + " text, " + KEY_COORDS
    			+ " text, " + KEY_DIFFICULTY + " smallint, " + KEY_DESCRIPTION + " text, " + KEY_FACILITIES
    			+ " text, " + KEY_LENGTH + " integer, " + KEY_ID + " integer primary key);";
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
    
 // Getting single contact
    public Trail getTrail(int name) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_TRAILS, new String[] { KEY_NAME, 
        		KEY_COORDS, KEY_DIFFICULTY, KEY_DESCRIPTION, KEY_FACILITIES, KEY_LENGTH, KEY_ID }, KEY_NAME + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Trail trail = new Trail(cursor.getString(0),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                cursor.getString(4), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));
        // return contact
        return trail;
    }
    public Trail getTrailByID(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRAILS, new String[] { KEY_ID, 
        		KEY_COORDS, KEY_DIFFICULTY, KEY_DESCRIPTION, KEY_FACILITIES, KEY_LENGTH, KEY_ID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Trail trail = new Trail(cursor.getString(0),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                cursor.getString(4), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));
        // return contact
    	return trail;
    }
     
    // Getting All Contacts
    public List<Trail> getAllTrails() {
        List<Trail> trailList = new ArrayList<Trail>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAILS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Trail trail= new Trail();
                trail.setName(cursor.getString(0));
                trail.setCoords(cursor.getString(1));
                trail.setDifficulty(Integer.parseInt(cursor.getString(2)));
                trail.setDescription(cursor.getString(3));
                trail.setFacilities(cursor.getString(4));
                trail.setDistance(Integer.parseInt(cursor.getString(5)));
                trail.setID(Integer.parseInt(cursor.getString(6)));
                // Adding contact to list
                trailList.add(trail);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return trailList;
    }
     
    // Getting contacts Count
    public int getTrailCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRAILS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    
}
