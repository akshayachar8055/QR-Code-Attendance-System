package com.attendance;



import android.app.Activity;  
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;  
import android.widget.EditText;   
import android.widget.Toast;
                

public class Student  extends Activity {

	String sem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student);
		
		String data = getIntent().getStringExtra("edit");
		
		sem = getIntent().getStringExtra("sem");
		   
		final EditText usn = (EditText) findViewById(R.id.editText1);
		final EditText name = (EditText) findViewById(R.id.editText2);
		final EditText Fname = (EditText) findViewById(R.id.editText3);
		final EditText fees = (EditText) findViewById(R.id.editText4);
		final EditText sem = (EditText) findViewById(R.id.editText5);
		final EditText sex = (EditText) findViewById(R.id.editText6);
		final EditText fnumber = (EditText) findViewById(R.id.editText7);
		final EditText snumber = (EditText) findViewById(R.id.editText8);
		final EditText bg = (EditText) findViewById(R.id.editText9);
		       
		 
		sem.setVisibility(View.GONE);
		if(data!=null){
			String[] temp = Entermarks.studentData.split("\n");
			usn.setText(temp[0]);
			name.setText(temp[1]);     
			Fname.setText(temp[2]);
			fees.setText(temp[3]);
			sem.setText(temp[4]);   
			sex.setText(temp[5]);
			fnumber.setText(temp[6]);
			snumber.setText(temp[7]);
			Student.this.sem = temp[4];  
		}else{
			     
		}
		     
		Button submit = (Button) findViewById(R.id.button1);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override   
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(usn.getText().toString().equals("")){
					
					
					Toast.makeText(getApplicationContext(), "Usn and Password should not be empty", 60000).show();
					
				}else{
					
					
					ContentValues cv = new ContentValues();
					cv.put("USN", usn.getText().toString());
					cv.put("Name", name.getText().toString());
					cv.put("FatherName", Fname.getText().toString());
					cv.put("Fees", fees.getText().toString());
					  
					cv.put("Sem", Student.this.sem);      
					cv.put("Sex", sex.getText().toString());
					cv.put("FatherNumber", fnumber.getText().toString());
					cv.put("StudentNumber", snumber.getText().toString());
					cv.put("BloodGroup", bg.getText().toString());
					cv.put("Attendance1", "JAVA 0 0");  
					//cv.put("Password", password.getText().toString());
					
					Database dat = new Database(getBaseContext());
					SQLiteDatabase db = dat.getWritableDatabase();   
					
					             
					Cursor c = db.rawQuery("select * from Student where USN='"+usn.getText().toString()+"'", null);
					if(c.getCount()!=0){
						          
						db.update("Student", cv, "USN='"+usn.getText().toString()+"'", null);
						Toast.makeText(getApplicationContext(), "Usn already exists, Date Updated.", 6000).show();
						      
					}else{   
						
						if(fnumber.getText().toString().length()<10||snumber.getText().toString().length()<10||usn.getText().toString().length()<5){
							
							Toast.makeText(getApplicationContext(), "Either father no or student no or usn should not be less than 10 characters", Toast.LENGTH_LONG).show();
						}else{
						     
							db.insert("Student", null, cv);
							Toast.makeText(getApplicationContext(), "Student inserted", 6000).show();
							alertDialogtwoButton(); 
						}
						      
					}   
					c.close();
				
					db.close();
				}
				
				 
				
			}
		});
		
	}

	

public void alertDialogtwoButton(){
		
		final AlertDialog alertDialog = new AlertDialog.Builder(this)
			.create();
		alertDialog.setTitle("Warning!");
			alertDialog.setMessage("Would like to register another Student");
			alertDialog.setButton("YES", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
					Intent intent = new Intent(Student.this,Student.class);
					startActivity(intent);
				
					  
				}
			});
			alertDialog.setButton2("NO", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
                      
        
        	 finish();
        	  
         	
            }
        });
			
			alertDialog.show();
		
	}
}
