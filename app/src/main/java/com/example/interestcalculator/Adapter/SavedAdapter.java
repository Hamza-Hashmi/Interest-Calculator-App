package com.example.interestcalculator.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interestcalculator.databinding.CustomSavedLayoutBinding;
import com.example.interestcalculator.models.InterestModel;

import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SaveViewHolder>{

    ArrayList<InterestModel> list;

    public SavedAdapter(ArrayList<InterestModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SaveViewHolder(CustomSavedLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SaveViewHolder holder, int position) {
            InterestModel model = list.get(position);

            holder.binding.tvSavedName.setText(model.getRecordName());
            holder.binding.tvCityName.setText(model.getCityName());
            holder.binding.tvDuration.setText(model.getDurationPeriod());
            holder.binding.tvInterest.setText(model.getInterestAmount());
            holder.binding.tvInterestAmount.setText(model.getInterest());
            holder.binding.tvInterestType.setText(model.getInterestType());
            holder.binding.tvPrincipleAmount.setText(model.getPrincipalAmount());
            holder.binding.tvReturnDate.setText(model.getReturnDate());
            holder.binding.totalAmountTv.setText("Total Amount : " + model.getTotalAmount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SaveViewHolder extends RecyclerView.ViewHolder{

        CustomSavedLayoutBinding binding;
        public SaveViewHolder(@NonNull CustomSavedLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
