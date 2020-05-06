package com.attendance;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BroadcastMemo extends ListActivity{
 
	String key;
	 ArrayList<String> lines;
	 ListView lv;
	 String[] items;
	 EditText mMessage;
	 Button mSubmit;
	 @Override 
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.broadpercentage);
	        
	        mMessage = (EditText) findViewById(R.id.message);
	        mSubmit = (Button) findViewById(R.id.sub);
	        
	        mSubmit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Database dat = new Database(getBaseContext());
		            SQLiteDatabase db = dat.getWritableDatabase();
		               
		            Cursor c = db.rawQuery("select * from Student", null);
		            SmsManager sm = SmsManager.getDefault();
		            if(c.getCount()!=0){
		         	   c.moveToFirst();
		         	   do{ 
		         		     
		         		   String mobile = c.getString(c.getColumnIndex("StudentNumber"));
		         		   sm.sendTextMessage(mobile, null, mMessage.getText().toString(), null, null);
		         		   
		         	   }while(c.moveToNext());
		         	                
		            }             
		                       
		             db.close();
				}
			});
	        lines = new ArrayList<String>();
	        
	       // DisplayALert();   
	           
	        Database dat = new Database(getBaseContext());
            SQLiteDatabase db = dat.getWritableDatabase();
               
            Cursor c = db.rawQuery("select * from Student", null);
            
            if(c.getCount()!=0){
         	   c.moveToFirst();
         	   do{  
         		   String name = c.getString(c.getColumnIndex("Name"));
         		   String att = c.getString(c.getColumnIndex("Attendance1"));
         		      
         		     
         		   String m1 = c.getString(c.getColumnIndex("M1"));
         		   String m2 = c.getString(c.getColumnIndex("M2"));
         		   String m3 = c.getString(c.getColumnIndex("M3"));
         		   lines.add(c.getString(c.getColumnIndex("USN"))+"\n"+c.getString(c.getColumnIndex("Name"))+"\n"+c.getString(c.getColumnIndex("FatherName"))+"\n"+c.getString(c.getColumnIndex("FatherNumber"))+"\n"+c.getString(c.getColumnIndex("StudentNumber"))
         				  +c.getString(c.getColumnIndex("Attendance1"))+c.getString(c.getColumnIndex("Attendance2"))+c.getString(c.getColumnIndex("Attendance3"))+"\n"+m1+" "+m2+" "+m3);
         		                  
         	   }while(c.moveToNext());
         	                
            }             
                       
             db.close();   
             items = lines.toArray(new String[lines.size()]);
             
             ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,items);
           
                 
             setListAdapter(aa);
     	          
	                    
	        
	 }
	 
	 @Override
	 protected void onListItemClick(ListView l, android.view.View v, int position, long id) {
		 
		 Toast.makeText(getApplicationContext(), "list selected "+position, 600000000).show();
		 String data = lines.get(position);
		 String[] temp = data.split("\n");
		 Intent intent = new Intent(BroadcastMemo.this,Entermarks.class);
		 intent.putExtra("usn", temp[0]);
		 startActivity(intent);
	 };
	
	  public void DisplayALert(){
	    	
	    	final AlertDialog.Builder alert = new AlertDialog.Builder(this);
	        final EditText input = new EditText(this); 
	        input.setHint("Enter total class taken");
	        alert.setView(input);
	        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	
	               key = input.getText().toString();
	               Toast.makeText(getApplicationContext(), "Enter total class taken"+key, 600).show();
	               
	               if(key.equals("")){
	            	   DisplayALert();
	               }else{
	            	      
	            	      
	            	   Database dat = new Database(getBaseContext());
	                   SQLiteDatabase db = dat.getWritableDatabase();
	                   
	                   Cursor c = db.rawQuery("select * from Student", null);
	                   
	                   if(c.getCount()!=0){
	                	   c.moveToFirst();
	                	   do{
	                		   String name = c.getString(c.getColumnIndex("Name"));
	                		   String att = c.getString(c.getColumnIndex("Attendance1"));
	                		      
	                		   int attd = Integer.parseInt(att);
	                		   int total = Integer.parseInt(key);
	                		        
	                		   float paercent = (attd/total) * 100; 
	                		   
	                		   lines.add(c.getString(c.getColumnIndex("Name"))+"   "+paercent+"%");
	                		            
	                	   }while(c.moveToNext());
	                	             
	                   }     
	                           
	                    db.close();
	                    items = lines.toArray(new String[lines.size()]);
	                    
	                    ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,items);
	                  
	                     
	                    setListAdapter(aa);
	            	   
	            	   
	               }
	                  
	                  
	            }
	        });

	       alert.setCancelable(false);
	       alert.show();

	    }
}
