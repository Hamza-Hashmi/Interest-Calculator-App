package com.example.interestcalculator.Fragments;

import static com.example.interestcalculator.Activities.DashboardActivity.DURATION;
import static com.example.interestcalculator.Activities.DashboardActivity.ID;
import static com.example.interestcalculator.Activities.DashboardActivity.NAME;
import static com.example.interestcalculator.Activities.DashboardActivity.PRINCIPAL_AMOUNT;
import static com.example.interestcalculator.Activities.DashboardActivity.TOTAL_AMOUNT;
import static com.example.interestcalculator.Adapter.IntrimeHistoryAdapter.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.interestcalculator.Activities.IntrimePayments;
import com.example.interestcalculator.Adapter.IntrimeHistoryAdapter;
import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.FragmentViewTransactionBinding;
import com.example.interestcalculator.models.InterestModel;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;


import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class ViewTransactionFragment extends Fragment {

    FragmentViewTransactionBinding binding;
    DbHeleper dbHeleper;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.pdf_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnPDF) {
            try {
                createPDF();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentViewTransactionBinding.inflate(getLayoutInflater());

        dbHeleper = new DbHeleper(getContext());

        ArrayList<InterestModel> list = new ArrayList<>(dbHeleper.getIntrimeHistory(ID));
        //list.forEach((listt) -> Log.e("TAG", "onCreateView: " + listt.getFid()));

        IntrimeHistoryAdapter adapter = new IntrimeHistoryAdapter(list, getContext());
        binding.transactionRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        binding.btnMenu.setOnClickListener(view -> {
            @SuppressLint("RestrictedApi") MenuBuilder menuBuilder =new MenuBuilder(getContext());
            MenuInflater minflater = new MenuInflater(getContext());
            minflater.inflate(R.menu.pdf_menu, menuBuilder);
            @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(getContext(), menuBuilder, view);
            optionsMenu.setForceShowIcon(true);

            // Set Item Click Listener
            menuBuilder.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.btnPDF: // Handle option1 Click
                            try {
                                createPDF();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            return true;


                        default:
                            return false;
                    }

                }

                @Override
                public void onMenuModeChange(@NonNull MenuBuilder menu) {}
            });


            // Display the menu
            optionsMenu.show();
        });


        return binding.getRoot();
    }

    //Adding the content to the document


    /*public void layoutToImage(View view) {
        // get view group using reference
        //relativeLayout = (RelativeLayout) view.findViewById(R.id.print);
        // convert view group to bitmap
        binding.transactionRv.setDrawingCacheEnabled(true);
        binding.transactionRv.buildDrawingCache();
        Bitmap bm = binding.transactionRv.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        try {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
*/
    public void createPDF() throws FileNotFoundException {
        try {
            File pdfFile = Environment.getExternalStorageDirectory();
            Log.e("TAG", "createPDF: " + pdfFile );
            File file = new File(pdfFile.getPath(), System.currentTimeMillis() + "INterim.pdf");

            PdfWriter pdfWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDocument);

            pdfDocument.setDefaultPageSize(PageSize.A4);
            float columWidth[] = {140, 140, 140, 140};
            Paragraph titile = new Paragraph("Interim Payment")
                    .setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER);

            Paragraph subHeading = new Paragraph("PDF Created in Interest Calculator App")
                    .setFontSize(14).setTextAlignment(TextAlignment.CENTER);


            Table table = new Table(columWidth);

            table.addCell(new Cell().add(new Paragraph("Serial No")));
            table.addCell(new Cell().add(new Paragraph("Name")));
            table.addCell(new Cell().add(new Paragraph("Given and Return Date")));
            table.addCell(new Cell().add(new Paragraph("Duration")));
            table.addCell(new Cell().add(new Paragraph("Interest Rate")));
            table.addCell(new Cell().add(new Paragraph("Interest")));
            table.addCell(new Cell().add(new Paragraph("Interest Type")));
            table.addCell(new Cell().add(new Paragraph("Total Amount")));

            int serialNO = 0;

            for (InterestModel model : list) {
                serialNO++;
                table.addCell(new Cell().add(new Paragraph(String.valueOf(serialNO))));
                table.addCell(new Cell().add(new Paragraph(model.getCityName())));
                table.addCell(new Cell().add(new Paragraph(model.getGivenDate() + "," + model.getReturnDate())));
                table.addCell(new Cell().add(new Paragraph(model.getDurationPeriod())));
                table.addCell(new Cell().add(new Paragraph(model.getInterest())));
                table.addCell(new Cell().add(new Paragraph(model.getInterestAmount())));
                table.addCell(new Cell().add(new Paragraph(model.getInterestType())));
                table.addCell(new Cell().add(new Paragraph(model.getTotalAmount())));
            }

            document.add(titile);
            document.add(subHeading);
            document.add(table);

            Toast.makeText(getContext(), file.getAbsolutePath(), Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Log.e("TAG", "createPDF:exception " + e.getMessage() );

        }

    }
}