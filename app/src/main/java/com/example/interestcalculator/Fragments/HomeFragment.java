package com.example.interestcalculator.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.CustomSaveDialougeBinding;
import com.example.interestcalculator.databinding.FragmentHomeBinding;
import com.example.interestcalculator.models.InterestModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    FragmentHomeBinding homeBinding;
    DatePickerDialog.OnDateSetListener mDateSetlistener, mRDateListener;
    String startDate,endDate;
    long duration,mDuration;
    String interestType = "Simple";
    DbHeleper dbHeleper;
    InterestModel interestModel;
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

        dbHeleper = new DbHeleper(requireContext());

        homeBinding.interestRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.interestSimpleRadio: {
                        interestType = "Simple";
                        break;
                    }
                    case R.id.interestCompountRadio:{
                        interestType = "Compound";
                    }
                }
            }
        });

        homeBinding.btnCalculate.setOnClickListener(view ->{
            String pricipalAmout = homeBinding.edtPrincipleAmount.getEditText().getText().toString();
            String interestAmountPerMonth = homeBinding.edtIntersrPerMonth.getEditText().getText().toString();
            SimpleDateFormat sdf
                    = new SimpleDateFormat(
                    "dd/MM/yyyy");




            if (TextUtils.isEmpty(pricipalAmout) || TextUtils.isEmpty(interestAmountPerMonth)){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }else{
                try {
                    duration = 0;
                    Date date1 = sdf.parse(startDate);
                    Date date2 = sdf.parse(endDate);
                    Log.e("TAG", "onCreateView: " + date1 );
                    Log.e("TAG", "onCreateView: " + date2 );
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    duration = difference / (24 * 60 * 60 * 1000);
                    calculateAndSaveSimpleInterest(pricipalAmout,interestAmountPerMonth);

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("TAG", "onCreateView: timeDuration " + e.getMessage() );
                }
            }


        });


        return homeBinding.getRoot();

    }

    private void calculateAndSaveSimpleInterest(String pricipalAmout, String interestAmountPerMonth) {

         long amount =  Long.parseLong(pricipalAmout);
         long interstAmount =Long.parseLong(interestAmountPerMonth);

        Log.e("TAG", "calculateAndSaveSimpleInterest: duration"  + getIntrestDuration(duration) );
        long interest = ((amount * interstAmount) / 100) * mDuration;
        long totalAmount = Long.parseLong(pricipalAmout) + interest;

        try {

            interestModel = new InterestModel(String.valueOf(System.currentTimeMillis()),startDate,endDate,pricipalAmout,getIntrestDuration(duration),String.valueOf(interest),interestAmountPerMonth,interestType,String.valueOf(totalAmount));
            Log.e("TAG", "onCreateView: " + interestModel.toString() );
            if (dbHeleper.addNewHistory(interestModel)){

                homeBinding.bottomLayout.setVisibility(View.VISIBLE);
                homeBinding.durationTv.setText("Duration: " + getIntrestDuration(duration));
                homeBinding.intersetTv.setText("Interest: " +interest);
                homeBinding.totalAmountTv.setText("Total Amount: " + totalAmount);
                homeBinding.interestTypeTv.setText(interestType);

                homeBinding.btnSave.setOnClickListener(view ->{

                    showDialouge(pricipalAmout,interest,totalAmount,interestAmountPerMonth);

                });


            }else{
                Toast.makeText(getContext(), "An Error Occured ", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Log.e("TAG", "insert exception " + e.getMessage() );
        }

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
            month++;
            homeBinding.edtReturnDate.getEditText().setText(dayOfMonth + "-" + month + "(" + getMonthFormat(month + 1) + ")" + "-" + year);
            endDate = dayOfMonth +  "/" + month +"/"+year;

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

            month++;

            homeBinding.edtGivenDate.getEditText().setText(dayOfMonth + "-" + month + "(" + getMonthFormat(month + 1) + ")" + "-" + year);
            startDate = dayOfMonth  + "/" + month +"/"+year;
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

    private String getIntrestDuration(long duration){

          long days = duration;

        Log.e("TAG", "getIntrestDuration: " + days );

          long totalMonthsInDays = days/30;

        Log.e("TAG", "totalMonths: " + totalMonthsInDays );

        long totalYear = totalMonthsInDays/12;
        Log.e("TAG", "totalYear: " + totalYear );


        if (days %30 == 0){
            days = 0;

            Log.e("TAG", "totalMonths reminder: " + totalMonthsInDays );

        }
          if (totalMonthsInDays %12 == 0){
              totalMonthsInDays = 0;

              Log.e("TAG", "totalMonths reminder: " + totalMonthsInDays );

          }
          if (totalYear >= 1 && totalMonthsInDays >=1 ){
              Log.e("TAG", "totalMonths and total year: ");
              mDuration = totalYear + totalMonthsInDays;
              return totalYear + " year " + totalMonthsInDays + " month";

          }
          else if (totalYear >=1){
              mDuration = totalYear;
               return totalYear + " year ";
          }
          else if (totalMonthsInDays>=1 && days >=1 ){
              Log.e("TAG", "getIntrestDuration: " + totalMonthsInDays );
              mDuration = totalMonthsInDays + 0;
              return  totalMonthsInDays + " month ";
          }
          else if (totalMonthsInDays>=1){
              mDuration = totalMonthsInDays;
              return  totalMonthsInDays + " month ";
          }
           mDuration = days;
          return days + " days";
    }



    private void showDialouge(String pricipalAmout, long interest, long totalAmount, String interestAmountPerMonth){
        CustomSaveDialougeBinding mBinding;

        mBinding = CustomSaveDialougeBinding.inflate(getLayoutInflater());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(mBinding.getRoot());


        AlertDialog dialog = builder.create();

        mBinding.btnCancel.setOnClickListener(view -> dialog.dismiss());

        mBinding.btnSaveRecord.setOnClickListener(view -> {
            String cityName = mBinding.cityNameEt.getText().toString();
            String recordName = mBinding.recordNameEt.getText().toString();
            String remark = mBinding.remarksEt.getText().toString();
            if (TextUtils.isEmpty(cityName ) || TextUtils.isEmpty(recordName) || TextUtils.isEmpty(remark)){
                dialog.dismiss();
                Toast.makeText(requireContext(), "Data not saved", Toast.LENGTH_SHORT).show();
            }
            else{

                interestModel = new InterestModel(String.valueOf(System.currentTimeMillis()),startDate,endDate,pricipalAmout,getIntrestDuration(duration),String.valueOf(interest),interestAmountPerMonth,interestType,String.valueOf(totalAmount),recordName,cityName,remark);

                if (dbHeleper.saveInterest(interestModel)){
                    Toast.makeText(requireContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

        });
        dialog.show();
    }
}