package com.m2comm.ultrasound.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.m2comm.ultrasound.MainActivity;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.module.Custom_SharedPreferences;

import java.util.Timer;

public class IntroActivity extends AppCompatActivity {

    private Handler handler;
    private int time = 1500;
    private Custom_SharedPreferences csp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        this.init();

    }

    private void init() {
        this.handler = new Handler();
        this.csp = new Custom_SharedPreferences(this);

        final String deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        csp.put("deviceid", deviceid);

        this.moveMain();
    }

    public void moveMain() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, time);
    }
}