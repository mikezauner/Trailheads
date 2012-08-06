package com.mikezauner.trailheads;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Submit extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    LayoutInflater factory = LayoutInflater.from(this);            
		final View editTextView = factory.inflate(R.layout.layout_submit, null);
		Context context = this;
		Button button;
		
		EditText name = (EditText) editTextView.findViewById(R.id.editText1);
		EditText description = (EditText) editTextView.findViewById(R.id.editText3);
		EditText facilities = (EditText) editTextView.findViewById(R.id.editText4);
		EditText length = (EditText) editTextView.findViewById(R.id.editText2);
		name.setText(null, TextView.BufferType.EDITABLE);
		description.setText(null, TextView.BufferType.EDITABLE);
		facilities.setText(null, TextView.BufferType.EDITABLE);
		length.setText(null, TextView.BufferType.EDITABLE);
		final AlertDialog.Builder alert= new AlertDialog.Builder(context);
		alert.setTitle("Submit A New Map");

		alert.setView(editTextView);
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
// TODO: Fill out email method.
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"addmap@mikezauner.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "Map Addition");
		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(Submit.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}

}
