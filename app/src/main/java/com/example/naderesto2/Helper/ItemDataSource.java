package com.example.naderesto2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.naderesto2.Domain.Item;

import java.util.ArrayList;

public class ItemDataSource {

    private dbhelper dbHelper;
    private SQLiteDatabase database;

    public ItemDataSource(Context context) {
        dbHelper = new dbhelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        Log.d("Database", "Database opened");
    }

    public void close() {
        dbHelper.close();
    }

    public void insertItem(Item item) {
        ContentValues values = new ContentValues();
        values.put("itemid", item.getItemId());
        values.put("itemname", item.getItemName());
        values.put("fee", item.getItemPrice());
        values.put("itemdescription", item.getDescription());
        values.put("url", item.getURL());
        values.put("category_id", item.getCategoryId());
        values.put("numberincart", item.getNumberInCart());
        database.insert("items", null, values);
    }

    public ArrayList<Item> getItemsByCategory(int category_id) {
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM items WHERE category_id = ?", new String[]{String.valueOf(category_id)});

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(
                        cursor.getInt(cursor.getColumnIndexOrThrow("itemid")),
                        cursor.getString(cursor.getColumnIndexOrThrow("itemname")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("fee")),
                        cursor.getString(cursor.getColumnIndexOrThrow("itemdescription")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("url")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("numberincart")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("category_id"))
                );
                items.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return items;
    }

    public void logAllItems() {
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM items", null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("itemid"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("itemname"));
                    Double fee = cursor.getDouble(cursor.getColumnIndexOrThrow("fee"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("itemdescription"));
                    int photoResId = cursor.getInt(cursor.getColumnIndexOrThrow("url"));
                    int numberincart = cursor.getInt(cursor.getColumnIndexOrThrow("numberincart"));
                    int categoryid = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));

                    Log.d("Item", "ID: " + id + ", Name: " + name + ", Fee: " + fee + ", Description: " + description + ", URL: " + photoResId + ", Number in Cart: " + numberincart + ", Category ID: " + categoryid);
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
