// dbhelper.java
package com.example.naderesto2.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbhelper extends SQLiteOpenHelper {

    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_CATEGORY_ID = "category_id";

    private static final String DATABASE_NAME = "naderesto.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ITEMS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_PRICE + " real not null, "
            + COLUMN_DESCRIPTION + " text, "
            + COLUMN_IMAGE + " integer, "
            + COLUMN_CATEGORY_ID + " integer);";

    public dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
}
