package com.mikezauner.trailheads;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Trailheads extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailheads);
//        text = (TextView) findViewById(R.id.textView1);
    }
    
    public void myClickHandler(View view) {
        DatabaseHandler db = new DatabaseHandler(this, null, null, 0);
        
        /**
         * CRUD Operations
         * */
     // Reading all contacts
        List<Trail> trail = db.getAllTrails();       
        // Put the trails on the screen one by one...
        for (Trail cn : trail) {
        	
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_trailheads, menu);
        return true;
    }

    
}
