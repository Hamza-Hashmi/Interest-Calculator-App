package com.example.interestcalculator.Activities;

import static com.example.interestcalculator.widgets.Commons.LANGUAGE;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.interestcalculator.Fragments.HistoryFragment;
import com.example.interestcalculator.Fragments.HomeFragment;
import com.example.interestcalculator.Fragments.SavedFragment;
import com.example.interestcalculator.Fragments.SettingsFragment;
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.ActivityDashboardBinding;
import com.example.interestcalculator.models.LocalHelper;
import com.example.interestcalculator.widgets.ExternalStoragePermissions;
import com.example.interestcalculator.widgets.SharedHelper;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding dashboardBinding;

    public static String ID = "";
    public static String NAME = "";
    public static String PRINCIPAL_AMOUNT= "";
    public static String TOTAL_AMOUNT = "";
    public static String DURATION = "";
    String language;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(dashboardBinding.getRoot());

        setLocalLanguage();
        setSupportActionBar(dashboardBinding.DBappbar);
        if(Build.VERSION.SDK_INT >= 23) {
            ExternalStoragePermissions.verifyStoragePermissions(this);
        }

        if (savedInstanceState == null) {
            dashboardBinding.DBappbar.setTitle(R.string.home);
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).commit();
        }
        setupBottomNavigaion();


    }

    private void setLocalLanguage() {
        language = SharedHelper.getKey(this, LANGUAGE);

        Log.e("TAG,","onCreate: " + language);
        if (language.equalsIgnoreCase("Hindi")) {
            LocalHelper.setLocale(this, "hi");

        } else if (language.equalsIgnoreCase("Kannada")) {
            LocalHelper.setLocale(this, "kn");

        } else if (language.equalsIgnoreCase("Malaylam")) {
            LocalHelper.setLocale(this, "ml");
        } else if (language.equalsIgnoreCase("Telugu")) {
            LocalHelper.setLocale(this, "te");
        }
    }


    @SuppressLint("NonConstantResourceId")
    private void setupBottomNavigaion() {
        dashboardBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    dashboardBinding.DBappbar.setTitle(R.string.home);
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.history:
                    dashboardBinding.DBappbar.setTitle(R.string.history);
                    selectedFragment = new HistoryFragment();
                    break;
                case R.id.saved:
                    dashboardBinding.DBappbar.setTitle(R.string.saved);
                    selectedFragment = new SavedFragment();
                    break;
                case R.id.settings:
                    dashboardBinding.DBappbar.setTitle(R.string.settings);
                    selectedFragment = new SettingsFragment();
                    break;

            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
            return true;
        });
    }
}