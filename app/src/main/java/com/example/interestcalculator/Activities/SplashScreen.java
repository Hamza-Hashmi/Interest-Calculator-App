package com.example.interestcalculator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interestcalculator.R;

public class SplashScreen extends AppCompatActivity {

    public static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  ! -> Splash Screen
        new Handler().postDelayed((Runnable) () -> {
            startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
            finish();
        }, SPLASH_DELAY);
    }
}