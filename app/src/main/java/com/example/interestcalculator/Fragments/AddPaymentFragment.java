package com.example.interestcalculator.Fragments;

import static com.example.interestcalculator.Activities.DashboardActivity.DURATION;
import static com.example.interestcalculator.Activities.DashboardActivity.ID;
import static com.example.interestcalculator.Activities.DashboardActivity.NAME;
import static com.example.interestcalculator.Activities.DashboardActivity.PRINCIPAL_AMOUNT;
import static com.example.interestcalculator.Activities.DashboardActivity.TOTAL_AMOUNT;
import static com.example.interestcalculator.widgets.Commons.getMonthFormat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.FragmentAddPaymentBinding;
import com.example.interestcalculator.models.InterestModel;

public class AddPaymentFragment extends Fragment {


    FragmentAddPaymentBinding binding;
    DatePickerDialog.OnDateSetListener mDateSetlistener;
    InterestModel model;
    DbHeleper dbHeleper;
    double mTotalAmount = 0;
    double remainingAmount = 0;

    String mDate, mAmount;

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

        Log.e("TAG", "onCreateView: " + mTotalAmount);
        Log.e("TAG", "onCreateView: " + ID);
        Log.e("TAG", "onCreateView: " + NAME);
        Log.e("TAG", "onCreateView: " + TOTAL_AMOUNT);
        Log.e("TAG", "onCreateView: " + PRINCIPAL_AMOUNT);
        Log.e("TAG", "onCreateView: " + DURATION);


        binding.btnCalculate.setOnClickListener(view -> {
            mDate = binding.edtInterimPayDate.getEditText().getText().toString();
            mAmount = binding.edtPayingAmount.getEditText().getText().toString();
            if (TextUtils.isEmpty(mDate)) {
                Toast.makeText(getContext(), "please select date", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mAmount)) {
                Toast.makeText(getContext(), "Please Enter amount", Toast.LENGTH_SHORT).show();
                return;
            }

            remainingAmount = mTotalAmount - Integer.parseInt(mAmount);

            binding.remainingAmountTv.setText("" + remainingAmount);
            binding.tvtotalAmount.setText("" + remainingAmount);


        });


        binding.btnSave.setOnClickListener(view -> {
            String remark = "";
            remark = binding.edtRemarks.getEditText().getText().toString();

            String remaining = binding.remainingAmountTv.getText().toString();

            if (TextUtils.isEmpty(remaining)) {
                Toast.makeText(getContext(), "Please Calculate interest", Toast.LENGTH_SHORT).show();
            }
            Log.e("TAG", "onCreateView: " + remainingAmount);
            model = new InterestModel(ID, String.valueOf(System.currentTimeMillis()), PRINCIPAL_AMOUNT, DURATION, String.valueOf(mTotalAmount), mAmount, remark, String.valueOf(remainingAmount));

            if (dbHeleper.insertIntrimePayment(model)) {
                if (dbHeleper.updateTotalAmount(String.valueOf(remainingAmount), ID)) {
                    Toast.makeText(requireContext(), "Record Stored Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Update Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "an error occured", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    private void initDate() {
        binding.edtInterimPayDate.getEditText().setOnClickListener(v -> {
            Dialog mDialog = new Dialog(getContext());
            View mview = getLayoutInflater().inflate(R.layout.data_picker_dialog, null);
            mDialog.setContentView(mview);
            mDialog.show();

            DatePicker mDatePicker = mview.findViewById(R.id.datePicker);
            LinearLayout btnOk = mview.findViewById(R.id.btnOk);
            LinearLayout btnCancler = mview.findViewById(R.id.btnCancler);
            TextView tvTitle = mview.findViewById(R.id.dialogTitile);

            tvTitle.setText("Pay Date");

            mDatePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                monthOfYear += 1;
                binding.edtInterimPayDate.getEditText().setText(dayOfMonth + "-" + monthOfYear + "(" + getMonthFormat(monthOfYear) + ")" + "-" + year);
            });

            btnOk.setOnClickListener(v1 -> mDialog.dismiss());
            btnCancler.setOnClickListener(v2 -> mDialog.dismiss());

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(mDialog.getWindow().getAttributes());
            layoutParams.width = 600;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mDialog.getWindow().setAttributes(layoutParams);

        });

    }
}