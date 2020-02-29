package com.example.monitory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
public class Home extends AppCompatActivity {
ImageView bgapp;
    Animation frombottom,bganim,forimage,date,dateH;
LinearLayout menus,submenus,submenus2,text;
TextView day ;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    int height ;
    int width ;
    int dens;
    double distance;
    int currentDay ;
    int currentMonth ;
    int currentYear ;
    int currentDayOfWeek ;
    int currentDayOfMonth ;
    int CurrentDayOfYear ;
    String Day,m;
    Button Button,Button2;
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
        setContentView(R.layout.activity_home);
        if(!internet()){showAlertInternet();

            Toast.makeText(getApplicationContext(), "internet111", Toast.LENGTH_LONG).show();

        }

        day  = (TextView)findViewById(R.id.day);


        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentTime = localCalendar.getTime();
        currentDay = localCalendar.get(Calendar.DATE);
        currentMonth = localCalendar.get(Calendar.MONTH) + 1;
        currentYear = localCalendar.get(Calendar.YEAR);
        currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
        currentDayOfMonth = localCalendar.get(Calendar.DAY_OF_MONTH);
        CurrentDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
        Day = getNameOfDay(currentYear, CurrentDayOfYear);
        m = theMonth(currentMonth);
        day.setText(currentDayOfMonth +" "+m);
        menus=(LinearLayout)findViewById(R.id.menus);
        text=(LinearLayout)findViewById(R.id.text);
        submenus=(LinearLayout)findViewById(R.id.submenus);
        submenus2=(LinearLayout)findViewById(R.id.submenus2);

        bgapp =(ImageView) findViewById(R.id.bgapp);
       // Button=(Button)findViewById(R.id.button);
       // Button2=(Button)findViewById(R.id.button2);

        frombottom= (Animation) AnimationUtils.loadAnimation(this,R.anim.frombottom);
        bganim= (Animation) AnimationUtils.loadAnimation(this,R.anim.bganim);
        date= (Animation) AnimationUtils.loadAnimation(this,R.anim.date);
        dateH= (Animation) AnimationUtils.loadAnimation(this,R.anim.dateh);

        forimage= (Animation) AnimationUtils.loadAnimation(this,R.anim.forimage);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape

            getScreenDimension();
            distance =width*.95;
            day.animate().translationX((-35) ).setDuration(1).setStartDelay(1);
            bgapp.animate().translationY((float) -distance).setDuration(800).setStartDelay(500);
            day.startAnimation( date);
            day.animate().translationY((96) ).setDuration(800).setStartDelay(1200);


           // day.startAnimation( dateH);
            //day.setTranslationY(95);
            //bgapp.setTranslationY(-900);
        } else {
            // In portrait
            getScreenDimension();
            day.startAnimation( date);


        }
        bgapp.animate().translationY((float) -distance).setDuration(800).setStartDelay(500);
        text.startAnimation( frombottom);
        menus.startAnimation( bganim);
        //getScreenDimension();
        //bgapp.animate().translationY((float) -distance).setDuration(800).setStartDelay(500);
       // menus.animate().translationY(-1100).setDuration(800).setStartDelay(1500);
        //text.animate().translationY(-1800).setDuration(800).setStartDelay(1500);
        //text.animate().translationX(-200).setDuration(800);


       // bgapp.startAnimation( forimage);
        //menus.animate().alpha((float) 0.0).setStartDelay(100);
        submenus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();

                Intent i = new Intent(Home.this, MainActivity.class);
                // i.putExtra("emailUser",value);
                //Toast.makeText(getApplicationContext(),"eeee: "+value, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

        submenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();
                //String value=mAuth.getCurrentUser().getEmail();
                Intent i = new Intent(Home.this, reportJob.class);
                // i.putExtra("emailUser",value);
                //Toast.makeText(getApplicationContext(),"eeee: "+value, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
    }

    private  void getScreenDimension(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        dens = displaymetrics.densityDpi;


        if (height<1500){distance =height*.966;}
        else {distance =height*.899;}


        //Toast.makeText(getApplicationContext(), ">>" + height+">>"+dens, Toast.LENGTH_LONG).show();
      /*  double wi = (double)width / (double)dens;
        double hi = (double)height / (double)dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x+y);

        String[] screenInformation = new String[3];
        screenInformation[0] = String.valueOf(width) + " px";
        screenInformation[1] = String.valueOf(height) + " px" ;
        screenInformation[2] = String.format("%.2f", screenInches) + " inches" ;

        return screenInformation;

       */
    }
    private String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month-1];
    }

    private String getNameOfDay(int year, int dayOfYear) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);

        return days[dayIndex - 1];
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
