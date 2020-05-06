package com.attendance;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class View_student extends ListActivity{
 
	String key;
	 ArrayList<String> lines;
	 ListView lv;
	 String[] items;
	
	 @Override 
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.percentage);
	        
	        lines = new ArrayList<String>();
	        
	        DisplayALert();
	        
	 }
	
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
