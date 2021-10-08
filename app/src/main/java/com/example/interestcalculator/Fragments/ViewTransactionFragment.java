package com.example.interestcalculator.Fragments;

import static com.example.interestcalculator.Activities.DashboardActivity.ID;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.Fragment;

import com.example.interestcalculator.Adapter.IntrimeHistoryAdapter;
import com.example.interestcalculator.DbHeleper;
import com.example.interestcalculator.R;
import com.example.interestcalculator.databinding.FragmentViewTransactionBinding;
import com.example.interestcalculator.models.InterestModel;
import com.itextpdf.text.Document;
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
            @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(getContext());
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
                public void onMenuModeChange(@NonNull MenuBuilder menu) {
                }
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

        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f);
        Document document = new Document();
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis()));
        String str = Environment.getExternalStorageDirectory() + "/" + format + ".pdf";
        try {
            PdfWriter.getInstance(document, new FileOutputStream(str));
            document.open();
            Paragraph titleParagraph = new Paragraph("Interest Calculator App");
            document.add(titleParagraph);
            document.add(new Paragraph("This PDF is created through Through Interest Calculator App \n", new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, 1)));
            document.add(getExpenses());
            document.addAuthor("Interset calculator");
            document.close();
            Toast.makeText(getContext(), format + ".pdf\nis saved to\n" + str, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getContext(), "working ", Toast.LENGTH_SHORT).show();

    }

    public PdfPTable getExpenses() {
        PdfPTable pdfPTable = new PdfPTable(6);

        pdfPTable.addCell(getpdfcell("Serial no"));
        pdfPTable.addCell(getpdfcell("Intrime Payment"));
        pdfPTable.addCell(getpdfcell("Given date"));
        pdfPTable.addCell(getpdfcell("Duration Period"));
        pdfPTable.addCell(getpdfcell("Remaining Amount"));
        pdfPTable.addCell(getpdfcell("Principal Amount"));

        pdfPTable.setHeaderRows(1);

        for (InterestModel model : com.example.interestcalculator.Adapter.IntrimeHistoryAdapter.list) {
            pdfPTable.addCell("serialNO");
            pdfPTable.addCell(model.getIntrimePayment());
            pdfPTable.addCell(model.getGivenDate());
            pdfPTable.addCell(model.getDurationPeriod());
            pdfPTable.addCell(model.getRemianingAmount());
            pdfPTable.addCell(model.getPrincipalAmount());
        }

        return pdfPTable;
    }

    public PdfPCell getpdfcell(String name) {
        PdfPCell pdfPCell2 = new PdfPCell(new Phrase(name));
        pdfPCell2.setHorizontalAlignment(1);
        return pdfPCell2;
    }
}


//        try {
//            File pdfFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            Log.e("TAG", "createPDF: " + pdfFile);
//            File file = new File(pdfFile, System.currentTimeMillis() + "Interim.pdf");
//
////            PdfWriter pdfWriter = new PdfWriter(file);
////            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
////            Document document = new Document(pdfDocument);
////
////
////            pdfDocument.setDefaultPageSize(PageSize.A4);
////            float columWidth[] = {120, 120, 120, 120, 120, 120, 120, 120};
////            Paragraph titile = new Paragraph("Interim Payment")
////                    .setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER);
////
////            Paragraph subHeading = new Paragraph("PDF Created in Interest Calculator App")
////                    .setFontSize(14).setTextAlignment(TextAlignment.CENTER);
//
//
////            Table table = new Table(columWidth);
//
////            table.addCell(new Cell().add(new Paragraph("S No").setFontSize(12)));
////            table.addCell(new Cell().add(new Paragraph("Intrime Payment").setFontSize(12)));
////            table.addCell(new Cell().add(new Paragraph("Pay Date").setFontSize(12)));
////            table.addCell(new Cell().add(new Paragraph("Duration").setFontSize(12)));
////            table.addCell(new Cell().add(new Paragraph("Remaining Amount").setFontSize(12)));
////            table.addCell(new Cell().add(new Paragraph("Principal Amount").setFontSize(12)));
//
////            int serialNO = 0;
//
//
////            document.add(titile);
////            document.add(subHeading);
////            document.add(table);
////            document.close();
//
////            Toast.makeText(getContext(), file.toString(), Toast.LENGTH_SHORT).show();
//
//
//        } catch (Exception e) {
//            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.e("TAG", "createPDF:exception " + e.getMessage());
//
//        }

// pdfPTable.addCell("Food Expense");
//         pdfPTable.addCell(this.totalFood.getText().toString());
//         pdfPTable.addCell("Vehcle Expense");
//         pdfPTable.addCell(this.totalVehicle.getText().toString());
//         pdfPTable.addCell("Stay Expense");
//         pdfPTable.addCell(this.totalStay.getText().toString());
//         pdfPTable.addCell("Other Expense");
//         pdfPTable.addCell(this.totalOther.getText().toString());
//         pdfPTable.addCell("Total Amount");
//         pdfPTable.addCell(this.totalExpense.getText().toString());
//         pdfPTable.addCell("Toal division");
//         pdfPTable.addCell(this.divTotal.getText().toString() + this.currsymbol);