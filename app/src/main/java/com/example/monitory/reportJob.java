package com.example.monitory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class reportJob extends AppCompatActivity {
    Spinner spinner;
    int currentYear;
    int currentDay ;
    int currentMonth ;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Boolean showLayoutMonth = false;
    Boolean showLayoutyear = false;
    int currentDayOfWeek ;
    int currentDayOfMonth ;
    int CurrentDayOfYear ;
    ConstraintLayout parnt ;
    int monthChose,yearChose;
    String Day,m;
    Calendar localCalendar;
    String[] currentYearList = new String[2];
    TextView year0,year1, day ;
    ImageView left,centerleft,rghit,centerrhite,go,back,background;
    ScrollView scrollmonth;
    LinearLayout years,subyears,subyears1,month;
    LinearLayout January_1,February_2,March_3,April_4,May_5,June_6,July_7,August_8,September_9,October_10,November_11,December_12;
    TextView overTime,overTimeMins ,totalSalary,salary,totalMamoria,totalAction;
    String email;
    int countMamoriaList;
    Double sumTotalActionList;
    Double sumOverTimeMins=0.0;
    Double  sumOverTime=0.0;
    ArrayList<Double>  totalActionList= new ArrayList<Double>();
    ArrayList<Boolean>   totalMamoriaList = new ArrayList<Boolean>();

    private FirebaseFirestore db,dbList;
    @Override
    protected void onStart() {
        super.onStart();
        showLayoutMonth = false;
        showLayoutyear = false;

    }
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
            setContentView(R.layout.activity_report_job);
            if(!internet()){showAlertInternet();

                //Toast.makeText(getApplicationContext(), "internet111", Toast.LENGTH_LONG).show();

            }
            sumTotalActionList=0.0;
            parnt=(ConstraintLayout)findViewById(R.id.parnt);
            go = (ImageView) findViewById(R.id.go);
            back = (ImageView) findViewById(R.id.back);
            left = (ImageView) findViewById(R.id.left);
           // background=(ImageView) findViewById(R.id.background);
            totalSalary = (TextView) findViewById(R.id.totalSalary);
            overTime= (TextView) findViewById(R.id.overTime);
            overTimeMins = (TextView) findViewById(R.id.overTimeMins);
            salary = (TextView) findViewById(R.id.salary);
            totalMamoria = (TextView) findViewById(R.id.totalMamoria);
            totalAction = (TextView) findViewById(R.id.totalAction);


            rghit = (ImageView) findViewById(R.id.rghit);

            centerrhite = (ImageView) findViewById(R.id.centerrhite);

            centerleft = (ImageView) findViewById(R.id.centerleft);
            month=(LinearLayout) findViewById(R.id.month);
            years = (LinearLayout) findViewById(R.id.years);
            subyears= (LinearLayout) findViewById(R.id.subyears);
            subyears1= (LinearLayout) findViewById(R.id.subyears1);

            day  = (TextView)findViewById(R.id.day);
            scrollmonth = (ScrollView) findViewById(R.id.scrollmonth);
            //scrollmonth.setVisibility(View.GONE);
            January_1 = (LinearLayout) findViewById(R.id.January_1);
            February_2 = (LinearLayout) findViewById(R.id.February_2);
            March_3 = (LinearLayout) findViewById(R.id.March_3);
            April_4 = (LinearLayout) findViewById(R.id.April_4);
            May_5 = (LinearLayout) findViewById(R.id.May_5);
            June_6 = (LinearLayout) findViewById(R.id.June_6);
            July_7 = (LinearLayout) findViewById(R.id.July_7);
            August_8 = (LinearLayout) findViewById(R.id.August_8);
            September_9 = (LinearLayout) findViewById(R.id.September_9);
            October_10 = (LinearLayout) findViewById(R.id.October_10);
            November_11 = (LinearLayout) findViewById(R.id.November_11);
            December_12 = (LinearLayout) findViewById(R.id.December_12);

            years.setVisibility(View.GONE);
            month.setVisibility(View.GONE);

            year0 = (TextView) findViewById(R.id.year0);
            year1 = (TextView) findViewById(R.id.year1);


           /* subyears .setVisibility(View.VISIBLE);

            subyears .setVisibility(View.INVISIBLE);

            subyears .setVisibility(View.GONE);


            */
            // spinner = findViewById(R.id.spinner);
            localCalendar = Calendar.getInstance(TimeZone.getDefault());
            currentYear = localCalendar.get(Calendar.YEAR);
            currentDay = localCalendar.get(Calendar.DATE);
            currentMonth = localCalendar.get(Calendar.MONTH) + 1;

            currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
            currentDayOfMonth = localCalendar.get(Calendar.DAY_OF_MONTH);
            CurrentDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
            m = theMonth(currentMonth);
            day.setText(currentDayOfMonth +" "+m);
            year0.setText(Integer.toString(currentYear));
           year1.setText(Integer.toString(currentYear-1));
            currentYearList[0]=Integer.toString(currentYear); ;
            currentYearList[1]=Integer.toString(currentYear-1);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    years.setVisibility(View.GONE);
                    month.setVisibility(View.GONE);
                    scrollmonth.setVisibility(View.GONE);
                    finish();
                   // Intent intent = new Intent(reportJob.this, Home.class);
                    //intent.putExtra("emailUser",value);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    //startActivity(intent);
                }
            });
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!internet()){showAlertInternet();

                     }
                    years.setVisibility(View.GONE);
                    month.setVisibility(View.GONE);
                    scrollmonth.setVisibility(View.GONE);
                    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
                    Date currentTime = localCalendar.getTime();
                    currentDay = localCalendar.get(Calendar.DATE);


                    if(monthChose==0){
                        showDialog(reportJob.this,  "choose month",  "You should choose the month");
                    }
                    else if (yearChose==0){

                        showDialog(reportJob.this,  "choose year",  "You should choose the year");

                    }

                    //recive fromm firestor
                    else{

                        showDialogrecive(reportJob.this,  "salary",  "Confirmation of salary knowledge");


                    }





                }
            });


            parnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    years .setVisibility(View.GONE);
                    scrollmonth.setVisibility(View.GONE);
                    years .setVisibility(View.GONE);


                }
            });

            centerleft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    years .setVisibility(View.GONE);
                    scrollmonth.setVisibility(View.GONE);
                    years .setVisibility(View.VISIBLE);
                    //showLayoutyear=true;
                   // Toast.makeText(getApplicationContext(), "showLayoutyear " +showLayoutyear, Toast.LENGTH_SHORT).show();


                }
            });
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    scrollmonth.setVisibility(View.GONE);
                    years .setVisibility(View.VISIBLE);
                    //showLayoutyear=true;
                   // Toast.makeText(getApplicationContext(), "showLayoutyear " +showLayoutyear, Toast.LENGTH_SHORT).show();



                }
            });

            subyears.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    years .setVisibility(View.GONE);
                    yearChose=currentYear;

                    Toast.makeText(getApplicationContext(), "yearChose "+yearChose, Toast.LENGTH_LONG).show();


                }
            });

            subyears1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    years .setVisibility(View.GONE);
                     yearChose=currentYear-1;

                    Toast.makeText(getApplicationContext(), "yearChose "+yearChose, Toast.LENGTH_LONG).show();



                }
            });
            centerrhite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    years .setVisibility(View.GONE);
                    month .setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "Month " +showLayoutMonth, Toast.LENGTH_SHORT).show();
                    //subyears .setVisibility(View.GONE);
                   // subyears1 .setVisibility(View.GONE);

                    scrollmonth .setVisibility(View.VISIBLE);
                    //month.setVisibility(View.VISIBLE);
                    showLayoutMonth=true;
                    //Toast.makeText(getApplicationContext(), "Month " +showLayoutMonth, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(getApplicationContext(), "currentYear-1"+currentYear1, Toast.LENGTH_LONG).show();

                }
            });

            rghit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    years .setVisibility(View.GONE);
                    month .setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "Month " +showLayoutMonth, Toast.LENGTH_SHORT).show();
                    //subyears .setVisibility(View.GONE);
                    // subyears1 .setVisibility(View.GONE);

                    scrollmonth .setVisibility(View.VISIBLE);
                    //month.setVisibility(View.VISIBLE);
                    //showLayoutMonth=true;
                    //Toast.makeText(getApplicationContext(), "Month " +showLayoutMonth, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(getApplicationContext(), "currentYear-1"+currentYear1, Toast.LENGTH_LONG).show();

                }
            });

            January_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=1;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });

            February_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=2;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
            March_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=3;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
            April_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=4;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
            May_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=5;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
            June_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=6;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
           July_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=7;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
            August_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=8;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
            September_9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=9;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
            October_10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=10;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
            November_11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=11;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();

                }
            });
            December_12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    month .setVisibility(View.GONE);
                    monthChose=12;


                    Toast.makeText(getApplicationContext(), "monthChose "+monthChose, Toast.LENGTH_LONG).show();


                }
            });

