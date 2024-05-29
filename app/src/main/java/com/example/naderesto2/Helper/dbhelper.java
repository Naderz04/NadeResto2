package com.example.naderesto2.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbhelper extends SQLiteOpenHelper {
    private static final String TAG = "dbhelper";
    private static final String DATABASE_NAME = "NadeResto.db";
    private static final int VERSION_NUMBER = 1;

    private String CREATE_TABLE_Category = "CREATE TABLE category(" +
            "category_id integer primary key autoincrement,"+
            "categoryname text not null," +
            "categoryphoto text not null);";
    private String CREATE_TABLE_ITEMS = "CREATE TABLE item("+
            "id integer primary key autoincrement," +
            "itemname text not null,"+
            "itemdescription text,"+
            "url text,"+
            "fee real,"+
            "category_id INTEGER,"+
            "FOREIGN KEY (category_id) REFERENCES category(category_id));";


    public dbhelper(Context context){
        super(context, DATABASE_NAME,null,VERSION_NUMBER);
        Log.d(TAG, "dbhelper constructor called");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_Category);
            Log.d(TAG, "Category table created");
            db.execSQL(CREATE_TABLE_ITEMS);
            Log.d(TAG, "Item table created");
        } catch (Exception e) {
            Log.e(TAG, "Error creating tables", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS item");
            db.execSQL("DROP TABLE IF EXISTS category");
            onCreate(db);
            Log.d(TAG, "Tables upgraded");
        } catch (Exception e) {
            Log.e(TAG, "Error upgrading tables", e);
        }
    }
}
