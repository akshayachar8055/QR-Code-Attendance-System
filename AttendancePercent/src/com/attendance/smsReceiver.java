package com.attendance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.sax.StartElementListener;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class smsReceiver extends BroadcastReceiver {

	String incomingno;
	LocationManager locationManager;

	SharedPreferences sharedPrefs;
	double lat, lon;

	Context cntxt;
	String pwd, no1, no2, no3;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		cntxt = context;

		SmsManager sm;
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null) {
			sm = SmsManager.getDefault();

			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				incomingno = msgs[i].getOriginatingAddress();
				String body = msgs[i].getMessageBody().toString();

				Toast.makeText(context, "no message", 6000000).show();
				String[] temp1 = body.split("\\:");
				if (temp1.length > 1) {
					updateAttendance(body);
				}

				if (body.contains("atd")) {

					String[] temp = body.split(" ");

					Database dat = new Database(cntxt);
					SQLiteDatabase db = dat.getWritableDatabase();

					Cursor c = db.rawQuery("select * from Student where USN='"
							+ temp[1] + "'", null);

					if (c.getCount() != 0) {

						c.moveToFirst();

						String a1 = c
								.getString(c.getColumnIndex("Attendance1"));

						String combined = "The no of classes you attended is "
								+ a1;

						sm.sendTextMessage(incomingno, null, "atd " + combined,
								null, null);

					} else {

					}
					c.close();
					db.close();

				}
				if (body.contains("access")) {

					Toast.makeText(cntxt, "inside ", 600).show();
					final String[] temp = body.split(" ");
					if (temp.length == 3) {
						FileSearcher fs = new FileSearcher();
						final List<File> matches = fs.searchFullSDCard(temp[1]);
						System.out.println(""
								+ matches.get(0).getAbsolutePath());
						sm.sendTextMessage(incomingno, null,
								"file found sending to id please check!", null,
								null);
						new Thread() {
							@Override
							public void run() {
								try {
									GMailSender sender = new GMailSender(
											"dayadad@gmail.com",
											"achievement123!@#");
									sender.sendMail(
											"from School app",
											"mail from School app for receiving notes",
											"dayadad@gmail.com", temp[2],
											matches, "");

								} catch (Exception e) {
									Log.e("SendMail", e.getMessage(), e);
								}
							}
						}.start();

					}
				}

				if (body.contains("marks")) {

					String[] temp = body.split(" ");
					Database dat = new Database(cntxt);
					SQLiteDatabase db = dat.getWritableDatabase();

					Cursor c = db.rawQuery("select * from Student where USN='"
							+ temp[1] + "'", null);

					if (c.getCount() != 0) {

						c.moveToFirst();
						int m1 = Integer.parseInt(c.getString(c
								.getColumnIndex("M1")));
						int m2 = Integer.parseInt(c.getString(c
								.getColumnIndex("M2")));
						int m3 = Integer.parseInt(c.getString(c
								.getColumnIndex("M3")));
						int m4 = Integer.parseInt(c.getString(c
								.getColumnIndex("M4")));
						int m5 = Integer.parseInt(c.getString(c
								.getColumnIndex("M5")));
						int m6 = Integer.parseInt(c.getString(c
								.getColumnIndex("M6")));
						int m7 = Integer.parseInt(c.getString(c
								.getColumnIndex("M7")));
						int m8 = Integer.parseInt(c.getString(c
								.getColumnIndex("M8")));

						/*
						 * String a1 = c
						 * .getString(c.getColumnIndex("Attendance1")); String
						 * a2 = c .getString(c.getColumnIndex("Attendance2"));
						 * String a3 = c
						 * .getString(c.getColumnIndex("Attendance3"));
						 */
						String name = c.getString(c.getColumnIndex("Name"));

						int average = (m1 + m2 + m3 + m4 + m5 + m6 + m7 + m8) / 8;
						
						String combined = "Your ward info Name: " + name
								+ "\nISM: " + m1 + "\nST: " + m2 + "\nNS: " + m3
								+ "\nACA: " + m4 + "\nSMS: " + m5 + "\nJAVA: " + m6
								+ "\nADA: " + m7 + "\nDATABASE: " + m8 +  "\nPecentage: "+average;
						
						Toast.makeText(context, ""+combined, 60000).show();
						System.out.println("combined: "+combined);
						sm.sendTextMessage(incomingno, null, "marks#"
								+ combined, null, null);

					} else {

					}
					c.close();
					db.close();
				}
			}
		}
	}

	public void updateAttendance(String body) {
		Database dat = new Database(cntxt);
		SQLiteDatabase db = dat.getWritableDatabase();

		Cursor c = db.rawQuery("select * from Student", null);
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				String usn = c.getString(c.getColumnIndex("USN"));
				String attd = c.getString(c.getColumnIndex("Attendance1"));
				String fatherno = c.getString(c.getColumnIndex("FatherNumber"));
				String[] attds = attd.split(" ");

				if (attd == null || attd.equals("")) {
					attd = "0";
				}

				int att = Integer.parseInt(attds[2]);
				int total = Integer.parseInt(attds[1]);
				total++;
				ContentValues cv = new ContentValues();

				if (body.contains(usn)) {
					att++;
					String finaldata = "JAVA " + total + " " + att;
					cv.put("Attendance1", finaldata);
				} else {

					String finaldata = "JAVA " + total + " " + att;
					cv.put("Attendance1", finaldata);
					SmsManager sms = SmsManager.getDefault();
					// sms.sendTextMessage(fatherno, null,
					// "Your ward no absent for todays java class", null, null);
				}
				db.update("Student", cv, "USN='" + usn + "'", null);

			} while (c.moveToNext());

		}
		c.close();
		db.close();

	}
}
