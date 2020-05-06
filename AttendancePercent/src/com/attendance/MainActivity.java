package com.attendance;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		     
 
		
		Button btn = (Button) findViewById(R.id.button1);
		Button btn1 = (Button) findViewById(R.id.button2);
		Button btn2 = (Button) findViewById(R.id.button3);
		Button btn3 = (Button) findViewById(R.id.reminder);
		Button broad = (Button) findViewById(R.id.broadcast);
		Button leave = (Button) findViewById(R.id.button5);
		
		leave.setVisibility(View.GONE);
		leave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,Leave.class);
				startActivity(intent);
			}
		});   
		
		   
		broad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,BroadcastMemo.class);
				startActivity(intent);
			}
		});
		  
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,AndroidTimeActivity.class);
				startActivity(intent);
			}
		});
		     
		         
		
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,List.class);
					startActivity(intent);
			}
		});
		           
		   
		btn.setOnClickListener(new OnClickListener() {
			
			@Override  
			public void onClick(View arg0) { 
				// TODO Auto-generated method stub
				
				
				Intent intent = new Intent(MainActivity.this,Student.class);
				startActivity(intent);
				
			}
		});
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub   
				   
				
				Intent intent = new Intent(MainActivity.this,MainActivity1.class);
				startActivity(intent);
				   
			}
		});
		
	}


}
