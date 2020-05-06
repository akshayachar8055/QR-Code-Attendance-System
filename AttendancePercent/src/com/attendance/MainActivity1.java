package com.attendance;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity1 extends Activity {

	EditText mSem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attdbackup);
		          
        
		
		mSem = (EditText) findViewById(R.id.sem);
		
		Button btn = (Button) findViewById(R.id.button1);
		Button btn1 = (Button) findViewById(R.id.button2);
		Button btn2 = (Button) findViewById(R.id.button3);
		Button btn3 = (Button) findViewById(R.id.button4);
		
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub   
				
				Intent intent = new Intent(MainActivity1.this,AndroidTimeActivity.class);
				startActivity(intent);
			}  
		});
		               
		                   
		
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {   
				// TODO Auto-generated method stub
				

				if(mSem.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Please enter sem", 60000).show();
				}else{
					Intent intent = new Intent(MainActivity1.this,List.class);
					intent.putExtra("sem", mSem.getText().toString());
					startActivity(intent);
				}
			}
		});
		           
		   
		btn.setOnClickListener(new OnClickListener() {
			
			@Override  
			public void onClick(View arg0) { 
				// TODO Auto-generated method stub
				  
				
				if(mSem.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Please enter sem", 60000).show();
				}else{
					Intent intent = new Intent(MainActivity1.this,Student.class);
					intent.putExtra("sem", mSem.getText().toString());
					startActivity(intent);
					
				}
				
			}
		});
		   
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub   
				if(mSem.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Please enter sem", 60000).show();
				}else{
				     
					Intent intent = new Intent(MainActivity1.this,AttendanceschoolActivity.class);
					intent.putExtra("sem", mSem.getText().toString());
					startActivity(intent);
				}
			}
		});
		
	}


}
