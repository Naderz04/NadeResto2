package com.example.naderesto2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.naderesto2.Domain.Photo;
import com.example.naderesto2.Domain.Review;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class ReviewDataSource {

    private dbhelper dbHelper;
    private SQLiteDatabase database;

    public ReviewDataSource(Context context){

        dbHelper = new dbhelper(context);
    }
    public int getLastReview() {
        int lastId = -1; // Default to -1 to indicate no entries
        Cursor cursor = null;
        try {
            String query = "SELECT MAX(review_id) FROM review";
            cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                lastId = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e("Database", "Error getting last review ID", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lastId;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        Log.d("Database", "Database opened");
    }

    public void close(){
        dbHelper.close();
        Log.d("Database", "Database closed");
    }

    public boolean insertReview(Review review){
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("review_id", review.getReview_Id());
            initialValues.put("costumername", review.getCostumerName());
            initialValues.put("costumerfeedback", review.getCostumerfeedback());
            didSucceed = database.insert("review", null, initialValues) > 0;

            if (didSucceed) {
                Log.d("User Insert", "User inserted: " + review.getReview_Id());
            } else {
                Log.d("User Insert", "Failed to insert user: " + review.getCostumerName());
            }
        } catch (Exception e) {
        }
        return didSucceed;
    }
    public Review getSpecificReview(int id) {
        Review c = new Review();
        String query = "SELECT * FROM review WHERE review_id = " + id;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            c.setReview_Id(cursor.getInt(0));
            c.setCostumerName(cursor.getString(1));
            c.setCostumerfeedback(cursor.getString(2));
        }
        cursor.close();
        return c;
    }
    public int getLastreview2() {
        int lastId;
        try {
            String query = "select Min(review_id) from review where review_id < 0";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();

        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }
    public boolean deleteReview(int id) {
        boolean deleted;
        try {
            deleted = database.delete("review", "review_id=" + id, null) > 0;
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }




    public ArrayList<Review> getAllContacts() {
        String query = "Select * from review";
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Review c = new Review();
                c.setReview_Id(cursor.getInt(0));
                c.setCostumerName(cursor.getString(1));
                c.setCostumerfeedback(cursor.getString(2));

                cursor.moveToNext();
                reviews.add(c);
            }
            cursor.close();
        } catch (Exception e) {
            reviews = new ArrayList<>();
        }
        return reviews;
    }


//
public void logAllReviews() {
    Cursor cursor = null;
    try {
        cursor = database.rawQuery("SELECT * FROM review", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("review_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("costumername"));
                String costumerfeedback = cursor.getString(cursor.getColumnIndexOrThrow("costumerfeedback"));  // Adjusted to int as it should be a resource ID
            } while (cursor.moveToNext());
        } else {
            Log.d("costumer Data", "No reviews found");
        }
    } catch (Exception e) {
        Log.e("costumer Data", "Error fetching reviews", e);
    } finally {
        if (cursor != null) {
            cursor.close();
        }
    }
}

}
