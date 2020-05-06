package com.attendance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{

	public static final int DATABASE_VERSION=1;
	public static final String DATABASE_NAME="School.db";
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		final String Student=
                "CREATE TABLE Student" + 
                         "(_id integer primary key autoincrement"
                        + ",USN  TEXT " +
                        ", Name TEXT" +
                        ", FatherName TEXT" +
                        ", Fees TEXT" +
                        ", M1 TEXT" +
                        ", M2 TEXT" +
                        ", M3 Text" +
                        ", M4 Text" +
                        ", M5 Text" +
                        ", M6 Text" +
                        ", M7 Text" +
                        ", M8 Text" +
                        
                        ", Sem TEXT" +
                        ", Sex TEXT" +
                        ", FatherNumber TEXT" +
                        ", StudentNumber TEXT" +
                        ", Attendance1 TEXT DEFAULT \'0 0 0\'" +
                        ", Attendance2 TEXT DEFAULT \'0 0 0\'" +
                        ", Attendance3 TEXT DEFAULT \'0 0 0\'" +
                        ", BloodGroup TEXT DEFAULT \'0 0 0\'" +
                        ", Password TEXT)";
		  db.execSQL(Student);   
		      
		 final String Faculty =
                "CREATE TABLE Faculty" + 
                         "(_id integer primary key autoincrement"
                        + ", Username TEXT" +
                        ", Name TEXT" +
                        ", MobileNo TEXT" +
                        ", Subject TEXT" +
                        ", Password TEXT" +
                        ")";
		 
        db.execSQL(Faculty);
        
      
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	

}
