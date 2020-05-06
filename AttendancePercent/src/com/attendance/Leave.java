package com.attendance;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Leave extends Activity{
	EditText mName,mDescription,mDate,mNo;
	Button mSubmit;
	
	@Override   
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leave);
		mName = (EditText) findViewById(R.id.editText1);
		mDescription = (EditText) findViewById(R.id.editText2);
		mDate = (EditText) findViewById(R.id.editText3);
		mNo = (EditText) findViewById(R.id.editTextno);
		
		mSubmit = (Button) findViewById(R.id.button1);
		
		mSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = mName.getText().toString();
				String description = mDescription.getText().toString();
				String date = mDate.getText().toString();
				
				if(name.equals("")||description.equals("")||date.equals("")||mNo.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Please enter details", 600000).show();
				}else{
					SmsManager sm = SmsManager.getDefault();
					sm.sendTextMessage(mNo.getText().toString(), null, name+"\n"+description+"\n for date"+date, null, null);
				}  
			}   
		});
	}
}
