package com.attendance;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Entermarks extends Activity{
	String usn;
	static String studentData;
	String[] dataMarks;
	TextView mTextview1,mTextview2,mTextview3;
	
	@Override   
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marks);
		
		mTextview1 = (TextView) findViewById(R.id.textView1);
		mTextview2 = (TextView) findViewById(R.id.textView2);
		mTextview3 = (TextView) findViewById(R.id.textView3);
		
		Register.mSubjectVal = "ISM,ST,NS";
		String[] subs = Register.mSubjectVal.split("\\,");
		       
		mTextview1.setText(subs[0]);   
		mTextview2.setText(subs[1]);
		mTextview3.setText(subs[2]);
		
		usn = getIntent().getStringExtra("usn");
		stundentData();
		Button btn = (Button) findViewById(R.id.edit);
		
		final EditText m1 = (EditText) findViewById(R.id.button1);
		final EditText m2 = (EditText) findViewById(R.id.button2);
		final EditText m3 = (EditText) findViewById(R.id.button3);
		final EditText m4 = (EditText) findViewById(R.id.button4);
		final EditText m5 = (EditText) findViewById(R.id.button5);
		final EditText m6 = (EditText) findViewById(R.id.button6);
		final EditText m7 = (EditText) findViewById(R.id.button7);
		final EditText m8 = (EditText) findViewById(R.id.button8);
		
		final EditText a1 = (EditText) findViewById(R.id.attendance1);   
		final EditText a2 = (EditText) findViewById(R.id.attendace2);  
		final EditText a3 = (EditText) findViewById(R.id.attendance3);
		  
		Button et = (Button) findViewById(R.id.submit);
		
		btn.setOnClickListener(new OnClickListener() {     
			  
			@Override
			public void onClick(View arg0) {  
				// TODO Auto-generated method stub
				Intent intent = new Intent(Entermarks.this,Student.class);
				intent.putExtra("edit", "yes");
				startActivity(intent);
			}
		});
		et.setOnClickListener(new OnClickListener() {
			
			@Override  
			public void onClick(View arg0) {  
				// TODO Auto-generated method stub
				ContentValues cv = new ContentValues();
				cv.put("M1", m1.getText().toString());
				cv.put("M2", m2.getText().toString());
				cv.put("M3", m3.getText().toString());
				cv.put("M4", m4.getText().toString());  
				cv.put("M5", m5.getText().toString());  
				cv.put("M6", m6.getText().toString());  
				cv.put("M7", m7.getText().toString());  
				cv.put("M8", m8.getText().toString());  
				
				
//				cv.put("Attendance1", a1.getText().toString());
//				cv.put("Attendance2", a2.getText().toString());
//				cv.put("Attendance3", a3.getText().toString());       
				      
				Database dat = new Database(getBaseContext());
	            SQLiteDatabase db = dat.getWritableDatabase();
	            
	            int row = db.update("Student", cv, "USN='"+usn+"'", null);
	            Toast.makeText(getApplicationContext(), "row updated "+row, 6000000).show();
	            db.close();
	            finish();
			}     
		});   
	}
	
	public void stundentData(){
		 Database dat = new Database(getBaseContext());
         SQLiteDatabase db = dat.getWritableDatabase();
            
         Cursor c = db.rawQuery("select * from Student where USN='"+usn+"'", null);
         
         c.moveToFirst();
         studentData = c.getString(c.getColumnIndex("USN"))+"\n"+c.getString(c.getColumnIndex("Name"))+"\n"+c.getString(c.getColumnIndex("FatherName"))+"\n"+c.getString(c.getColumnIndex("Fees"))
				  +"\n"+c.getString(c.getColumnIndex("Sem"))+"\n"+c.getString(c.getColumnIndex("Sex"))+"\n"+c.getString(c.getColumnIndex("FatherNumber"))+"\n"+c.getString(c.getColumnIndex("StudentNumber"));
				  
	}

}
