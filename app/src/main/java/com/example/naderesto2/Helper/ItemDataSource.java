package com.example.naderesto2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.RouteListingPreference;
import android.util.Log;

import com.example.naderesto2.Domain.Category;
import com.example.naderesto2.Domain.Item;

public class ItemDataSource {

    private dbhelper dbHelper;
    private SQLiteDatabase database;


    public ItemDataSource(Context context){

        dbHelper = new dbhelper(context);
    }

    public void open() throws SQLException {

        database = dbHelper.getWritableDatabase();
        Log.d("Database", "Database opened");

    }

    public void close(){

        dbHelper.close();
    }


    public void insertItem(Item item) {
        ContentValues values2 = new ContentValues();
        values2.put("itemname", item.getItemName());
        values2.put("itemdescription", item.getDescription());
        values2.put("url", item.getURL());
        values2.put("fee", item.getItemPrice());
        values2.put("category_id", item.getCategoryId());
        values2.put("numberincart", item.getNumberInCart()); // Ensure this is included
        database.insert("item", null, values2);
    }




    public void logAllItems() {
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM item", null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("itemname"));
                    String descriptionme = cursor.getString(cursor.getColumnIndexOrThrow("itemdescription"));
                    int photoResId = cursor.getInt(cursor.getColumnIndexOrThrow("url"));  // Adjusted to int as it should be a resource ID
                    Double fee=cursor.getDouble(cursor.getColumnIndexOrThrow("fee"));
                    int categoryid = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));



                } while (cursor.moveToNext());
            } else {
                Log.d("Items Data", "No Items found");
            }
        } catch (Exception e) {
            Log.e("Item Data", "Error fetching items", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }




}
