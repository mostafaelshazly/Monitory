package com.example.monitory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class ProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
        SharedPreferences prfPassword =getSharedPreferences("PREFS",0);
        String password = prfPassword.getString("password","0");
                //Toast.makeText(ProgramActivity.this, "password  "+password, Toast.LENGTH_SHORT).show();
        if(password.equals("0")){
            Intent intent =new Intent(getApplicationContext(),CreatePasswordActivity.class);
            startActivity(intent);
            finish();

        }else {
            Intent intent=new Intent(getApplicationContext(),InputPasswordActivity.class);
            startActivity(intent);
            finish();
        }
    }
},500);
        }
}
