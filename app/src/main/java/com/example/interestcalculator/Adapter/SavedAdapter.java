package com.example.interestcalculator.Adapter;

import static com.example.interestcalculator.Activities.DashboardActivity.DURATION;
import static com.example.interestcalculator.Activities.DashboardActivity.ID;
import static com.example.interestcalculator.Activities.DashboardActivity.NAME;
import static com.example.interestcalculator.Activities.DashboardActivity.PRINCIPAL_AMOUNT;
import static com.example.interestcalculator.Activities.DashboardActivity.TOTAL_AMOUNT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interestcalculator.Activities.IntrimePayments;
import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.Fragments.UpdateFragment;
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.CustomSavedLayoutBinding;
import com.example.interestcalculator.models.InterestModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SaveViewHolder> {

    ArrayList<InterestModel> list;
    Context context;
    DbHeleper dbHeleper;

    public SavedAdapter(ArrayList<InterestModel> list, Context context) {
        this.list = list;
        this.context = context;
        dbHeleper = new DbHeleper(context);
    }

    @NonNull
    @Override
    public SaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SaveViewHolder(CustomSavedLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
            MenuBuilder menuBuilder = new MenuBuilder(context);
            MenuInflater inflater = new MenuInflater(context);
            inflater.inflate(R.menu.popup_menu, menuBuilder);
            MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
            optionsMenu.setForceShowIcon(true);

            // Set Item Click Listener
            menuBuilder.setCallback(new MenuBuilder.Callback() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    boolean result = true;
                    switch (item.getItemId()) {
                        case R.id.share: // Handle option1 Click
                            shareData(model);
                            break;
                        case R.id.edt:

                            Bundle bundle = new Bundle();
                            bundle.putString("id", model.getId());
                            bundle.putString("pm", model.getPrincipalAmount());
                            bundle.putString("interest", model.getInterestAmount());
                            bundle.putString("gd", model.getGivenDate());
                            bundle.putString("rd", model.getReturnDate());

                            AppCompatActivity activity = (AppCompatActivity) view.getContext();
                            UpdateFragment uf = new UpdateFragment();
                            uf.setArguments(bundle);

                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, uf).commit();

                            //}
                            break;

                        case R.id.intrimePayment:

                            ID = model.getId();
                            NAME = model.getRecordName();
                            TOTAL_AMOUNT = model.getTotalAmount();
                            PRINCIPAL_AMOUNT = model.getPrincipalAmount();
                            DURATION = model.getDurationPeriod();

                            context.startActivity(new Intent(context, IntrimePayments.class));
                            Log.e("TAG", "onMenuItemSelected: intrimePayment");
                            break;
                        case R.id.transection:
                            Log.e("TAG", "onMenuItemSelected: transactions");
                            break;
                        case R.id.pdf:
                            try {
                                createPDF();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            result = false;
                            break;
                    }

                    return result;
                }

                @Override
                public void onMenuModeChange(@NonNull MenuBuilder menu) {
                }
            });

            optionsMenu.show();
        });

    }

    private void shareData(InterestModel model) {

        Intent myintent = new Intent(Intent.ACTION_SEND);
        myintent.setType("text/plain");

        String shareBody = "Principal Amount : " + model.getPrincipalAmount() + "\n" +
                "Give Date : " + model.getGivenDate() + "\n" +
                "Return Date : " + model.getReturnDate() + "\n" +
                "Duration : " + model.getDurationPeriod() + "\n" +
                "Interest Rate : " + model.getInterestAmount() + ".0" + "\n" +
                "Interest : " + "-" + model.getInterest() + "\n" +
                "Total Amount : " + model.getTotalAmount() + "\n" +
                model.getInterestType() + "\n\n" +
                "This was shared via Interest Calculator App \n" +
                "Android App " + "https://play.google.com/store/apps/details?id=" + context.getPackageName();
        myintent.putExtra(Intent.EXTRA_TEXT, "Name : " + model.getRecordName() + "\n" + "----------------------- \n" + "\t\n" + shareBody);
        context.startActivity(Intent.createChooser(myintent, "Share via"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SaveViewHolder extends RecyclerView.ViewHolder {

        CustomSavedLayoutBinding binding;

        public SaveViewHolder(@NonNull CustomSavedLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    //    PDF Created over Here
    public void createPDF() throws FileNotFoundException {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f);
        Document document = new Document();
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis()));
        String str = Environment.getExternalStorageDirectory() + "/" + format + ".pdf";
        try {

            PdfWriter.getInstance(document, new FileOutputStream(str));
            document.open();
            Paragraph titleParagraph = new Paragraph("Interest Calculator App", new Font(Font.FontFamily.HELVETICA, 18, 1));
            document.add(titleParagraph);

            document.add(new Paragraph(" This PDF is created through Through\n" +
                    " Interest Calculator App  \n", new Font(Font.FontFamily.HELVETICA, 12.0f, 1)));
            document.add(new Paragraph("\n"));


            document.add(getExpenses());
            document.addAuthor("Interset calculator");
            document.close();
            Toast.makeText(context, format + ".pdf\nis saved to\n" + str, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public PdfPTable getExpenses() throws DocumentException {
        PdfPTable pdfPTable = new PdfPTable(10);
        float width[] = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
        pdfPTable.addCell(getpdfcell("Serial no"));
        pdfPTable.addCell(getpdfcell("Name"));
        pdfPTable.addCell(getpdfcell("City"));
        pdfPTable.addCell(getpdfcell("Given Date"));
        pdfPTable.addCell(getpdfcell("Return Date"));
        pdfPTable.addCell(getpdfcell("Duration"));
        pdfPTable.addCell(getpdfcell("Interest"));
        pdfPTable.addCell(getpdfcell("Interest Type"));
        pdfPTable.addCell(getpdfcell("Interest Amount"));
        pdfPTable.addCell(getpdfcell("Total Amount"));
        pdfPTable.setWidths(width);

        pdfPTable.setHeaderRows(1);
        int serialNo = 0;
        int grandTotal = 0;
        for (InterestModel model : list) {
            serialNo++;
            grandTotal += grandTotal;
            pdfPTable.addCell(String.valueOf(serialNo));
            pdfPTable.addCell(model.getRecordName());
            pdfPTable.addCell(model.getCityName());
            pdfPTable.addCell(model.getGivenDate());
            pdfPTable.addCell(model.getReturnDate());
            pdfPTable.addCell(model.getDurationPeriod());
            pdfPTable.addCell(model.getInterest());
            pdfPTable.addCell(model.getInterestType());
            pdfPTable.addCell(model.getInterestAmount());
            pdfPTable.addCell(model.getTotalAmount());
        }
        pdfPTable.addCell(getpdfcell("Total Amount"));
        pdfPTable.addCell(getpdfcell(String.valueOf(grandTotal)));
        return pdfPTable;
    }

    public PdfPCell getpdfcell(String name) {
        PdfPCell pdfPCell2 = new PdfPCell(new Phrase(name));
        pdfPCell2.setHorizontalAlignment(1);
        return pdfPCell2;
    }
}
