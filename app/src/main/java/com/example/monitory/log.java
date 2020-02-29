package com.example.monitory;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
public class log extends AppCompatActivity {
    ClipboardManager myClipboard;
    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;
    Button login ;
    LocalData localData;
    LocalDataEnd localDataEnd;
    ConnectionDetector cd;
    int hour, min,hourEnd,minEnd;
    Boolean isInternetPresent = false;
    public static String MY_PREFS_NAME= "nameOfSharedPreferences";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad  = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_log);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        startAlarm(true,true);
//        EndAlarm(true,true);
//        localDataEnd= new LocalDataEnd(getApplicationContext());
//        localDataEnd.set_hour(1);
//        localDataEnd.set_min(20);
//        NotificationSchedulerEnd.setReminder(log.this, AlarmReceiverEnd.class, localDataEnd.get_hour(), localDataEnd.get_min());
//
//        localData = new LocalData(getApplicationContext());
//        localData.set_hour(1);
//        localData.set_min(22);
//        NotificationScheduler.setReminder(log.this, AlarmReceiver.class, localData.get_hour(), localData.get_min());

        if(!internet()){showAlertInternet();

            Toast.makeText(getApplicationContext(), "internet111", Toast.LENGTH_LONG).show();

        }
        localData = new LocalData(getApplicationContext());
        localDataEnd = new LocalDataEnd(getApplicationContext());

        hour = localData.get_hour();
        min = localData.get_min();
        hourEnd = localDataEnd.get_hour();
        minEnd = localDataEnd.get_min();

       mAuth = FirebaseAuth.getInstance();




        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        login=(Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!internet()){showAlertInternet();



                }
                NotificationScheduler.setReminder(log.this, AlarmReceiver.class, localData.get_hour(), localData.get_min());
                NotificationSchedulerEnd.setReminder(log.this, AlarmReceiverEnd.class, localDataEnd.get_hour(), localDataEnd.get_min());
                userLogin();
//                finish();
//                // String value=mAuth.getCurrentUser().getEmail();
//                Intent intent = new Intent(log.this, Home.class);
//                //intent.putExtra("emailUser",value);
//                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                startActivity(intent);

                //startAlarm(true,true);

                //startAlarm(true,true);

//01006769789 saf
/*
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 14);
                calendar.set(Calendar.MINUTE, 10);
                calendar.set(Calendar.SECOND, 0);

                Intent alertIntent = new Intent(getApplicationContext(), AlertReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE );

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY , PendingIntent.getBroadcast(getApplicationContext(), 0, alertIntent,
               PendingIntent.FLAG_UPDATE_CURRENT ));

                //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(getApplicationContext(), 0, alertIntent,
                  //      PendingIntent.FLAG_UPDATE_CURRENT ));


 */
/*
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.set(Calendar.HOUR_OF_DAY, 17);
                calendarEnd.set(Calendar.MINUTE, 54);
                calendarEnd.set(Calendar.SECOND, 0);


                Intent alertIntentEnd = new Intent(getApplicationContext(), AlertReceiver.class);
                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService( ALARM_SERVICE );

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES, PendingIntent.getBroadcast(getApplicationContext(), 0, alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT ));

 */




            }

        });

        //findViewById(R.id.textViewSignup).setOnClickListener(this);
       // findViewById(R.id.buttonLogin).setOnClickListener((View.OnClickListener) this);

    }


    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 7) {
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    // String value=mAuth.getCurrentUser().getEmail();
                    Intent intent = new Intent(log.this, Home.class);
                    //intent.putExtra("emailUser",value);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
/*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }
*/




    @Override
    protected void onStart() {
        super.onStart();
//هنا بيخليني بخش على طول


        /*
        localDataEnd= new LocalDataEnd(getApplicationContext());
        localDataEnd.set_hour(1);
        localDataEnd.set_min(34);
        NotificationSchedulerEnd.setReminder(log.this, AlarmReceiverEnd.class, localDataEnd.get_hour(), localDataEnd.get_min());

        localData = new LocalData(getApplicationContext());
        localData.set_hour(1);
        localData.set_min(35);
        NotificationScheduler.setReminder(log.this, AlarmReceiver.class, localData.get_hour(), localData.get_min());


         */

        if (mAuth.getCurrentUser() != null) {
            finish();
            //String value=mAuth.getCurrentUser().getEmail();
            Intent i = new Intent(log.this, Home.class);
            // i.putExtra("emailUser",value);
            //Toast.makeText(getApplicationContext(),"eeee: "+value, Toast.LENGTH_SHORT).show();
            startActivity(i);
            //Intent intent = new Intent(MainActivity.this, job.class);

            //intent.putExtra("emailUser", mAuth.getCurrentUser().getEmail());
            // startActivity(new Intent(this, MainActivity.class));
        }


    }






    private boolean internet() {
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {

            return true;
        }
        else {
            //System.out.println("Internet Connection Not Present");
            return false;

        }
    }

    private void showAlertInternet() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable internet")
                .setMessage("Your internet Settings is set to 'Off'.\nPlease Enable internet to " +
                        "use this app")
                .setPositiveButton("internet Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

}
