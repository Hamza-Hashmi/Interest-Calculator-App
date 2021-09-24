package com.example.interestcalculator.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.interestcalculator.Fragments.HistoryFragment;
import com.example.interestcalculator.Fragments.HomeFragment;
import com.example.interestcalculator.Fragments.SavedFragment;
import com.example.interestcalculator.Fragments.SettingsFragment;
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding dashboardBinding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(dashboardBinding.getRoot());

        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).commit();
        }
        setSupportActionBar(dashboardBinding.DBappbar);
        setupBottomNavigaion();
//        dashboardBinding.bottomNavigation.setOnItemSelectedListener(navigationItemSelectedListener);


    }

    @SuppressLint("NonConstantResourceId")
    private void setupBottomNavigaion() {
        dashboardBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.history:
                    selectedFragment = new HistoryFragment();
                    break;
                case R.id.saved:
                    selectedFragment = new SavedFragment();
                    break;
                case R.id.settings:
                    selectedFragment = new SettingsFragment();
                    break;

            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
            return true;
        });
    }
//    @SuppressLint("NonConstantResourceId")
//    private final  navigationItemSelectedListener =
//            item -> {
//                Fragment selectedFragment = null;
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        selectedFragment = new HomeFragment();
//                        break;
//                    case R.id.history:
//                        selectedFragment = new HistoryFragment();
//                        break;
//                    case R.id.saved:
//                        selectedFragment = new SavedFragment();
//                        break;
//                    case R.id.settings:
//                        selectedFragment = new SettingsFragment();
//                        break;
//
//                }
//                assert selectedFragment != null;
//                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
//                return true;
//            };
}