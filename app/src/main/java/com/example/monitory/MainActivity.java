package com.example.monitory;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    public Location mLocation;
    TextView latLng;
    Button buttonStart,buttonEnd;
    ImageView notification;
    GeoPoint locationStart,locationEnd;
    Timestamp timeStart,timeEnd;
    //Button buttonEnd;
    GoogleApiClient mGoogleApiClient;
    String Day,m;
    String address;
    String city ;
    String state,value ,knownName;
    String country ;
    String postal_code ,SubLocality;
    int currentDay ;
    int currentMonth ;
    int currentYear ;
    int currentDayOfWeek ;
    int currentDayOfMonth ;
    int CurrentDayOfYear ;
    int minute;
    String email;
    //12 hour format
    int hour ;

    private static final String hourStart  = "hourStart";
    private static final String minuteStart = "minuteStart";
    private static final String locationLatitudeStart = "locationLatitudeStart";
    private static final String locationLongitudeStart = "locationLongitudeStart";

    private static final String hourEnd = "hourEnd";
    private static final String minuteEnd = "minuteEnd";
    private static final String locationLatitudeEnd = "locationLatitudeEnd";
    private static final String locationLongitudeEnd = "locationLongitudeEnd";

    FirebaseFirestore db;
    TextView textDisplay;


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 15000;  /* 15 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    public double locationLatitude =-1;
    public double locationLongitude =-1;

    FileOutputStream fos = null;
    DataOutputStream dos = null;

    FileInputStream fis=null ;
    DataInputStream dis=null;
    LocalData localData;
    private static final  String fileName = "fileName";
    ClipboardManager myClipboard;
    public int statButtonInFile ;
    SharedPreferences.Editor editor;
    SharedPreferences pref ;
    private FirebaseUser mAuth;
    LinearLayout years,subyears,subyears1;
    ConstraintLayout parnt ;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    GeoPoint locationDb=null;

    Timestamp timeStartDb=null;

    private final static int ALL_PERMISSIONS_RESULT = 101;

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
        setContentView(R.layout.activity_main);







        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        localData = new LocalData(getApplicationContext());
//        localData.set_hour(18);
//        localData.set_min(50);
//        NotificationScheduler.setReminder(MainActivity.this, AlarmReceiver.class, localData.get_hour(), localData.get_min());
       // latLng = (TextView) findViewById(R.id.lat);
        buttonStart = (Button) findViewById(R.id.btn);
        latLng=findViewById(R.id.latLng);
        notification=(ImageView) findViewById(R.id.notification);
        years = (LinearLayout) findViewById(R.id.years);
        subyears= (LinearLayout) findViewById(R.id.subyears);
        subyears1= (LinearLayout) findViewById(R.id.subyears1);
        parnt=(ConstraintLayout)findViewById(R.id.parnt);
        //buttonEnd = (Button) findViewById(R.id.btn1);
        db = FirebaseFirestore.getInstance();
        years.setVisibility(View.GONE);








       if(!internet()){showAlertInternet();

            //Toast.makeText(getApplicationContext(), "internet111", Toast.LENGTH_LONG).show();

        }
        if(!isLocationEnabled()){showAlert();

            //Toast.makeText(getApplicationContext(), "Location111", Toast.LENGTH_LONG).show();

        }




        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0) {
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }



        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                years .setVisibility(View.VISIBLE);
            }
        });

        subyears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                years .setVisibility(View.GONE);
                //Toast.makeText(getApplicationContext(), "subyears1111", Toast.LENGTH_LONG).show();


                //finish();
                // String value=mAuth.getCurrentUser().getEmail();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                //intent.putExtra("emailUser",value);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                  startActivity(intent);

            }
        });

        subyears1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                years .setVisibility(View.GONE);
                //Toast.makeText(getApplicationContext(), "subyears22222", Toast.LENGTH_LONG).show();


                   Intent intent2 = new Intent(MainActivity.this, MainActivity2End.class);
                //intent.putExtra("emailUser",value);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent2);



            }
        });
        parnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                years .setVisibility(View.GONE);



            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationLatitude = -1;
                locationLongitude = -1;
               if(!internet()){showAlertInternet();

                    //Toast.makeText(getApplicationContext(), "internet2222", Toast.LENGTH_LONG).show();
                if(!isLocationEnabled()){showAlert();

               }


                    //Toast.makeText(getApplicationContext(), "Location2222", Toast.LENGTH_LONG).show();

                }


                //mGoogleApiClient.connect();

                mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(MainActivity.this)
                        .addOnConnectionFailedListener(MainActivity.this)
                        .build();
                mGoogleApiClient.connect();


                if (mGoogleApiClient != null) {
                   // Toast.makeText(getApplicationContext(), "connection true ", Toast.LENGTH_LONG).show();
                    mGoogleApiClient.connect();
                }
                if (mGoogleApiClient == null) {
                    //Toast.makeText(getApplicationContext(), "nnn nnnnnnnnnnnnn ", Toast.LENGTH_LONG).show();
                    mGoogleApiClient.connect();
                }
                if (!checkPlayServices()) {
                    latLng.setText("Please install Google Play services.");
                }
                //


                 if(internet()&&isLocationEnabled()){

                     if (statButtonInFile==1){
                         showDialog(MainActivity.this,  "start job",  "Are you sure to start work");
                     }
                     else if (statButtonInFile==0){

                         showDialog(MainActivity.this,  "end job",  "Are you sure to end work");
                     }


                }

                //mGoogleApiClient.disconnect();
                //stopLocationUpdates();



            }
        });

/*
        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, reportJob.class);

                //intent.putExtra("emailUser", mAuth.getCurrentUser().getEmail());
                startActivity(intent);
/*
                pref = getApplicationContext().getSharedPreferences("file", 0); // 0 - for private mode
                editor = pref.edit();
                String i=pref.getString("statButton","start");
                if (i=="start"){
                    Toast.makeText(getApplicationContext(), i+"-"+ i, Toast.LENGTH_LONG).show();
                    statButtonInFile ="start";
                    //editor.putInt("statButton",0);
                    //statButtonInFile =0;
                    // editor.commit();
                }

                editor.putString("statButton", "start");
                editor.commit();

 */

