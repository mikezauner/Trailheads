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
//    	String CREATE_TRAILS_TABLE = "CREATE TABLE " + TABLE_TRAILS + "(" + KEY_NAME + " text, " + KEY_COORDS
//    			+ " text, " + KEY_DIFFICULTY + " smallint, " + KEY_DESCRIPTION + " text, " + KEY_FACILITIES
//    			+ " text, " + KEY_LENGTH + " integer, " + KEY_ID + " integer primary key);";
//    	db.execSQL(CREATE_TRAILS_TABLE);
    }
    
    // Upgrading databasesimple_list_item_1
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
    public DatabaseHandler close() throws SQLException {
        mDb.close();
        return this;
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
     
    // Getting All Trail Names.
    public Cursor getAllTrails() {
        //List trailList = new ArrayList<Trail>();
        // Select All Query
        Cursor mCursor = mDb.query(true, TABLE_TRAILS, new String[] {KEY_NAME}, null, null, null, null, null, null);
 
        // looping through all rows and adding to list
/*        if (mCursor.moveToFirst()) {
            do {
                Trail trail= new Trail();
                trail.setName(mCursor.getString(0));
                //trail.setName(mCursor.getString(1));
                // Adding contact to list
                trailList.add(trail);
            } while (mCursor.moveToNext()); 
        	
        } */
        // return contact list
        return mCursor;
    }
    
    public int getCount() {
    	int count;
    	String query = "SELECT COUNT(*) FROM " + TABLE_TRAILS;
    	Cursor cursorCount = mDb.rawQuery(query, null);
    	count = cursorCount.getCount();
    	return count;
    }
}