/*
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    R.layout.custom_spinner, currentYearList

            );
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String selectedItem = spinner.getItemAtPosition(position).toString();


                    //Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), ">>>"+position, Toast.LENGTH_LONG).show();
                    switch (position)

                    {

                        case 0:

                            break;

                        case 1:

                            break;

                        case 2:

                            break;

                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

 */
        }




    @Override
    protected void onResume() {
        super.onResume();




    }

    private String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month-1];
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



    public void showDialog(Activity activity, final String title, CharSequence message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }



    public void showDialogrecive(Activity activity, final String title, CharSequence message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                sumTotalActionList=0.0;
                countMamoriaList=0;
                dbList = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                email = user.getEmail();
                   dbList.collection("users").document(email).collection(String.valueOf(monthChose+"-"+yearChose)).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent( QuerySnapshot documentSnapshots ,  FirebaseFirestoreException e) {
                        if(e!=null){
                            Toast.makeText(reportJob.this, "no data", Toast.LENGTH_SHORT).show();
                        }

                        for (DocumentSnapshot doc : documentSnapshots){
//                            totalActionList.add(Double.valueOf(doc.getString("action")));
//                            //Toast.makeText(reportJob.this, "dis >"+String.valueOf(doc.getString("action")), Toast.LENGTH_SHORT).show();
//                            totalMamoriaList.add(doc.getBoolean("mamoria"));
//                            //
                            // Toast.makeText(reportJob.this, "overTimeMins  >"+doc.getString("overTimeMins"), Toast.LENGTH_SHORT).show();
                            if(doc!=null){
                                if(doc.getBoolean("mamoria")!=null&&doc.getString("action")!=null&&doc.getString("overTime")!=null&&doc.getString("overTimeMins")!=null){





                                sumTotalActionList=sumTotalActionList+Double.valueOf(doc.getString("action"));
                                sumOverTime=sumOverTime+Double.valueOf(doc.getString("overTime"));
                                sumOverTimeMins=sumOverTimeMins+Double.valueOf(doc.getString("overTimeMins"));

                                if(doc.getBoolean("mamoria")==true){
                                    countMamoriaList=countMamoriaList+1;

                                }
}
                            }

                        }

//                        for (Double num :totalActionList ) {
//                            sumTotalActionList=sumTotalActionList+num;
//                            //Toast.makeText(reportJob.this, "looop11111 >"+String.valueOf(num), Toast.LENGTH_SHORT).show();
//
//
//                        }
//
//                        Toast.makeText(reportJob.this, "size  >"+String.valueOf(totalActionList.size()), Toast.LENGTH_SHORT).show();
//
//                        for (Boolean numM : totalMamoriaList) {
//                           Toast.makeText(reportJob.this, "looop22222 >"+String.valueOf(numM), Toast.LENGTH_SHORT).show();
//
//                            if(numM==true){countMamoriaList++;
//                              }
//                            }



                    }
                });


