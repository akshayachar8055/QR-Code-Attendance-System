package com.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class AttendanceschoolActivity extends ListActivity {
    /** Called when the activity is first created. */
	   
	 ArrayList<String> lines,Unslist;
	 ListView lv;
	 String[] items;
	 List<String> uncheck;
	static final int DATE_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 2;
	static private int year=2013, month=0, day=27;
	private int hours=3, min=00,a;
	                     
	String dateformat;
	EditText subcode;      
	                     
	String[] sub = {"ISM","ST","NS"};
	String subject = "";
	String semVal;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        semVal = getIntent().getStringExtra("sem");
        uncheck = new ArrayList<String>();
        lines = new ArrayList<String>();  
        Unslist = new ArrayList<String>();  
        lv = getListView();  
           
        sub = Register.mSubjectVal.split("\\,");
           
        Calendar cal = Calendar.getInstance();   
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);   
        
        dateformat = year+"-"+month+"-"+day;
      //  ListView lv = (ListView) findViewById(R.id.lt);
        Button btn = (Button) findViewById(R.id.sub);
        Button date = (Button) findViewById(R.id.date);
        subcode = (EditText) findViewById(R.id.subcode);
        Spinner spin = (Spinner) findViewById(R.id.spin);
        
        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sub);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa1);    
        
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				subject = sub[arg2];
			}    
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        		
        date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID); 
			}
		});
        
       btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Toast.makeText(getApplicationContext(), "Attendance taken successfully", 600000).show();
			  SparseBooleanArray checked = lv.getCheckedItemPositions();
			  
			  Database dat = new Database(getBaseContext());
	          SQLiteDatabase db = dat.getWritableDatabase();
	                
			         
			  for(int i=0; i<items.length; i++) {
				  String usn = Unslist.get(i);
				   
	        	  
				  if(subject.equalsIgnoreCase(sub[0])){
					  Cursor c = db.rawQuery("select * from Student where USN='"+usn+"'", null);
					  c.moveToFirst();
					  String data = c.getString(c.getColumnIndex("Attendance1"));
					  String no = c.getString(c.getColumnIndex("FatherNumber"));
					  
					  String[] temp = data.split(" ");
					  int attended = Integer.parseInt(temp[2]);
					  int total = Integer.parseInt(temp[1]);
					  total++;
					  
					 
					  int percentbef;
					  int percent ;
					  if(!checked.get(i)){
						  attended++;
						  percentbef = attended*100;
						  percent = percentbef/total;
						  
						 
					  }else{
						  percentbef = attended*100;
						  percent = percentbef/total;
						  SmsManager sm = SmsManager.getDefault();
						  sm.sendTextMessage(no, null, "Your ward is absent for today's "+subject+" class, total percent is: "+percent+"% Minimum should be 75%", null, null);
						    
					  }        
					  
//					  int percentbef = attended*100;
//					  int percent = percentbef/total;
					        
					  String finaldata = subject+" "+total+" "+attended+" "+percent+"%";
					  ContentValues cv = new ContentValues();
		        	  cv.put("Attendance1", finaldata);
		        	  db.update("Student", cv, "USN='"+usn+"'", null);
		        	  c.close();                       
					  
		        	       
				  }else if(subject.equalsIgnoreCase(sub[1])){      
					  Cursor c = db.rawQuery("select * from Student where USN='"+usn+"'", null);
					  c.moveToFirst();
					  String data = c.getString(c.getColumnIndex("Attendance2"));
					  String no = c.getString(c.getColumnIndex("FatherNumber"));
					  String[] temp = data.split(" ");
					  int attended = Integer.parseInt(temp[2]);
					  int total = Integer.parseInt(temp[1]);
					  total++;
					  
					   
					  int percentbef;
					  int percent ;
					  if(!checked.get(i)){
						  attended++;
						  percentbef = attended*100;
						  percent = percentbef/total;
					  }else{  
						  percentbef = attended*100;
						  percent = percentbef/total;
						  SmsManager sm = SmsManager.getDefault();
						  sm.sendTextMessage(no, null, "Your ward is absent for today's "+subject+" class, total percent is: "+percent+"% Minimum should be 75%", null, null);
			         }        
//					  int percentbef = attended*100;
//					  int percent = percentbef/total;  
					  
					  String finaldata = subject+" "+total+" "+attended+" "+percent+"%";
					  ContentValues cv = new ContentValues();
		        	  cv.put("Attendance2", finaldata);
		        	  db.update("Student", cv, "USN='"+usn+"'", null);   
		        	  c.close();                 
					
		        	         
				  }else if(subject.equalsIgnoreCase(sub[2])){
					  
					  Cursor c = db.rawQuery("select * from Student where USN='"+usn+"'", null);
					  c.moveToFirst();
					  String data = c.getString(c.getColumnIndex("Attendance3"));
					  String no = c.getString(c.getColumnIndex("FatherNumber"));   
					  String[] temp = data.split(" ");
					  int attended = Integer.parseInt(temp[2]);
					  int total = Integer.parseInt(temp[1]);
					  total++;        
					  
					  int percentbef;
					  int percent;
					  if(!checked.get(i)){    
						  attended++;  
						  percentbef = attended*100;
						  percent = percentbef/total;
					  }else{ 
						  percentbef = attended*100;
						  percent = percentbef/total;
						  SmsManager sm = SmsManager.getDefault();
						  sm.sendTextMessage(no, null, "Your ward is absent for today's "+subject+" class, total percent is: "+percent+"% Minimum should be 75%", null, null);
			         }
//					  int percentbef = attended*100;   
//					  int percent = percentbef/total;
					  String finaldata = subject+" "+total+" "+attended+" "+percent+"%";
					  ContentValues cv = new ContentValues();
		        	  cv.put("Attendance3", finaldata);
		        	  db.update("Student", cv, "USN='"+usn+"'", null);
		        	  c.close();
		        	
				  }
				  	
		}   	
			  db.close();
			  finish();
		}
	});
       
       Database dat = new Database(getBaseContext());
       SQLiteDatabase db = dat.getWritableDatabase();
       
       Cursor c = db.rawQuery("select * from Student where sem='"+semVal+"'", null);
       
       if(c.getCount()!=0){
    	   c.moveToFirst();  
    	   do{
    		      
    		   lines.add(c.getString(c.getColumnIndex("Name")));
    		   Unslist.add(c.getString(c.getColumnIndex("USN")));
    		   
    	   }while(c.moveToNext());
    	             
       }   
        
        db.close();
        items = lines.toArray(new String[lines.size()]);
        
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice,items);
      
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        setListAdapter(aa);
             
        
    }
    
    private DatePickerDialog.OnDateSetListener dateListener = 
 			new DatePickerDialog.OnDateSetListener() {


				@Override
				public void onDateSet(DatePicker view, int yr, int monthOfYear,
 						int dayOfMonth) {
					// TODO Auto-generated method stub
					
					
					year = yr;
 					month = monthOfYear+1;
 					day = dayOfMonth;
 					Toast.makeText(getApplicationContext(), "the month"+month, 600).show();
 					//updateDate();
				}
 		};
	
	protected Dialog onCreateDialog(int id){
		switch(id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateListener, year, month, day);
		
		}
		return null;
		
	}
}