package com.example.interestcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.interestcalculator.models.InterestModel;

public class DbHeleper extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "intersetDb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_SAVE_INTEREST = "save_interest";
    private static final String TABLE_INTEREST_HISTORY = "interest_histroy";
    private static final String TABLE_INTERIM_PAYMENT = "interin_table";

    public static final String ID = "id";
    public static final String currentDate = "currentDate";
    public static final String givenDate = "givenDate";
    public static final String returnDate = "returnDate";
    public static final String principalAmount = "principalAmount";
    public static final String durationPeriod = "durationPeriod";
    public static final String interest = "interest";
    public static final String interestAmount = "interestAmount";
    public static final String interestType = "interestType";
    public static final String totalAmount = "totalAmount";


    public DbHeleper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_INTEREST_HISTORY
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,currentDate TEXT,givenDate TEXT,returnDate TEXT,principalAmount TEXT," +
                "durationPeriod TEXT,interest TEXT," +
                "interestAmount TEXT,interestType TEXT," +
                "totalAmount TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTEREST_HISTORY);
    }

    public boolean addNewHistory(InterestModel model) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(currentDate, model.getCurrentDate());
        values.put(givenDate, model.getGivenDate());
        values.put(returnDate, model.getReturnDate());
        values.put(principalAmount, model.getPrincipalAmount());
        values.put(durationPeriod, model.getDurationPeriod());
        values.put(interest, model.getInterest());
        values.put(interestAmount, model.getInterestAmount());
        values.put(interestType, model.getInterestType());
        values.put(totalAmount, model.getTotalAmount());

        long isInserted = db.insert(TABLE_INTEREST_HISTORY, null, values);
        db.close();
        if (isInserted == -1) {
            return false;
        } else {
            return true;
        }
    }
}
