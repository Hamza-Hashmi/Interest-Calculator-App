package com.example.interestcalculator.Fragments;

import static com.example.interestcalculator.widgets.Commons.*;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.DialogSelectLanguageBinding;
import com.example.interestcalculator.databinding.FragmentSettingsBinding;
import com.example.interestcalculator.databinding.LayoutCompoundMonthBinding;
import com.example.interestcalculator.models.LocalHelper;
import com.example.interestcalculator.widgets.Commons;
import com.example.interestcalculator.widgets.SharedHelper;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        String months = SharedHelper.getKey(requireContext(),COMPOUND_MONTHS);
        if (months.equals("")){
            binding.monthTv.setText("12");
        }else{
            binding.monthTv.setText(months);
        }


        String mLanugage = SharedHelper.getKey(requireContext(),LANGUAGE);
        if (mLanugage.equals("")){
            binding.tvLanguage.setText("Englis");
        }else{
            binding.tvLanguage.setText(mLanugage);
        }

        if (SharedHelper.getKey(requireContext(),PERCENT_SWITCH).equalsIgnoreCase("true")){
            binding.switchPercent.setChecked(true);
            binding.intersetPercentTv.setText(getString(R.string.interest_rate_considered_as_annum));
        }
        if (SharedHelper.getKey(requireContext(),ALLOW_FRACTION).equalsIgnoreCase("true")){
            binding.switchFraction.setChecked(true);
            binding.tvFraction.setText(getString(R.string.allow_fraction));
        }
        if (SharedHelper.getKey(requireContext(),COMPOUND_EXEMPTIONS).equalsIgnoreCase("true")){
            binding.switchCopmoundConseption.setChecked(true);
            binding.tvCompoundExemption.setVisibility(View.VISIBLE);
            binding.tvCompoundExemption.setText(getString(R.string.compound_interest_will_not_be));
        }

        binding.btnRate.setOnClickListener(v -> rate_us());
        binding.btnShare.setOnClickListener(v -> ShowShare());

        binding.switchPercent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.intersetPercentTv.setText(getString(R.string.interest_rate_considered_as_annum));
                    SharedHelper.putKey(requireContext(), PERCENT_SWITCH,"true");
                } else {
                    binding.intersetPercentTv.setText(getString(R.string.interest_rate_consider_per_month_in_rupees));
                    SharedHelper.putKey(requireContext(), PERCENT_SWITCH,"false");

                }
            }
        });

        binding.switchFraction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.tvFraction.setText(getString(R.string.allow_fraction));
                    SharedHelper.putKey(requireContext(), ALLOW_FRACTION,"true");
                } else {
                    binding.tvFraction.setText(getString(R.string.calculation_rounds_the_value));
                    SharedHelper.putKey(requireContext(), ALLOW_FRACTION,"false");

                }
            }
        });

        binding.switchCopmoundConseption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.tvCompoundExemption.setVisibility(View.VISIBLE);
                    binding.tvCompoundExemption.setText(getString(R.string.compound_interest_will_not_be));
                    SharedHelper.putKey(requireContext(), COMPOUND_EXEMPTIONS,"true");
                } else {
                    binding.tvCompoundExemption.setVisibility(View.GONE);
                    binding.tvCompoundExemption.setText(getString(R.string.last_year_month_amp_days_compound_exemptions));
                    SharedHelper.putKey(requireContext(), COMPOUND_EXEMPTIONS,"false");
                }
            }
        });
        
        binding.btnCompoundInterestPeriod.setOnClickListener(v->{
            String month = binding.monthTv.getText().toString();
            
            showDialoge(month);
        });

        binding.btnLangauge.setOnClickListener(v->{
            showLanguageDialoge();
        });

        return binding.getRoot();
    }

    private void showDialoge(String month) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
       LayoutCompoundMonthBinding mBinding = LayoutCompoundMonthBinding.inflate(getLayoutInflater());
       builder.setView(mBinding.getRoot());

       AlertDialog dialog = builder.create();

       mBinding.edtMonths.setText(month);

       mBinding.btnCancel.setOnClickListener(view -> dialog.dismiss());
       mBinding.btnOk.setOnClickListener(view -> {
                        String mMonth = mBinding.edtMonths.getText().toString();
                        SharedHelper.putKey(requireContext(),COMPOUND_MONTHS,mMonth);
                        binding.monthTv.setText(mMonth);
                        dialog.dismiss();
               });


       dialog.show();

    }

    private void showLanguageDialoge() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DialogSelectLanguageBinding mBinding = DialogSelectLanguageBinding.inflate(getLayoutInflater());
        builder.setView(mBinding.getRoot());

        AlertDialog dialog = builder.create();


        mBinding.btnHindi.setOnClickListener(v->{
            LocalHelper.setLocale(requireContext(),"hi");
            binding.tvLanguage.setText("Hindi");
            SharedHelper.putKey(requireContext(),LANGUAGE,"Hindi");
            dialog.dismiss();
        });

        mBinding.btnKannada.setOnClickListener(v->{
            LocalHelper.setLocale(requireContext(),"kn");
            SharedHelper.putKey(requireContext(),LANGUAGE,"Kannada");
            binding.tvLanguage.setText("Kannada");

            dialog.dismiss();
        });

        mBinding.btnMalaylam.setOnClickListener(v->{
            LocalHelper.setLocale(requireContext(),"ml");
            SharedHelper.putKey(requireContext(),LANGUAGE,"Malaylam");
            binding.tvLanguage.setText("Malaylam");

            dialog.dismiss();
        });

        mBinding.btnTelugu.setOnClickListener(v->{
            LocalHelper.setLocale(requireContext(),"te");
            SharedHelper.putKey(requireContext(),LANGUAGE,"Telugu");
            binding.tvLanguage.setText("Telugu");
            dialog.dismiss();
        });


        dialog.show();

    }


    public void ShowShare() {
        Intent myintent = new Intent(Intent.ACTION_SEND);
        myintent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
        String shareTitle = "Check it Out: " + getResources().getString(R.string.app_name);
        myintent.putExtra(Intent.EXTRA_TEXT, shareTitle + "\t\n" + shareBody);
        startActivity(Intent.createChooser(myintent, "Share Using"));
    }

    public void rate_us() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
    }

}