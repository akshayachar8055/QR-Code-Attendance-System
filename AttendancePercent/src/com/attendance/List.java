package com.attendance;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class List extends ListActivity {

	String key;
	ArrayList<String> lines;
	ListView lv;
	String[] items;
	static String mStudentData;
	String sem;
	String[] subs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.percentage);

		lines = new ArrayList<String>();

		Register.mSubjectVal = "ISM,ST,NS";
		subs = Register.mSubjectVal.split("\\,");

		// DisplayALert();
		sem = getIntent().getStringExtra("sem");
		Database dat = new Database(getBaseContext());
		SQLiteDatabase db = dat.getWritableDatabase();

		Cursor c = db.rawQuery("select * from Student where sem='" + sem + "'",
				null);

		if (c.getCount() != 0) {
			c.moveToFirst();
			do {
				String name = c.getString(c.getColumnIndex("Name"));
				String att = c.getString(c.getColumnIndex("Attendance1"));

				String m1 = c.getString(c.getColumnIndex("M1"));
				String m2 = c.getString(c.getColumnIndex("M2"));
				String m3 = c.getString(c.getColumnIndex("M3"));
				String m4 = c.getString(c.getColumnIndex("M4"));
				String m5 = c.getString(c.getColumnIndex("M5"));
				String m6 = c.getString(c.getColumnIndex("M6"));
				String m7 = c.getString(c.getColumnIndex("M7"));
				String m8 = c.getString(c.getColumnIndex("M8"));

				mStudentData = c.getString(c.getColumnIndex("USN")) + "\n"
						+ c.getString(c.getColumnIndex("Name")) + "\n"
						+ c.getString(c.getColumnIndex("FatherName")) + "\n"
						+ c.getString(c.getColumnIndex("Fees")) + "\n"
						+ c.getString(c.getColumnIndex("Sem")) + "\n"
						+ c.getString(c.getColumnIndex("Sex")) + "\n"
						+ c.getString(c.getColumnIndex("FatherNumber")) + "\n"
						+ c.getString(c.getColumnIndex("StudentNumber"));

				lines.add(c.getString(c.getColumnIndex("USN")) + "\n"
						+ "Name: " + c.getString(c.getColumnIndex("Name"))
						+ "\n"
						+ "Father Name: "
						+ c.getString(c.getColumnIndex("FatherName"))
						+ "\n"
						+ "Student Number: "
						+ c.getString(c.getColumnIndex("StudentNumber"))
						+ "\n"
						// + "Attendance" + "\n" + ""
						// + c.getString(c.getColumnIndex("Attendance1")) + "\n"
						+ "Marks\n" + "ISM: " + m1 + " " + "ST: " + m2 + " "
						+ "NS: " + m3 + " ACA: " + m4 + " SMS: " + m5
						+ " JAVA: " + m6 + " ADA: " + m7 + " DATABASE: " + m8);

			} while (c.moveToNext());

		}

		db.close();
		items = lines.toArray(new String[lines.size()]);

		ArrayAdapter<String> aa = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1,
				items);

		setListAdapter(aa);

	}

	@Override
	protected void onListItemClick(ListView l, android.view.View v,
			int position, long id) {

		Toast.makeText(getApplicationContext(), "list selected " + position,
				600000000).show();
		String data = lines.get(position);
		// mStudentData = data;
		String[] temp = data.split("\n");
		Intent intent = new Intent(List.this, Entermarks.class);
		intent.putExtra("usn", temp[0]);
		startActivity(intent);
	};

	public void DisplayALert() {

		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		input.setHint("Enter total class taken");
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {

				key = input.getText().toString();
				Toast.makeText(getApplicationContext(),
						"Enter total class taken" + key, 600).show();

				if (key.equals("")) {
					DisplayALert();
				} else {

					Database dat = new Database(getBaseContext());
					SQLiteDatabase db = dat.getWritableDatabase();

					Cursor c = db.rawQuery("select * from Student", null);

					if (c.getCount() != 0) {
						c.moveToFirst();
						do {
							String name = c.getString(c.getColumnIndex("Name"));
							String att = c.getString(c
									.getColumnIndex("Attendance1"));

							int attd = Integer.parseInt(att);
							int total = Integer.parseInt(key);

							float paercent = (attd / total) * 100;

							lines.add(c.getString(c.getColumnIndex("Name"))
									+ "   " + paercent + "%");

						} while (c.moveToNext());

					}

					db.close();
					items = lines.toArray(new String[lines.size()]);

					ArrayAdapter<String> aa = new ArrayAdapter<String>(
							getApplicationContext(),
							android.R.layout.simple_list_item_1, items);

					setListAdapter(aa);

				}

			}
		});

		alert.setCancelable(false);
		alert.show();

	}
}
