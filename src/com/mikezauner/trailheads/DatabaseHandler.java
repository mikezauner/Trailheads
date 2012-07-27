package com.mikezauner.trailheads;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler {

    public static final String KEY_NAME = "name";
    public static final String KEY_DIFFICULTY= "difficulty";
    public static final String KEY_ID = "id";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_COORDS = "coords";
    public static final String KEY_LENGTH = "length";
    public static final String KEY_FACILITIES = "facilities";

    private static DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;


    private static final String DATABASE_NAME = "TrailHeads.sqlite";
    private static final String TABLE_TRAILS = "trails";
    private static final int DATABASE_VERSION = 2;

    private static Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }      
        
    public void onCreate(SQLiteDatabase db) {
    	String CREATE_TRAILS_TABLE = "CREATE TABLE " + TABLE_TRAILS + "(" + KEY_NAME + " text, " + KEY_COORDS
    			+ " text, " + KEY_DIFFICULTY + " smallint, " + KEY_DESCRIPTION + " text, " + KEY_FACILITIES
    			+ " text, " + KEY_LENGTH + " integer, " + KEY_ID + " integer primary key);";
//    	db.execSQL(CREATE_TRAILS_TABLE);
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
    public DatabaseHandler(Context ctx) {
        this.mCtx = ctx;
    }
    
    public DatabaseHandler open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    } 
 // Getting single contact
    public Cursor getTrail(int id) throws SQLException {
        Cursor mCursor =

                mDb.query(true, TABLE_TRAILS, new String[] {KEY_ID,
                        KEY_NAME, KEY_COORDS, KEY_DIFFICULTY, KEY_DESCRIPTION, KEY_FACILITIES, KEY_LENGTH}, KEY_ID + "=" + id, null,
                        null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;
    }
     
    // Getting All Contacts
/*    public List<Trail> getAllTrails() {
        List<Trail> trailList = new ArrayList<Trail>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAILS;
        SQLiteDatabase db = this.open();
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
        SQLiteDatabase db = this.open();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    } */
}
