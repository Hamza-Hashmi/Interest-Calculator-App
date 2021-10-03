package com.example.interestcalculator.Fragments;

import static com.example.interestcalculator.Activities.DashboardActivity.DURATION;
import static com.example.interestcalculator.Activities.DashboardActivity.ID;
import static com.example.interestcalculator.Activities.DashboardActivity.NAME;
import static com.example.interestcalculator.Activities.DashboardActivity.PRINCIPAL_AMOUNT;
import static com.example.interestcalculator.Activities.DashboardActivity.TOTAL_AMOUNT;
import static com.example.interestcalculator.widgets.Commons.getMonthFormat;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.databinding.FragmentAddPaymentBinding;
import com.example.interestcalculator.models.InterestModel;

import java.util.Calendar;

public class AddPaymentFragment extends Fragment {


    FragmentAddPaymentBinding binding;
    DatePickerDialog.OnDateSetListener mDateSetlistener;
    InterestModel model;
    DbHeleper dbHeleper;
    double mTotalAmount = 0;
    double remainingAmount = 0;

    String mDate,mAmount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAddPaymentBinding.inflate(getLayoutInflater());

        dbHeleper = new DbHeleper(getContext());
        binding.tvName.setText(NAME);
        binding.tvDuration.setText(DURATION);
        binding.tvPrincipleAmount.setText(PRINCIPAL_AMOUNT);
        binding.tvtotalAmount.setText(TOTAL_AMOUNT);

        mTotalAmount = Double.parseDouble(TOTAL_AMOUNT);

        initDate();

        Log.e("TAG", "onCreateView: " + mTotalAmount );
        Log.e("TAG", "onCreateView: " + ID);
        Log.e("TAG", "onCreateView: " + NAME);
        Log.e("TAG", "onCreateView: " + TOTAL_AMOUNT);
        Log.e("TAG", "onCreateView: " + PRINCIPAL_AMOUNT);
        Log.e("TAG", "onCreateView: " + DURATION);


        binding.btnCalculate.setOnClickListener(view ->{
               mDate = binding.edtInterimPayDate.getEditText().getText().toString();
               mAmount = binding.edtPayingAmount.getEditText().getText().toString();
              if (TextUtils.isEmpty(mDate)){
                  Toast.makeText(getContext(), "please select date", Toast.LENGTH_SHORT).show();
                  return;
              }
              if (TextUtils.isEmpty(mAmount)){
                  Toast.makeText(getContext(), "Please Enter amount", Toast.LENGTH_SHORT).show();
                  return;
              }

            remainingAmount = mTotalAmount - Integer.parseInt(mAmount);

              binding.remainingAmountTv.setText(""+remainingAmount);
              binding.tvtotalAmount.setText(""+remainingAmount);

        });


        binding.btnSave.setOnClickListener(view -> {
            String remark = "" ;
            remark = binding.edtRemarks.getEditText().getText().toString();

            String remaining = binding.remainingAmountTv.getText().toString();

            if(TextUtils.isEmpty(remaining)){
                Toast.makeText(getContext(), "Please Calculate interest", Toast.LENGTH_SHORT).show();
            }
            model = new InterestModel(ID,String.valueOf(System.currentTimeMillis()),PRINCIPAL_AMOUNT,DURATION,String.valueOf(mTotalAmount),mAmount,remark,String.valueOf(remainingAmount));

            if (dbHeleper.insertIntrimePayment(model)){
                 if (dbHeleper.updateTotalAmount(String.valueOf(remainingAmount),ID)){
                     Toast.makeText(requireContext(), "Record Stored Successfully", Toast.LENGTH_SHORT).show();
                 }
                 else{
                     Toast.makeText(requireContext(), "Update Error", Toast.LENGTH_SHORT).show();
                 }
            }else{
                Toast.makeText(getContext(), "an error occured", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    private void initDate() {
        binding.edtInterimPayDate.getEditText().setOnClickListener(v -> {
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

            month++;

            binding.edtInterimPayDate.getEditText().setText(dayOfMonth + "-" + month + "(" + getMonthFormat(month + 1) + ")" + "-" + year);

        };
    }
}