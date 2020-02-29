package com.example.monitory;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jaison on 18/06/17.
 */

public class LocalDataEnd {

    private static final String APP_SHARED_PREFS = "RemindMePrefEnd";

    private SharedPreferences appSharedPrefsEnd;
    private SharedPreferences.Editor prefsEditorEnd;

    private static final String reminderStatus="reminderStatus";
    private static final String hour="hour";
    private static final String min="min";

    public LocalDataEnd(Context context)
    {
        this.appSharedPrefsEnd = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.prefsEditorEnd = appSharedPrefsEnd.edit();
    }

    // Settings Page Set Reminder

    public boolean getReminderStatus()
    {
        return appSharedPrefsEnd.getBoolean(reminderStatus, true);
    }

    public void setReminderStatus(boolean status)
    {
        prefsEditorEnd.putBoolean(reminderStatus, status);
        prefsEditorEnd.commit();
    }

    // Settings Page Reminder Time (Hour)

    public int get_hour()
    {
        return appSharedPrefsEnd.getInt(hour, 15);
    }

    public void set_hour(int h)
    {
        prefsEditorEnd.putInt(hour, h);
        prefsEditorEnd.commit();
    }

    // Settings Page Reminder Time (Minutes)

    public int get_min()
    {
        return appSharedPrefsEnd.getInt(min, 17);
    }

    public void set_min(int m)
    {
        prefsEditorEnd.putInt(min, m);
        prefsEditorEnd.commit();
    }

    public void reset()
    {
        prefsEditorEnd.clear();
        prefsEditorEnd.commit();

    }

}
