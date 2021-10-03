package com.example.interestcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.interestcalculator.models.InterestModel;

import java.util.ArrayList;

public class DbHeleper extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "intersetDb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_SAVE_INTEREST = "save_interest";
    private static final String TABLE_INTEREST_HISTORY = "interest_histroy";
    private static final String TABLE_INTERIM_PAYMENT = "interim_table";


    public DbHeleper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.

        // at last we are calling a exec sql
        // method to execute above sql query


        db.execSQL("create Table interest_histroy(id INTEGER PRIMARY KEY AUTOINCREMENT,currentDate TEXT,givenDate TEXT,returnDate TEXT,principalAmount TEXT,durationPeriod TEXT,interest TEXT,interestAmount TEXT,interestType TEXT,totalAmount TEXT) ");
        db.execSQL("create Table save_interest(id INTEGER PRIMARY KEY AUTOINCREMENT,currentDate TEXT,givenDate TEXT,returnDate TEXT,principalAmount TEXT,durationPeriod TEXT,interest TEXT,interestAmount TEXT,interestType TEXT,totalAmount TEXT,recordName TEXT,cityName TEXT,remarks TEXT)");
        db.execSQL("create Table interim_table(id INTEGER PRIMARY KEY AUTOINCREMENT,fid TEXT,principalAmount TEXT,totalAmount TEXT,currentDate TEXT,intrimePayment TEXT,durationPeriod TEXT,remarks TEXT,remaingingAmount TEXT)");
    }

    // this method is use to add new course to our sqlite database.
    public boolean addNewHistory(InterestModel model) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put("currentDate", model.getCurrentDate());
        values.put("givenDate", model.getGivenDate());
        values.put("returnDate", model.getReturnDate());
        values.put("principalAmount", model.getPrincipalAmount());
        values.put("durationPeriod", model.getDurationPeriod());
        values.put("interest", model.getInterest());
        values.put("interestAmount", model.getInterestAmount());
        values.put("interestType", model.getInterestType());
        values.put("totalAmount", model.getTotalAmount());


        // after adding all values we are passing
        // content values to our table.
        long result = db.insert(TABLE_INTEREST_HISTORY, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();

        return result != -1;
    }


    // save interest
    public boolean saveInterest(InterestModel model) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("currentDate", model.getCurrentDate());
        values.put("givenDate", model.getGivenDate());
        values.put("returnDate", model.getReturnDate());
        values.put("principalAmount", model.getPrincipalAmount());
        values.put("durationPeriod", model.getDurationPeriod());
        values.put("interest", model.getInterest());
        values.put("interestAmount", model.getInterestAmount());
        values.put("interestType", model.getInterestType());
        values.put("totalAmount", model.getTotalAmount());
        values.put("recordName", model.getRecordName());
        values.put("cityName", model.getCityName());
        values.put("remarks", model.getRemarks());

        long result = db.insert(TABLE_SAVE_INTEREST, null, values);
        db.close();

        return result != -1;
    }

    public boolean insertIntrimePayment(InterestModel model){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put("fid",model.getFid());
        values.put("principalAmount",model.getPrincipalAmount());
        values.put("totalAmount",model.getTotalAmount());
        values.put("currentDate",model.getCurrentDate());
        values.put("intrimePayment",model.getIntrimePayment());
        values.put("intrimePayment",model.getIntrimePayment());
        values.put("durationPeriod",model.getDurationPeriod());
        values.put("remarks",model.getRemarks());



        long result = db.insert(TABLE_INTERIM_PAYMENT,null ,values);

        return result != -1;
    }

    //get the all history
    public ArrayList<InterestModel> getHistory() {
        ArrayList<InterestModel> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + TABLE_INTEREST_HISTORY;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InterestModel model = new InterestModel();
                model.setId(cursor.getString(0));
                model.setCurrentDate(cursor.getString(1));
                model.setGivenDate(cursor.getString(2));
                model.setReturnDate(cursor.getString(3));
                model.setPrincipalAmount(cursor.getString(4));
                model.setDurationPeriod(cursor.getString(5));
                model.setInterest(cursor.getString(6));
                model.setInterestAmount(cursor.getString(7));
                model.setInterestType(cursor.getString(8));
                model.setTotalAmount(cursor.getString(9));

                Log.e("TAG", "getHistory: model " + model );
           /*     noteModel.setID(cursor.getString(0));
                noteModel.setTitle(cursor.getString(1));
                noteModel.setDes(cursor.getString(2));
           */     arrayList.add(model);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    //get the all history
    public ArrayList<InterestModel> getSaveRecord() {
        ArrayList<InterestModel> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + TABLE_SAVE_INTEREST;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InterestModel model = new InterestModel();
                model.setId(cursor.getString(0));
                model.setCurrentDate(cursor.getString(1));
                model.setGivenDate(cursor.getString(2));
                model.setReturnDate(cursor.getString(3));
                model.setPrincipalAmount(cursor.getString(4));
                model.setDurationPeriod(cursor.getString(5));
                model.setInterest(cursor.getString(6));
                model.setInterestAmount(cursor.getString(7));
                model.setInterestType(cursor.getString(8));
                model.setTotalAmount(cursor.getString(9));
                model.setRecordName(cursor.getString(10));
                model.setCityName(cursor.getString(11));
                model.setRemarks(cursor.getString(12));
                Log.e("TAG", "getHistory: model " + model );
           /*     noteModel.setID(cursor.getString(0));
                noteModel.setTitle(cursor.getString(1));
                noteModel.setDes(cursor.getString(2));
           */     arrayList.add(model);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    //get intrimePaymentHistory
    public ArrayList<InterestModel> getIntrimeHistory(String fid) {
        ArrayList<InterestModel> arrayList = new ArrayList<>();


        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INTERIM_PAYMENT + " where fid = '" +fid + "'" , null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InterestModel model = new InterestModel();
                model.setId(cursor.getString(0));
                model.setFid(cursor.getString(1));
                model.setPrincipalAmount(cursor.getString(2));
                model.setTotalAmount(cursor.getString(3));
                model.setCurrentDate(cursor.getString(4));
                model.setIntrimePayment(cursor.getString(5));
                model.setDurationPeriod(cursor.getString(6));
                model.setRemarks(cursor.getString(7));
                model.setRemianingAmount(cursor.getString(8));

                Log.e("TAG", "getHistory: model " + model );
           /*     noteModel.setID(cursor.getString(0));
                noteModel.setTitle(cursor.getString(1));
                noteModel.setDes(cursor.getString(2));
           */     arrayList.add(model);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }


    // delete History
    public void deleteHistory(int p){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_INTEREST_HISTORY + " WHERE " + "id" + " = "+p+"");
        db.close();
    }

    // update savedTable total amount
    public boolean updateTotalAmount(String totalAmount,String id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("totalAmount", totalAmount);

       long result = db.update(TABLE_SAVE_INTEREST,values,"id = ?",new String[]{id});

        return result != -1;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTEREST_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERIM_PAYMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE_INTEREST);
        onCreate(db);
    }
}
