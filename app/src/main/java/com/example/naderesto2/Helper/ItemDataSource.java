package com.example.naderesto2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.RouteListingPreference;
import android.util.Log;

import com.example.naderesto2.Domain.Category;
import com.example.naderesto2.Domain.Item;

public class ItemDataSource {

    private dbhelper dbhelper;
    private SQLiteDatabase database;


    public ItemDataSource(Context context){

        dbhelper = new dbhelper(context);
    }

    public void open(){

        database = dbhelper.getWritableDatabase();
        Log.d("Database", "Database opened");

    }

    public void close(){

        dbhelper.close();
    }


    public boolean insertItem(Item d){
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("itemname",d.getItemName ());
            initialValues.put("itemdescription",d.getDescription());
            initialValues.put("url", d.getURL());
            initialValues.put("price", d.getItemPrice());
            initialValues.put("category_id", d.getCategoryId());

            didSucceed = database.insert("item", null, initialValues) > 0;
        }catch (Exception e){
            Log.d("Item db", "Something wrong");
        }
        return didSucceed;
    }




}
