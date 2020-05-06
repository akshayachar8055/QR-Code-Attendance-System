package com.attendance;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		Toast.makeText(arg0, "Alarm received! "+arg1.getStringExtra("text"), Toast.LENGTH_LONG).show();
  
        
		Intent intent = new Intent(arg0,MyAlarmService.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("text", arg1.getStringExtra("text"));
		arg0.startService(intent);   

          
      

	}

}


