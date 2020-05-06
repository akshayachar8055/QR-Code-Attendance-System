package com.attendance;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	
	EditText mUserid,mPassword,mName,mSubject,mPhone;
	Button mSubmit;
	static String mSubjectVal;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		mUserid = (EditText) findViewById(R.id.editText1);
		mPassword = (EditText) findViewById(R.id.editText2);
		mName = (EditText) findViewById(R.id.editText3);
		mSubject = (EditText) findViewById(R.id.editText4);
		mPhone = (EditText) findViewById(R.id.editText5);
		mSubmit = (Button) findViewById(R.id.button1);
		   
		
		mSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] subs = mSubject.getText().toString().split("\\,");
				
				if(subs.length!=3){
					Toast.makeText(getApplicationContext(), "Subject must be 3", 60000).show();
				}else{  
					
					Insertdata();
					
					finish();
				}
			}
		});
	}
	
	public void Insertdata(){
		
		ContentValues cv = new ContentValues();
		cv.put("Username", mUserid.getText().toString());
		cv.put("Password", mPassword.getText().toString());
		cv.put("Name", mName.getText().toString());
		cv.put("MobileNo", mPhone.getText().toString());
		cv.put("Subject", mSubject.getText().toString());
		
		Database dat = new Database(getBaseContext());
		SQLiteDatabase db = dat.getWritableDatabase();   
		
		Cursor c = db.rawQuery("select * from Faculty where Username='"+mUserid.getText().toString()+"'", null);
		if(c.getCount()!=0){
			Toast.makeText(getApplicationContext(), "User Already exists.", 6000).show();
		}else{   
			Toast.makeText(getApplicationContext(), "registration success full", 60000).show();
			db.insert("Faculty", null, cv);
		}
		db.close();
	}

}
