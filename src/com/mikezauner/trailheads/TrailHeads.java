package com.mikezauner.trailheads;


import java.util.ArrayList;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TrailHeads extends ListActivity {
    private static final int NEXT_ID = Menu.FIRST;
	private DatabaseHandler mDbHelper;
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_trailheads);
    	mDbHelper = new DatabaseHandler(this);
        mDbHelper.open();
        Cursor names = mDbHelper.getAllTrails();
        ArrayList<String> mNameList = new ArrayList<String>();
        names.moveToFirst();
        while (!names.isAfterLast()) {
        	mNameList.add(names.getString(names.getColumnIndexOrThrow(DatabaseHandler.KEY_NAME)));
        	names.moveToNext();
        }
        
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>
			( this, android.R.layout.simple_list_item_1, mNameList );
 
		// connecting the list adapter to this ListActivity
		setListAdapter(listAdapter);
    }
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		position++;
		Intent intent = new Intent(this.getApplicationContext(), Details.class);
		mDbHelper.close();
		intent.putExtra("position", position);
		startActivity(intent);
	}

//	public void onResume(Bundle savedInstanceState) {
//		onCreate(savedInstanceState);
//	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, NEXT_ID, 0, R.string.menuNext);
        return true;
    }
}