/*

                if(!internet()){showAlertInternet();

                    Toast.makeText(getApplicationContext(), "internet2222", Toast.LENGTH_LONG).show();

                }
                if(!isLocationEnabled()){showAlert();

                    Toast.makeText(getApplicationContext(), "Location2222", Toast.LENGTH_LONG).show();

                }





                mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(MainActivity.this)
                        .addOnConnectionFailedListener(MainActivity.this)
                        .build();
                if (mGoogleApiClient != null) {
                    mGoogleApiClient.connect();
                }
                if (!checkPlayServices()) {
                    latLng.setText("Please install Google Play services.");
                }
                // mGoogleApiClient.disconnect();

                if(internet()&&isLocationEnabled()){

                    showDialog(MainActivity.this,  "end job",  "Are you sure to finish the work");
                }


            }
        });


*/


    }


    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();

        pref = getApplicationContext().getSharedPreferences("file", 0); // 0 - for private mode
        editor = pref.edit();
        statButtonInFile=pref.getInt("statButton",1);

       // Toast.makeText(getApplicationContext(), statButtonInFile+"-"+ statButtonInFile, Toast.LENGTH_LONG).show();

        //Toast.makeText(getApplicationContext(), i+"-"+ "sssss", Toast.LENGTH_LONG).show();

        if (statButtonInFile==1){
            buttonStart.setText("start");
        }
        else if (statButtonInFile==0){

            buttonStart.setText("end");
        }

        //editor.putInt("statButton",0);
        //statButtonInFile =0;
        // editor.commit();



        // Toast.makeText(getApplicationContext(), i+"-"+"eeee", Toast.LENGTH_LONG).show();
        //statButtonInFile ="end";
        //buttonStart.setText("end");








        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();



        if (!checkPlayServices()) {
            latLng.setText("Please install Google Play services.");
        }




    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if(mLocation!=null)
        {

            locationLatitude= mLocation.getLatitude();
            locationLongitude= mLocation.getLongitude();


        }

         startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        //if(location!=null)
        // latLng.setText("Lati: "+location.getLatitude()+" , Long : "+location.getLongitude());



    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else
                finish();

            return false;
        }
        return true;
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }

              LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


    }

    private boolean hasPermission(Object permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission((String) permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Intent i = new Intent(MainActivity.this, Home.class);
        // i.putExtra("emailUser",value);
        //Toast.makeText(getApplicationContext(),"eeee: "+value, Toast.LENGTH_SHORT).show();
        //startActivity(i);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }


    public void stopLocationUpdates()
    {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
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

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // MyActivity.this.finish();

                if (locationLatitude != -1) {
                   // db.enableNetwork();
                    //latLng.setText("Latitude : " + locationLatitude + " , Longitude : " + locationLongitude);


                    //myRef.setValue(module);


                    //DatabaseReference myRef = database.getReference("Monitory").child(email);
                    //ModuleStart module = new ModuleStart(locationLatitude,locationLongitude);



                       /* latLng.setText("Day: "+currentDay+"\n"+"Month: "+locationLatitude+
                                        "\n"+"Year: "+locationLongitude+
                                        "\n"+"currentDayOfWeek: "+currentDayOfWeek+
                                        "\n"+"currentDayOfMonth: "+currentDayOfMonth+
                                        "\n"+"CurrentDayOfYear: "+CurrentDayOfYear+
                                        "\n"+"DayName: "+Day+
                                        "\n"+"MonthName: "+m
                                );

                        */

                    //Toast.makeText(getApplicationContext(), ">>" + statButtonInFile, Toast.LENGTH_LONG).show();
                    //int i =pref.getInt("statButton", 1);
                    if (statButtonInFile == 1) {




                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                         email = user.getEmail();
                        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
                        Date currentTime = localCalendar.getTime();
                        currentDay = localCalendar.get(Calendar.DATE);
                        currentMonth = localCalendar.get(Calendar.MONTH) + 1;
                        currentYear = localCalendar.get(Calendar.YEAR);
                        currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
                        currentDayOfMonth = localCalendar.get(Calendar.DAY_OF_MONTH);
                        CurrentDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
                        //Day = getNameOfDay(currentYear, CurrentDayOfYear);
                        //m = theMonth(currentMonth);

                        minute = localCalendar.get(Calendar.MINUTE);
                        //12 hour format
                        hour = localCalendar.get(Calendar.HOUR);
                        //24 hour format
                        int hourofday = localCalendar.get(Calendar.HOUR_OF_DAY);
                       //
                        final DocumentReference docRef = db.collection("users").document(email).collection(String.valueOf(currentMonth+"-"+currentYear)).document(String.valueOf(currentDay));
                        //
                        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                   // Log.w(TAG, "Listen failed.", e);
                                    //Toast.makeText(MainActivity.this, "Listen failed.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if ( !snapshot.exists()) {

                                    //Toast.makeText(MainActivity.this, "you already transferred data", Toast.LENGTH_SHORT).show();
//                                    locationDb= snapshot.getGeoPoint("start_loc" );
//
//                                    timeStartDb=snapshot.getTimestamp("start_time");



                                    Calendar calendar = Calendar.getInstance();
                                    Date currentTimestamp = new java.sql.Date(calendar.getTime().getTime());

                                    //


                                    locationStart= new GeoPoint
                                            (locationLatitude, locationLongitude);
                                    Date now = new java.util.Date();
//
                                    timeStart = new Timestamp(new Date());
                                    //Map<Boolean, Object> checkedContact = new HashMap<>();

                                    Map<String, Object> newContact = new HashMap<>();
                                    //newContact.put("checked", false);
                                    newContact.put("checked","0" );
                                    newContact.put("start_loc", locationStart);
                                    newContact.put("start_time", new Timestamp(new Date()));


                                    db.collection("users").document(email).collection(String.valueOf(currentMonth+"-"+currentYear)).document(String.valueOf(currentDay))
                                            .set(newContact)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    //db.disableNetwork();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });


//                        Map<String, Object> newContact = new HashMap<>();
//                        newContact.put(hourStart, minute);
//                        newContact.put(minuteStart, hour);
//                        newContact.put(locationLatitudeStart, locationLatitude);
//                        newContact.put(locationLongitudeStart, locationLongitude);
//                        ModuleStart module = new ModuleStart(locationLatitude,locationLongitude,minute,hour);
/*
                        Task<DocumentReference> documentReferenceTask = db.collection("Users").document(email)
                                .collection(String.valueOf("Years")).document(String.valueOf(currentYear))
                                .collection(String.valueOf("Months")).document(m)
                                .collection(String.valueOf("Days")).document(Day)
                                .collection(String.valueOf("Start")).add(module);




 */
                                    locationLatitude = -1;
                                    locationLongitude = -1;

                                    buttonStart.setText("end");
                                    statButtonInFile = 0;

                                    editor.putInt("statButton", 0);


                                    editor.commit();
                                    mGoogleApiClient.disconnect();
                                    stopLocationUpdates();
                                    //db=null;
                                }
                                //else{Toast.makeText(getApplicationContext(), "you alrady  started job " , Toast.LENGTH_LONG).show();}
                            }
                        });
                     /*   try {
                            Geocoder gecoder2 = new Geocoder(getApplicationContext(), Locale.getDefault());
                            List<Address> addresses = gecoder2.getFromLocation(29.994289, 31.2810098, 1);

                            address = addresses.get(0).getAddressLine(0);
                            city = addresses.get(0).getLocality();
                            state = addresses.get(0).getAdminArea();
                            country = addresses.get(0).getCountryName();
                            postal_code = addresses.get(0).getPostalCode();
                            knownName = addresses.get(0).getFeatureName();
                            SubLocality = addresses.get(0).getSubAdminArea();

                            latLng.setText(
                                    "adress:  " + address + "\n\n" +
                                            "city:  " + city + "\n\n" +
                                            "state:  " + state + "\n\n" +
                                            "country:  " + country + "\n\n" +
                                            "postal_code:  " + email + "\n\n" +
                                            "knownName:  " + knownName + "\n\n" +
                                            "SubLocality:  " + SubLocality);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                      */


                        // Write a message to the database

                        //email = email.replace(".", "");
/*
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                DatabaseReference myRef = database.getReference("database").child(email).child(String.valueOf(currentYear))
                                        .child(String.valueOf(m))
                                        .child(String.valueOf(Day))
                                        .child(String.valueOf("Start"))
                                        ;
                                ModuleStart module = new ModuleStart(locationLatitude,locationLongitude,minute,hour);

                                myRef.setValue(module);


 */



                        }
                        else if (statButtonInFile == 0) {
                        //db.enableNetwork();


                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String email = user.getEmail();
                      /* try {
                            Geocoder gecoder2 = new Geocoder(getApplicationContext(), Locale.getDefault());
                            List<Address> addresses = gecoder2.getFromLocation(30.073553, 31.2864914, 1);

                            address = addresses.get(0).getAddressLine(0);
                            city = addresses.get(0).getLocality();
                            state = addresses.get(0).getAdminArea();
                            country = addresses.get(0).getCountryName();
                            postal_code = addresses.get(0).getPostalCode();
                            knownName = addresses.get(0).getFeatureName();
                            SubLocality = addresses.get(0).getSubAdminArea();

                            latLng.setText(
                                    "adress:  " + address + "\n\n" +
                                            "city:  " + city + "\n\n" +
                                            "state:  " + state + "\n\n" +
                                            "country:  " + country + "\n\n" +
                                            "postal_code:  " + email + "\n\n" +
                                            "knownName:  " + knownName + "\n\n" +
                                            "SubLocality:  " + SubLocality);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                       */





                        // Write a message to the database
                        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
                        Date currentTime = localCalendar.getTime();
                        currentDay = localCalendar.get(Calendar.DATE);
                        currentMonth = localCalendar.get(Calendar.MONTH) + 1;
                        currentYear = localCalendar.get(Calendar.YEAR);
                        currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
                        currentDayOfMonth = localCalendar.get(Calendar.DAY_OF_MONTH);
                        CurrentDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
                        //Day = getNameOfDay(currentYear, CurrentDayOfYear);
                        //m = theMonth(currentMonth);

                        minute = localCalendar.get(Calendar.MINUTE);
                        //12 hour format
                        hour = localCalendar.get(Calendar.HOUR);
                        //24 hour format
                        int hourofday = localCalendar.get(Calendar.HOUR_OF_DAY);
                       // email = email.replace(".", "");
/*
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                DatabaseReference myRef = database.getReference("database").child(email).child(String.valueOf(currentYear))
                                        .child(String.valueOf(m))
                                        .child(String.valueOf(Day))
                                        .child(String.valueOf("End"))
                                        ;
                                ModuleEnd module = new ModuleEnd(locationLatitude,locationLongitude,minute,hour);

                                myRef.setValue(module);

 */
                        locationEnd= new GeoPoint
                                (locationLatitude, locationLongitude);
                        Date now = new java.util.Date();
//
                        timeEnd = new Timestamp(new Date());

                        Map<String, Object> newContact = new HashMap<>();
                        newContact.put("end_loc", locationEnd);
                        newContact.put("end_time", timeEnd
                        );


                         db.collection("users").document(email).collection(String.valueOf(currentMonth+"-"+currentYear)).document(String.valueOf(currentDay))
                                .update(newContact)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

//                        Map<String, Object> newContact = new HashMap<>();
//                        newContact.put(hourEnd, minute);
//                        newContact.put(minuteEnd, hour);
//                        newContact.put(locationLatitudeEnd, locationLatitude);
//                        newContact.put(locationLongitudeEnd, locationLongitude);
//                        ModuleEnd module = new ModuleEnd(locationLatitude, locationLongitude, minute, hour);

/*
                        Task<DocumentReference> documentReferenceTask = db.collection("Users").document(email)
                                   .collection(String.valueOf("Years")).document(String.valueOf(currentYear))
                                .collection(String.valueOf("Months")).document(m)
                                .collection(String.valueOf("Days")).document(Day)
                                .collection(String.valueOf("End")).add(module);


 */




                                        /*.addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(MainActivity.this, "User Registered",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MainActivity.this, "ERROR" + e.toString(),
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                         */


                        locationLatitude = -1;
                        locationLongitude = -1;

                        buttonStart.setText("start");
                        statButtonInFile = 1;


                        editor.putInt("statButton", 1);

                        editor.commit();
                        //editor.apply();

                        mGoogleApiClient.disconnect();
                       stopLocationUpdates();




                    }



                } else {
                    Toast.makeText(getApplicationContext(), "Please click again to " + title, Toast.LENGTH_LONG).show();
                }

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

//    private String theMonth(int month){
//        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//        return monthNames[month-1];
//    }
//
//    private String getNameOfDay(int year, int dayOfYear) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
//        String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
//
//        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
//
//        return days[dayIndex - 1];
//    }

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

class ConnectionDetector {

    private Context _context;

    public ConnectionDetector(Context context){
        this._context = context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //return (connectivity != null && connectivity.getActiveNetworkInfo().isConnectedOrConnecting()) ? true : false;
        if (connectivity.getActiveNetworkInfo() != null && connectivity.getActiveNetworkInfo().isAvailable() &&    connectivity.getActiveNetworkInfo().isConnected())
        {

            return true;
        } else {
            //System.out.println("Internet Connection Not Present");
            return false;

        }
    }




}