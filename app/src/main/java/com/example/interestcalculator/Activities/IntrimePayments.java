package com.example.interestcalculator.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.interestcalculator.Adapter.PagerAdapter;
import com.example.interestcalculator.databinding.ActivityIntrimePaymentsBinding;
import com.google.android.material.tabs.TabLayout;

public class IntrimePayments extends AppCompatActivity {
    ActivityIntrimePaymentsBinding intrimePaymentsBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intrimePaymentsBinding = ActivityIntrimePaymentsBinding.inflate(getLayoutInflater());
        setContentView(intrimePaymentsBinding.getRoot());
        setTabLayout();
    }

    private void setTabLayout() {
        TabLayout tabLayout = intrimePaymentsBinding.tabLayoutinterime;
        TabLayout.Tab intrimePaymentTab = tabLayout.newTab();
        intrimePaymentTab.setText("Add Payment");
        tabLayout.addTab(intrimePaymentTab);

        TabLayout.Tab transactionTab = tabLayout.newTab();
        tabLayout.addTab(transactionTab);
        transactionTab.setText("View Transaction");

        setTabLayoutAdapter();
    }

    private void setTabLayoutAdapter() {

        TabLayout tabLayout = intrimePaymentsBinding.tabLayoutinterime;
        ViewPager simpleViewPager = intrimePaymentsBinding.viewPager;

        PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        simpleViewPager.setAdapter(adapter);

        // addOnPageChangeListener event change the tab on slide
        simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(simpleViewPager));


    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(ViewPager simpleViewPager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                simpleViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

}