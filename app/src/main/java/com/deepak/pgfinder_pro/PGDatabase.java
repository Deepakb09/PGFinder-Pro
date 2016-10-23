package com.deepak.pgfinder_pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Deepak on 14-Oct-16.
 */
public class PGDatabase {
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase, sqLiteDatabase1;
    PGDetails pgDetails;
    ArrayList<PGDetails> pgDetailsArrayList;

    public PGDatabase(Context context){
        myHelper = new MyHelper(context, "pgdetails.db", null, 1);
    }

    public void open(){
        sqLiteDatabase = myHelper.getWritableDatabase();
    }

    public void insertPgDetails(String advertisername, String pgname, String contact, String pgcity, String pgarea, String pgrent,
                                String negotiable, String gender, String typeoffood, String moredetails, String dateofposting,
                                byte[] image1, byte[] image2, String latitude, String longitude){
        ContentValues contentValues = new ContentValues();
        contentValues.put("advertisername", advertisername);
        contentValues.put("pgname", pgname);
        contentValues.put("contact", contact);
        contentValues.put("pgcity", pgcity);
        contentValues.put("pgarea", pgarea);
        contentValues.put("pgrent", pgrent);
        contentValues.put("negotiable", negotiable);
        contentValues.put("gender", gender);
        contentValues.put("typeoffood", typeoffood);
        contentValues.put("moredetails", moredetails);
        contentValues.put("dateofposting", dateofposting);
        contentValues.put("image1", image1);
        contentValues.put("image2", image2);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        try {
            sqLiteDatabase.insert("pgdetails", null, contentValues);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<PGDetails> queryPGDetails(){
        ArrayList<PGDetails> pgDetailsArrayList = new ArrayList<PGDetails>();
        Cursor cursor = sqLiteDatabase.query("pgdetails", null, null, null, null, null, null, null);
        while(cursor.moveToNext() == true) {
            pgDetails = new PGDetails(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                    cursor.getString(10), cursor.getString(11), cursor.getBlob(12), cursor.getBlob(13),
                    cursor.getString(14), cursor.getString(15));
            //advertisername, pgname, contact, city, area, rent, negotiable2, gender2, food2, desc,
            //dateofposting, sample1, sample2, latitude, longitude
            pgDetails.setAdvertisername(cursor.getString(1));
            pgDetails.setPgname(cursor.getString(2));
            pgDetails.setContact(cursor.getString(3));
            pgDetails.setPgcity(cursor.getString(4));
            pgDetails.setPgarea(cursor.getString(5));
            pgDetails.setPgrent(cursor.getString(6));
            pgDetails.setNegotiable(cursor.getString(7));
            pgDetails.setGender(cursor.getString(8));
            pgDetails.setTypeoffood(cursor.getString(9));
            pgDetails.setMoredetails(cursor.getString(14));
            pgDetails.setDateofposting(cursor.getString(11));
            pgDetails.setImage1(cursor.getBlob(12));
            pgDetails.setImage2(cursor.getBlob(13));
            pgDetails.setLatitude(cursor.getString(14));
            pgDetails.setLongitude(cursor.getString(15));
            pgDetailsArrayList.add(pgDetails);
        }

        return pgDetailsArrayList;
    }

    public ArrayList<PGDetails> querySortedPGDetails(String fromdate, int rent){
        //String date = fromdate.trim();
        //int id = 0;
        Cursor cursor = null;
        ArrayList<PGDetails> pgDetailsArrayList = new ArrayList<PGDetails>();
        //Cursor cursor = sqLiteDatabase.query("pgdetails", null, null, null, null, null, null, null);
        /*if(cursor.moveToNext() == true) {
            id = cursor.getInt(0);
        }
        cursor = sqLiteDatabase.query("pgdetails", null, "_id >= ?", new String[]{""+id}, null, null, null, null);*/
        if(rent == 1) {
            cursor = sqLiteDatabase.query("pgdetails", null, null, null, null, null, "pgrent" + " ASC", null);
        }
        else if(rent == 2){
            cursor = sqLiteDatabase.query("pgdetails", null, null, null, null, null, "pgrent" + " DESC", null);
        }
        while(cursor.moveToNext() == true) {
            pgDetails = new PGDetails(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                    cursor.getString(10), cursor.getString(11), cursor.getBlob(12), cursor.getBlob(13),
                    cursor.getString(14), cursor.getString(15));
            //advertisername, pgname, contact, city, area, rent, negotiable2, gender2, food2, desc,
            //dateofposting, sample1, sample2, latitude, longitude
            pgDetails.setAdvertisername(cursor.getString(1));
            pgDetails.setPgname(cursor.getString(2));
            pgDetails.setContact(cursor.getString(3));
            pgDetails.setPgcity(cursor.getString(4));
            pgDetails.setPgarea(cursor.getString(5));
            pgDetails.setPgrent(cursor.getString(6));
            pgDetails.setNegotiable(cursor.getString(7));
            pgDetails.setGender(cursor.getString(8));
            pgDetails.setTypeoffood(cursor.getString(9));
            pgDetails.setMoredetails(cursor.getString(14));
            pgDetails.setDateofposting(cursor.getString(11));
            pgDetails.setImage1(cursor.getBlob(12));
            pgDetails.setImage2(cursor.getBlob(13));
            pgDetails.setLatitude(cursor.getString(14));
            pgDetails.setLongitude(cursor.getString(15));
            pgDetailsArrayList.add(pgDetails);
        }

        return pgDetailsArrayList;
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public class MyHelper extends SQLiteOpenHelper{
        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table pgdetails(_id integer primary key, advertisername text, pgname text, contact text, pgcity text, pgarea text, pgrent text, negotiable text, gender text, typeoffood text, moredetails text, dateofposting string, image1 blob, image2 blob, latitude text, longitude text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
