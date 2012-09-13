package com.mikezauner.trailheads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class About extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_about);
    	
    	String title = "TrailHeads";
		String copyright = "��2012 Mike Zauner";
		String license = "Copyright under GPL";
		String source = "Source code available at https://github.com/mikezauner/trailheads";
		String credits = "";
		String information = "For more information, visit my website at http://trailheads.mikezauner.com";
		
		TextView titleView1 = (TextView)findViewById(R.id.titleView1);
		TextView textView1 = (TextView)findViewById(R.id.textView1);
		TextView textView2 = (TextView)findViewById(R.id.textView2);
		TextView textView3 = (TextView)findViewById(R.id.textView3);
		TextView textView4 = (TextView)findViewById(R.id.textView4);
		TextView textView5 = (TextView)findViewById(R.id.textView5);
		
		titleView1.setText(title);
		textView1.setText(copyright);
		textView2.setText(license);
		textView3.setText(source);
		textView4.setText(information);
		textView5.setText(credits);
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_about, menu);
        return true;
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent settings = new Intent(this.getApplicationContext(), Preferences.class);
			startActivity(settings);
			return true;
		case R.id.menu_about:
			Intent about = new Intent(this.getApplicationContext(), About.class);
			startActivity(about);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
}
