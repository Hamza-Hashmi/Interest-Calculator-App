package com.example.interestcalculator.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.databinding.CustomHistoryLayoutBinding;
import com.example.interestcalculator.models.InterestModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    ArrayList<InterestModel> list;
    Context context;
    DbHeleper dbHeleper;

    public HistoryAdapter(ArrayList<InterestModel> list, Context context) {
        this.list = list;
        this.context = context;

        dbHeleper = new DbHeleper(context);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(CustomHistoryLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        InterestModel model = list.get(position);

        holder.binding.tvGivendate.setText(model.getGivenDate());
        holder.binding.tvDuration.setText(model.getDurationPeriod());
        holder.binding.tvInterest.setText(model.getInterestAmount());
        holder.binding.tvInterestAmount.setText(model.getInterest());
        holder.binding.tvInterestType.setText(model.getInterestType());
        holder.binding.tvPrincipleAmount.setText(model.getPrincipalAmount());
        holder.binding.tvReturnDate.setText(model.getReturnDate());
        holder.binding.totalAmountTv.setText("Total Amount : " + model.getTotalAmount());

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(model.getCurrentDate()));
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();

        holder.binding.savedDate.setText(date);

        holder.binding.btnDeleteHistory.setOnClickListener(view -> {
            Log.e("TAG", "onBindViewHolder: delete clicked");
            dbHeleper.deleteHistory(Integer.parseInt(model.getId()));
            list.remove(position);
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        CustomHistoryLayoutBinding binding;

        public HistoryViewHolder(@NonNull CustomHistoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
