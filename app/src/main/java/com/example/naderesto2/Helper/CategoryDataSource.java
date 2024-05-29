package com.example.naderesto2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.naderesto2.Domain.Category;

public class CategoryDataSource {

    private dbhelper dbHelper;
    private SQLiteDatabase database;


    public CategoryDataSource(Context context){

        dbHelper = new dbhelper(context);
    }
    public void open(){

        database = dbHelper.getWritableDatabase();
        Log.d("Database", "Database opened");

    }
    public void close(){

        dbHelper.close();
        Log.d("Database", "Database closed");

    }

    public boolean insertCategory(Category d){
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("categoryid",d.getCategoryId());
            initialValues.put("categoryname",d.getCategoryName());
            initialValues.put("categoryphoto", d.getCategoryPhoto());
            didSucceed = database.insert("category", null, initialValues) > 0;
        }catch (Exception e){
            Log.d("Category db", "Something wrong");
        }
        return didSucceed;
    }



}