//                sumTotalActionList=0.0;
//                for (String num :totalActionList ) {
//                    sumTotalActionList=sumTotalActionList+Double.valueOf(num);
//                    //Toast.makeText(reportJob.this, "looop11111 >"+String.valueOf(num), Toast.LENGTH_SHORT).show();
//
//
//                }
//                countMamoriaList=0;
//                for (Boolean numM : totalMamoriaList) {
//                    Toast.makeText(reportJob.this, "looop22222 >"+String.valueOf(numM), Toast.LENGTH_SHORT).show();
//
//                    if(numM==true){countMamoriaList++;
//
//                    }
//
//                }

                db = FirebaseFirestore.getInstance();
                final DocumentReference docRef = db.collection("users").document(email).collection(String.valueOf("monthlySalary")).document(monthChose+"-"+yearChose);
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                            Toast.makeText(reportJob.this, "Listen failed.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {

				String salaryText =snapshot.getString("salary");
				String salaryTotalText =snapshot.getString("salaryTotal");

				if(salaryText !=null&&salaryTotalText !=null){

					                salary.setText(" salary : "+salaryText );
                                 	totalSalary.setText(" total Salary : "+salaryTotalText );
                                 	totalAction.setText(" total Discounts : "+String.valueOf(sumTotalActionList));
                                	totalMamoria.setText(" total Mamoria : "+countMamoriaList);
                                    overTime.setText(" over Time "+sumOverTime);
                                    overTimeMins.setText(" over Time Mins "+sumOverTimeMins);



                    totalActionList.clear();
                    totalMamoriaList.clear();
					}
				else{
					Toast.makeText(reportJob.this,   "salary data null", Toast.LENGTH_SHORT).show();

				}
                                 









                        } else {
                            //Log.d(TAG, "Current data: null");
                            Toast.makeText(reportJob.this, "Current data: null", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // Intent i = new Intent(reportJob.this, Home.class);
        // i.putExtra("emailUser",value);
        //Toast.makeText(getApplicationContext(),"eeee: "+value, Toast.LENGTH_SHORT).show();
        //startActivity(i);

    }



}