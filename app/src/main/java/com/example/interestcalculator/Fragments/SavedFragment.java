package com.example.interestcalculator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.interestcalculator.Adapter.SavedAdapter;
import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.databinding.FragmentSavedBinding;
import com.example.interestcalculator.models.InterestModel;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    FragmentSavedBinding savedBinding;
ArrayList<InterestModel> savedList;
    DbHeleper dbHeleper;
    SavedAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        savedBinding = FragmentSavedBinding.inflate(inflater, container, false);

        dbHeleper = new DbHeleper(requireContext());


        savedList = new ArrayList<InterestModel>(dbHeleper.getSaveRecord());

        adapter = new SavedAdapter(savedList,getContext());
        savedBinding.savedRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return savedBinding.getRoot();
    }
}