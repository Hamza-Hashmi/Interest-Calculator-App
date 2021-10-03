package com.example.interestcalculator.Fragments;

import static com.example.interestcalculator.Activities.DashboardActivity.ID;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.interestcalculator.Adapter.IntrimeHistoryAdapter;
import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.databinding.FragmentViewTransactionBinding;
import com.example.interestcalculator.models.InterestModel;


import java.util.ArrayList;

public class ViewTransactionFragment extends Fragment {

    FragmentViewTransactionBinding binding;
    DbHeleper dbHeleper;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentViewTransactionBinding.inflate(getLayoutInflater());

        dbHeleper = new DbHeleper(getContext());

        ArrayList<InterestModel> list = new ArrayList<>(dbHeleper.getIntrimeHistory(ID));
        list.forEach((listt) -> Log.e("TAG", "onCreateView: " + listt.getFid() ));

        IntrimeHistoryAdapter adapter = new IntrimeHistoryAdapter(list,getContext());
        binding.transactionRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return binding.getRoot();
    }
}