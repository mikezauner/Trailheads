package com.mikezauner.trailheads;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Submit extends Activity {
	private MyLocation myLocation;
	private Location location;
	private Context context = this;
	private EditText name;
	private EditText description;
	private EditText facilities;
	private EditText length;
	private EditText enterCoords;
	@Override
    protected void onResume() {
		super.onResume();
// Start reading the location...
    	myLocation = new MyLocation(this);
    	location = myLocation.myLocation();
// Create layoutinflater object, then start building the UI.
	    LayoutInflater factory = LayoutInflater.from(this);
		final View editTextView = factory.inflate(R.layout.layout_submit, null);
		enterCoords = (EditText) editTextView.findViewById(R.id.coords);
		name = (EditText) editTextView.findViewById(R.id.editText1);
		description = (EditText) editTextView.findViewById(R.id.editText3);
		facilities = (EditText) editTextView.findViewById(R.id.editText4);
		length = (EditText) editTextView.findViewById(R.id.editText2);
		final CheckBox useCurrent = (CheckBox) editTextView.findViewById(R.id.currentLocation);
		
// Start an onClickListener for the use current location checkbox.  We should change UI accordingly.
		useCurrent.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox)v).isChecked() == false) {
					enterCoords.setText(null, TextView.BufferType.EDITABLE);
					enterCoords.setVisibility(View.VISIBLE);
				}
				else {
					enterCoords.setVisibility(View.GONE);
				}
			}
		});
// Write UI elements to the screen.
		name.setText(null, TextView.BufferType.EDITABLE);
		description.setText(null, TextView.BufferType.EDITABLE);
		facilities.setText(null, TextView.BufferType.EDITABLE);
		length.setText(null, TextView.BufferType.EDITABLE);
		final AlertDialog.Builder alert= new AlertDialog.Builder(context);
		alert.setTitle("Submit A New Map");

		alert.setView(editTextView);
		
// Create Buttons.
        alert.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int whichButton) {
                                 sendEmail();
                         }
                         });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int whichButton) {
                                 dialog.cancel();
                         }
                         });
		alert.show();
	}
	
	private void sendEmail() {
		String body = "Trail name: " + name.getText().toString() + "\nDescription: " + description.getText().toString() + "\nFacilities: " + facilities.getText().toString();
		Log.v("EMAIL", body);
		/*Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"addmap@mikezauner.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "Map Addition");
		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(Submit.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}*/
	}
    @Override
    protected void onStop() {
	   super.onStop();
	   myLocation.stopService();
    }
   @Override
   protected void onDestroy() {
	   super.onDestroy();
 	   myLocation.stopService();
   }
//   @Override
//   protected void onPause() {
//	   super.onPause();
//	   mDbHelper.close();
//	   myLocation.stopService();
//   }
}
