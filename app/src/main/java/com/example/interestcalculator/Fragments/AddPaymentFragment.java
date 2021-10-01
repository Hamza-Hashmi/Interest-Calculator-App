package com.example.interestcalculator.Fragments;

import static android.content.Context.MODE_APPEND;
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
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.FragmentAddPaymentBinding;
import com.example.interestcalculator.models.InterestModel;

import java.util.Calendar;

public class AddPaymentFragment extends Fragment {


    FragmentAddPaymentBinding binding;
    DatePickerDialog.OnDateSetListener mDateSetlistener;
    InterestModel model;
    DbHeleper dbHeleper;
    int mTotalAmount = 0;

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

        mTotalAmount = Integer.parseInt(TOTAL_AMOUNT);

        initDate();

        Log.e("TAG", "onCreateView: " + mTotalAmount );
        Log.e("TAG", "onCreateView: " + ID);
        Log.e("TAG", "onCreateView: " + NAME);
        Log.e("TAG", "onCreateView: " + TOTAL_AMOUNT);
        Log.e("TAG", "onCreateView: " + PRINCIPAL_AMOUNT);
        Log.e("TAG", "onCreateView: " + DURATION);


        binding.btnCalculate.setOnClickListener(view ->{
              String mDate = binding.edtInterimPayDate.getEditText().getText().toString();
              String mAmount = binding.edtPayingAmount.getEditText().getText().toString();
              if (TextUtils.isEmpty(mDate)){
                  Toast.makeText(getContext(), "please select date", Toast.LENGTH_SHORT).show();
                  return;
              }
              if (TextUtils.isEmpty(mAmount)){
                  Toast.makeText(getContext(), "Please Enter amount", Toast.LENGTH_SHORT).show();
                  return;
              }

              mTotalAmount = mTotalAmount - Integer.parseInt(mAmount);

              binding.remainingAmountTv.setText(""+mTotalAmount);
              binding.tvtotalAmount.setText(""+mTotalAmount);



              model = new InterestModel(ID,String.valueOf(System.currentTimeMillis()),PRINCIPAL_AMOUNT,DURATION,String.valueOf(mTotalAmount),mAmount);

                if (dbHeleper.insertIntrimePayment(model)){
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
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