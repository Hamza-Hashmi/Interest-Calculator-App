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
        db.execSQL("create Table interim_table(id INTEGER PRIMARY KEY AUTOINCREMENT,fid TEXT,principalAmount TEXT,totalAmount TEXT,currentDate TEXT,intrimePayment TEXT,remianingAmount TEXT,durationPeriod TEXT)");
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
        values.put("recordName", model.getRecordName());
        values.put("cityName", model.getCityName());
        values.put("remarks", model.getRemarks());


        // after adding all values we are passing
        // content values to our table.
        long result = db.insert(TABLE_SAVE_INTEREST, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTEREST_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERIM_PAYMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE_INTEREST);
        onCreate(db);
    }
}
