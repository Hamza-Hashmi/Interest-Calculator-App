package com.example.interestcalculator.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.databinding.FragmentHiistoryBinding;
import com.example.interestcalculator.models.InterestModel;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    FragmentHiistoryBinding hiistoryBinding;
    DbHeleper dbHeleper;
    ArrayList<InterestModel> intersetHistoryList;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHeleper = new DbHeleper(requireContext());


        intersetHistoryList = new ArrayList<>(dbHeleper.getHistory());
        intersetHistoryList.forEach((model) -> Log.e("TAG", "onCreate: " + model ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hiistoryBinding = FragmentHiistoryBinding.inflate(inflater, container, false);
        return hiistoryBinding.getRoot();
    }
}