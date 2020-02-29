package com.example.monitory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class CreatePasswordActivity extends AppCompatActivity {
    PatternLockView mPatternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                SharedPreferences prfPassword =getSharedPreferences("PREFS",0);
                SharedPreferences.Editor editor=prfPassword.edit();
                editor.putString("password", PatternLockUtils.patternToString(mPatternLockView, pattern));
                editor.commit();
                editor.apply();
                        //

                Intent intent =new Intent(getApplicationContext(),log.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCleared() {

        }
    });



   }


    }

