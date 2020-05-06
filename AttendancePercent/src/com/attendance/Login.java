package com.attendance;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	Button b1, b2;
	EditText e1, e2;
	EditText e3;
	Socket ss;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		e1 = (EditText) findViewById(R.id.editText1);
		e2 = (EditText) findViewById(R.id.editText2);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);

		// e1.setText("daya");
		// e2.setText("daya");
		// TelephonyManager telephonyManager =
		// (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		// String imei = telephonyManager.getDeviceId();

		// if((!imei.contains("911304153844521"))){
		// TextView tv=null;
		// tv.setText("");
		// }
		//
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// MatchUser();

				String username = e1.getText().toString();
				String pwd = e2.getText().toString();

				if (username.equals("admin") && pwd.equals("admin")) {
					Intent intent = new Intent(Login.this, MainActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(),
							"Invalid credentials", 6000).show();
				}
			}
		});

		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Login.this, MainActivity.class);
				startActivity(intent);
			}
		});

	}

	public void MatchUser() {

		Database dat = new Database(getBaseContext());
		SQLiteDatabase db = dat.getWritableDatabase();

		Cursor c = db.rawQuery("select * from Faculty where Username='"
				+ e1.getText().toString() + "' and Password='"
				+ e2.getText().toString() + "'", null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			Toast.makeText(getApplicationContext(), "Login successfull.", 6000)
					.show();
			finish();
			Register.mSubjectVal = c.getString(c.getColumnIndex("Subject"));
			Intent intent = new Intent(Login.this, MainActivity.class);
			startActivity(intent);

		} else {
			Toast.makeText(getApplicationContext(), "Invalid credentials", 6000)
					.show();
		}
		c.close();
		db.close();

	}

}
