package com.example.interestcalculator.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.interestcalculator.Adapter.HistoryAdapter;
import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.databinding.FragmentHiistoryBinding;
import com.example.interestcalculator.models.InterestModel;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    FragmentHiistoryBinding hiistoryBinding;
    DbHeleper dbHeleper;
    ArrayList<InterestModel> intersetHistoryList;
    HistoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hiistoryBinding = FragmentHiistoryBinding.inflate(inflater, container, false);
        dbHeleper = new DbHeleper(requireContext());


        intersetHistoryList = new ArrayList<>(dbHeleper.getHistory());
        //intersetHistoryList.forEach((model) -> Log.e("TAG", "onCreate: " + model.getDurationPeriod() ));

        adapter = new HistoryAdapter(intersetHistoryList,requireContext());
        hiistoryBinding.historyRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        return hiistoryBinding.getRoot();
    }
}