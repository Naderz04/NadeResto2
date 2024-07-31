package com.example.naderesto2.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "naderesto.db";
    private static final int DATABASE_VERSION = 21; // Increment this version if you change the schema
    private static final String TAG = "dbhelper";

    private String CREATE_TABLE_Category = "CREATE TABLE category(" +
            "category_id integer primary key autoincrement," +
            "categoryname text not null," +
            "categoryphoto text not null);";
    private String CREATE_TABLE_ITEMS = "CREATE TABLE items(" +
            "itemid integer primary key autoincrement," +
            "itemname text not null," +
            "fee real," +
            "itemdescription text," +
            "url text," +
            "category_id INTEGER," +
            "numberincart INTEGER," +
            "FOREIGN KEY (category_id) REFERENCES category(category_id));";

    private String CREATE_TABLE_Review = "CREATE TABLE review(" +
            "review_id integer primary key autoincrement," +
            "costumername text not null," +
            "costumerfeedback text not null);";
    private String CREATE_TABLE_COSTUMERPHOTO = "CREATE TABLE photo(" +
            "id integer primary key autoincrement," +
            "contactphoto blob);";
    public dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "dbhelper constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_Category);
            Log.d(TAG, "Category table created");
            db.execSQL(CREATE_TABLE_ITEMS);
            Log.d(TAG, "Item table created");
            db.execSQL(CREATE_TABLE_Review);
            Log.d(TAG, "User table created");
            db.execSQL(CREATE_TABLE_COSTUMERPHOTO);

        } catch (Exception e) {
            Log.e(TAG, "Error creating tables", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS category");
            db.execSQL("DROP TABLE IF EXISTS items");
            db.execSQL("DROP TABLE IF EXISTS item");
            db.execSQL("DROP TABLE IF EXISTS review");
            db.execSQL("DROP TABLE IF EXISTS photo");

            onCreate(db);
        } catch (Exception e) {
            Log.e(TAG, "Error upgrading database", e);
        }
    }
}
