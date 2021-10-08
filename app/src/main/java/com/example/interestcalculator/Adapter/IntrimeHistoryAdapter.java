package com.example.interestcalculator.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interestcalculator.databinding.CustomIntrimeRowBinding;
import com.example.interestcalculator.models.InterestModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class IntrimeHistoryAdapter extends RecyclerView.Adapter<IntrimeHistoryAdapter.MyViewHolder>{

  public static ArrayList<InterestModel> list;
  Context context;
  static ArrayList<ConstraintLayout> mList;

    public IntrimeHistoryAdapter(ArrayList<InterestModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomIntrimeRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InterestModel model = list.get(position);
        holder.binding.principalAmountTv.setText("Principal Amount "+model.getPrincipalAmount());
        holder.binding.totalAmountTv.setText(model.getTotalAmount());
        holder.binding.tvIntrimePayment.setText(model.getIntrimePayment());

        Log.e("TAG", "onBindViewHolder: " +model.getIntrimePayment());
        holder.binding.tvRemainingAmount.setText(model.getRemianingAmount());



        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(model.getCurrentDate()));
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();


        holder.binding.tvSubmitDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CustomIntrimeRowBinding binding;
        public MyViewHolder(@NonNull CustomIntrimeRowBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }


}
