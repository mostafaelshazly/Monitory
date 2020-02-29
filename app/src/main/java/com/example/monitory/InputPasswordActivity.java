package com.example.monitory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;
public class InputPasswordActivity extends AppCompatActivity {
    PatternLockView mPatternLockView;
String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_password);
        final Intent intent = getIntent();
        final String message = intent.getStringExtra("message");
        SharedPreferences prfPassword =getSharedPreferences("PREFS",0);
        password=prfPassword.getString("password","0");
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
                if(password.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))) {




                    Intent intent = new Intent(getApplicationContext(), log.class);
                    startActivity(intent);
                    finish();




                }
                else {

                    Toast.makeText(InputPasswordActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
              mPatternLockView.clearPattern();
                }
            }

            @Override
            public void onCleared() {

            }
        });


    }
}
