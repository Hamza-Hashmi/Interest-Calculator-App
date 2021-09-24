package com.example.interestcalculator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.interestcalculator.databinding.FragmentSavedBinding;

public class SavedFragment extends Fragment {

    FragmentSavedBinding savedBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        savedBinding = FragmentSavedBinding.inflate(inflater, container, false);
        return savedBinding.getRoot();
    }
}