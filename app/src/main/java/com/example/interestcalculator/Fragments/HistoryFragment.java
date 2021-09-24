package com.example.interestcalculator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.interestcalculator.databinding.FragmentHiistoryBinding;

public class HistoryFragment extends Fragment {

    FragmentHiistoryBinding hiistoryBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hiistoryBinding = FragmentHiistoryBinding.inflate(inflater, container, false);
        return hiistoryBinding.getRoot();
    }
}