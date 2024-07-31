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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.Calendar;

public class PhotoDataSource {
    private String[] allColumns = { "contactPhoto" };

    SQLiteDatabase database;
    dbhelper dbHelper;

    public PhotoDataSource(Context context) {

        dbHelper = new dbhelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {

        dbHelper.close();
    }


    public boolean insertContact(Photo photo) {
        boolean didSucceed = false;
        try {
            ContentValues values = new ContentValues();
            if (photo.getContactPhoto() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.getContactPhoto().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] photoo = baos.toByteArray();
                values.put("contactphoto", photoo);
            }
            didSucceed = database.insert("photo", null, values) > 0;

        }catch (Exception e) {
            Log.d("My Database", "Something went wrong!");
        }
        return didSucceed;
    }
    public boolean deleteContact(int id) {
        boolean deleted;
        try {
            deleted = database.delete("photo", "id=" + id, null) > 0;
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }

    public int getLastphoto() {
        int lastId;
        try {
            String query = "Select MAX(id) from photo";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();

        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }
    private Photo cursorToPhoto(Cursor cursor) {
        Photo photo = new Photo();
        photo.setContactPhoto(photo.getImage(cursor.getBlob(1))); // Convert byte[] to Bitmap
        return photo;
    }



    public Photo getSpecificContact(int id) {
        Photo c = new Photo();
        String query = "SELECT * FROM photo WHERE id = " + id;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            c.setContactID(cursor.getInt(0));
            byte[] photo = cursor.getBlob(1);
            if (photo != null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(photo);
                Bitmap contactPhoto = BitmapFactory.decodeStream(bais);
                c.setContactPhoto(contactPhoto);
            }
        }
        cursor.close();
        return c;
    }

}