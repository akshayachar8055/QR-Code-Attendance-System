package com.attendance;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AndroidTimeActivity extends Activity {
 
 TimePicker myTimePicker;
 Button buttonstartSetDialog;
 TextView textAlarmPrompt;
 
 TimePickerDialog timePickerDialog;
    
     
 int count=0;  
 EditText et,et2;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timelayout);   
                    
        et = (EditText) findViewById(R.id.editText1);   
        et2 = (EditText) findViewById(R.id.name);
        
        
          
        
        
        textAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);
        
        buttonstartSetDialog = (Button)findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		   
        		textAlarmPrompt.setText("");
        		openTimePickerDialog(false);
        		
        	}
        });

     //   Managetime();
    	}   

  
    private void openTimePickerDialog(boolean is24r){
    	Calendar calendar = Calendar.getInstance();
  
    	timePickerDialog = new TimePickerDialog(
    			AndroidTimeActivity.this, 
    			onTimeSetListener, 
    			calendar.get(Calendar.HOUR_OF_DAY), 
    			calendar.get(Calendar.MINUTE), 
    			is24r);
    	timePickerDialog.setTitle("Set Alarm Time");  
        
    	timePickerDialog.show();

    }
    
       
  
    
    OnTimeSetListener onTimeSetListener
    = new OnTimeSetListener(){
      
    	@Override
    	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    		Calendar cal = Calendar.getInstance();
    		
    		Date date = new Date();
    		int hour = date.getHours();
    		int min = date.getMinutes();
    		Calendar calNow = Calendar.getInstance();
    		Calendar calSet = (Calendar) calNow.clone();
    		
    		calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
    		calSet.set(Calendar.MINUTE, minute);  
    		calSet.set(Calendar.SECOND, 0);
    		calSet.set(Calendar.MILLISECOND, 0);        
        
    		Toast.makeText(getApplicationContext(), "alarm time is "+hourOfDay+" "+minute, 6000).show();
    		Toast.makeText(getApplicationContext(), "alarm time is "+hour+" "+min, 6000).show();
//    		if(calSet.compareTo(calNow) <= 0){   
//    			//Today Set time passed, count to tomorrow     
//    			calSet.add(Calendar.DATE, 1);
//    		}   
                   
    		      
    		setAlarm(calSet);
    	}
    	};
    
  	private void setAlarm(Calendar targetCal){

  		textAlarmPrompt.setText(
  				"\n\n***\n"
  						+ "Alarm is set@ " + targetCal.getTime() + "\n"
  						+ "***\n");
     
  		count++;  
  		   
  		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
  		intent.putExtra("text", et.getText().toString());
  		
               
  		          
  		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), count, intent, 0);
  		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
  		alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
  		

		        
		
  	} 
  
}
