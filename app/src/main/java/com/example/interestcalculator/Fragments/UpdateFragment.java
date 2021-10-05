package com.example.interestcalculator.Fragments;

import static com.example.interestcalculator.widgets.Commons.getMonthFormat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.CustomSaveDialougeBinding;
import com.example.interestcalculator.databinding.FragmentUpdateBinding;
import com.example.interestcalculator.models.InterestModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateFragment extends Fragment {

    FragmentUpdateBinding binding;
    String id;
    DbHeleper dbHeleper;
    String interestType = "Simple";
    DatePickerDialog.OnDateSetListener mDateSetlistener, mRDateListener;

    InterestModel interestModel;
    String startDate,endDate;
    long duration,mDuration;
    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentUpdateBinding.inflate(getLayoutInflater());
        dbHeleper = new DbHeleper(getContext());
        initGivenDate();
        initReturnDate();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

             id=bundle.getString("id");
            String pm = bundle.getString("pm");
            String intrst = bundle.getString("interest");
            String gd = bundle.getString("gd");
            String rd = bundle.getString("rd");

            binding.edtPrincipleAmount.getEditText().setText(pm);
            binding.edtIntersrPerMonth.getEditText().setText(intrst);
            binding.edtGivenDate.getEditText().setText(gd);
            binding.edtReturnDate.getEditText().setText(rd);
            //value2=bundle.getString("key_name2","");
        }


        binding.btnCalculate.setOnClickListener(v ->{


            String pricipalAmout = binding.edtPrincipleAmount.getEditText().getText().toString();
            String interestAmountPerMonth = binding.edtIntersrPerMonth.getEditText().getText().toString();
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

                    calculateAndUpdateSimpleInterest(pricipalAmout,interestAmountPerMonth);


                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("TAG", "onCreateView: timeDuration " + e.getMessage() );
                }
            }




            // dbHeleper.updateHistory(id,);
        });

        binding.interestRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.interestSimpleRadio: {
                        interestType = "Simple";
                        break;
                    }
                    case R.id.interestCompountRadio:{
                        interestType = "Compound 12M";
                    }
                }
            }
        });
    }

    private void calculateAndUpdateSimpleInterest(String pricipalAmout, String interestAmountPerMonth) {

        long amount =  Long.parseLong(pricipalAmout);
        long interstAmount =Long.parseLong(interestAmountPerMonth);

        Log.e("TAG", "calculateAndSaveSimpleInterest: duration"  + getIntrestDuration(duration) );
        long interest = ((amount * interstAmount) / 100) * mDuration;
        long totalAmount = Long.parseLong(pricipalAmout) + interest;

        try {

            interestModel = new InterestModel(String.valueOf(System.currentTimeMillis()),startDate,endDate,pricipalAmout,getIntrestDuration(duration),String.valueOf(interest),interestAmountPerMonth,interestType,String.valueOf(totalAmount));
            Log.e("TAG", "onCreateView: " + interestModel.toString() );
            if (dbHeleper.updateHistory(id,interestModel)){

                binding.bottomLayout.setVisibility(View.VISIBLE);
                binding.durationTv.setText("Duration: " + getIntrestDuration(duration));
                binding.intersetTv.setText("Interest: " +interest);
                binding.totalAmountTv.setText("Total Amount: " + totalAmount);
                binding.interestTypeTv.setText(interestType);

                binding.btnSave.setOnClickListener(view ->{

                    showDialouge(pricipalAmout,interest,totalAmount,interestAmountPerMonth);

                });


            }else{
                Toast.makeText(getContext(), "An Error Occured ", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Log.e("TAG", "insert exception " + e.getMessage() );
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

    private void showDialouge(String pricipalAmout, double interest, double totalAmount, String interestAmountPerMonth){
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

                if (dbHeleper.updateInterst(id,interestModel)){
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

    @SuppressLint("SetTextI18n")
    private void initReturnDate() {

        binding.edtReturnDate.getEditText().setOnClickListener(v -> {
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
            binding.edtReturnDate.getEditText().setText(dayOfMonth + "-" + month + "(" + getMonthFormat(month + 1) + ")" + "-" + year);
            endDate = dayOfMonth +  "/" + month +"/"+year;

        };
    }

    //    ! -> Given DatePicker
    @SuppressLint("SetTextI18n")
    private void initGivenDate() {
        binding.edtGivenDate.getEditText().setOnClickListener(v -> {
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

            binding.edtGivenDate.getEditText().setText(dayOfMonth + "-" + month + "(" + getMonthFormat(month + 1) + ")" + "-" + year);
            startDate = dayOfMonth  + "/" + month +"/"+year;
        };

    }
}