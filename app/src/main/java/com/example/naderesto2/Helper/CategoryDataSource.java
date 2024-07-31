package com.example.naderesto2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.naderesto2.Domain.Category;
import com.example.naderesto2.Domain.Item;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class CategoryDataSource {

    private dbhelper dbHelper;
    private SQLiteDatabase database;

    public CategoryDataSource(Context context){

        dbHelper = new dbhelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        Log.d("Database", "Database opened");
    }

    public void close(){
        dbHelper.close();
        Log.d("Database", "Database closed");
    }

    public boolean insertCategory(Category category){
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("category_id", category.getCategoryId());
            initialValues.put("categoryname", category.getCategoryName());
            initialValues.put("categoryphoto", category.getCategoryPhotoResId());
            didSucceed = database.insert("category", null, initialValues) > 0;

            if (didSucceed) {
                Log.d("Category Insert", "Category inserted: " + category.getCategoryName());
            } else {
                Log.d("Category Insert", "Failed to insert category: " + category.getCategoryName());
            }
        } catch (Exception e) {
            Log.e("Category Insert", "Error inserting category: " + category.getCategoryName(), e);
        }
        return didSucceed;
    }
    public boolean deleteCategory(int id) {
        boolean deleted;
        try {
            deleted = database.delete("category", "_id=" + id, null) > 0;
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }

//    public ArrayList<Item> getItemsByCategory(String categoryName) {
//        ArrayList<Item> items = new ArrayList<>();
//        SQLiteDatabase db = this.etReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM items WHERE category = ?", new String[]{categoryName});
//
//        if (cursor.moveToFirst()) {
//            do {
//                Item item = new Item(
//                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
//                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
//                        cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
//                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
//                        cursor.getInt(cursor.getColumnIndexOrThrow("URL")),
//                        cursor.getInt(cursor.getColumnIndexOrThrow("categoryid")),
//                        cursor.getInt(cursor.getColumnIndexOrThrow("numberInCart"))
//
//                        );
//                items.add(item);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return items;
//    }


    public Category getSpecificCategory(int id) {
        Category c = new Category();
        String query = "SELECT * FROM category WHERE _id = " + id;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            c.setCategoryId(cursor.getInt(0));
            c.setCategoryName(cursor.getString(1));
            c.setCategoryPhotoResId(cursor.getInt(2));

        }
        cursor.close();
        return c;
    }


    public void logAllCategories() {
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM category", null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("categoryname"));
                    int photoResId = cursor.getInt(cursor.getColumnIndexOrThrow("categoryphoto"));  // Adjusted to int as it should be a resource ID
                    Log.d("Category Data", "ID: " + id + ", Name: " + name + ", PhotoResId: " + photoResId);
                } while (cursor.moveToNext());
            } else {
                Log.d("Category Data", "No categories found");
            }
        } catch (Exception e) {
            Log.e("Category Data", "Error fetching categories", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
