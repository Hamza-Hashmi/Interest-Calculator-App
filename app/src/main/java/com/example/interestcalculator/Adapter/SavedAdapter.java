package com.example.interestcalculator.Adapter;

import static com.example.interestcalculator.Activities.DashboardActivity.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interestcalculator.Activities.DashboardActivity;
import com.example.interestcalculator.Activities.IntrimePayments;
import com.example.interestcalculator.Fragments.AddPaymentFragment;
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.CustomSavedLayoutBinding;
import com.example.interestcalculator.models.InterestModel;

import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SaveViewHolder>{

    ArrayList<InterestModel> list;
    Context context;

    public SavedAdapter(ArrayList<InterestModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SaveViewHolder(CustomSavedLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @SuppressLint("RestrictedApi")
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

            holder.binding.btnMenu.setOnClickListener(view -> {
                 MenuBuilder menuBuilder =new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.popup_menu, menuBuilder);
                 MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);

                // Set Item Click Listener
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.share: // Handle option1 Click
                               shareData(model);
                                return true;
                            case R.id.edt:
                                Log.e("TAG", "onMenuItemSelected: edit");
                                return true;

                            case R.id.intrimePayment:

                                ID = model.getId();
                                NAME = model.getRecordName();
                                TOTAL_AMOUNT = model.getTotalAmount();
                                PRINCIPAL_AMOUNT = model.getPrincipalAmount();
                                DURATION = model.getDurationPeriod();

                                /*
                                Bundle bundle = new Bundle();
                                String id = model.getId();
                                bundle.putString("id",id);
                                AddPaymentFragment APF = new AddPaymentFragment();
                                APF.setArguments(bundle);
*/
                                context.startActivity(new Intent(context, IntrimePayments.class));
                                Log.e("TAG", "onMenuItemSelected: intrimePayment");
                                return true;
                            case R.id.transection:
                                Log.e("TAG", "onMenuItemSelected: transactions");
                                return true;


                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });


                // Display the menu
                optionsMenu.show();
            });

    }

    private void shareData(InterestModel model) {

        Intent myintent = new Intent(Intent.ACTION_SEND);
        myintent.setType("text/plain");

        String shareBody = "Principal Amount : " + model.getPrincipalAmount() + "\n"+
                "Give Date : " + model.getGivenDate() + "\n"+
                "Return Date : " + model.getReturnDate() + "\n" +
                "Duration : " + model.getDurationPeriod() + "\n" +
                "Interest Rate : " + model.getInterestAmount() + ".0" + "\n"+
                "Interest : " + "-" + model.getInterest() + "\n" +
                "Total Amount : " + model.getTotalAmount() + "\n"+
                 model.getInterestType() + "\n\n" +
                "This was shared via Interest Calculator App \n"+
                "Android App "+"https://play.google.com/store/apps/details?id=" + context.getPackageName();
        myintent.putExtra(Intent.EXTRA_TEXT, "Name : " + model.getRecordName() + "\n" + "----------------------- \n" + "\t\n" + shareBody);
        context.startActivity(Intent.createChooser(myintent, "Share via"));

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
