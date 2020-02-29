package com.example.monitory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class AlarmReceiverEnd extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                LocalDataEnd localDataEnd = new LocalDataEnd(context);
                NotificationSchedulerEnd.setReminder(context, AlarmReceiverEnd.class, localDataEnd.get_hour(), localDataEnd.get_min());
                return;
            }
        }

        Log.d(TAG, "onReceive: ");

        //Trigger the notification
        NotificationSchedulerEnd.showNotification(context, ProgramActivity.class,
                "monitory job", "go to end  job");

    }
}


