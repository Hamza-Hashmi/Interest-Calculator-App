package com.example.interestcalculator.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.interestcalculator.databinding.FragmentHomeBinding;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    FragmentHomeBinding homeBinding;
    DatePickerDialog.OnDateSetListener mDateSetlistener, mRDateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        initGivenDate();
        initReturnDate();
        return homeBinding.getRoot();

    }


    @SuppressLint("SetTextI18n")
    private void initReturnDate() {
        homeBinding.edtReturnDate.getEditText().setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            month = month + 1;

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mRDateListener, day, month, year);
            datePickerDialog.setTitle("Return Date");
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });
        mRDateListener = (view, year, month, dayOfMonth) -> {
            homeBinding.edtReturnDate.getEditText().setText(dayOfMonth + "-" + month + "(" + getMonthFormat(month + 1) + ")" + "-" + year);
        };
    }

    //    ! -> Given DatePicker
    @SuppressLint("SetTextI18n")
    private void initGivenDate() {
        homeBinding.edtGivenDate.getEditText().setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            month = month + 1;
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetlistener, day, month, year);
            datePickerDialog.setTitle("Given Date");
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        mDateSetlistener = (view, year, month, dayOfMonth) -> {
            homeBinding.edtGivenDate.getEditText().setText(dayOfMonth + "-" + month + "(" + getMonthFormat(month + 1) + ")" + "-" + year);
        };

    }


    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN";
        }
    }

}