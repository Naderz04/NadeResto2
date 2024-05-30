package com.example.naderesto2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.naderesto2.Domain.Category;

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
