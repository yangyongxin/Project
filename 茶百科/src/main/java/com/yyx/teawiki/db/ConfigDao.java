package com.yyx.teawiki.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConfigDao {

    public MyOpenHelper helper;

    public ConfigDao(Context context) {
        helper = new MyOpenHelper(context);
    }


    public boolean add(String name, String value) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("value", value);
        long rowid = db.insert("config", null, contentValues);
        if (rowid == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String find(String name) {
        String value = "";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("config", new String[]{"value"}, "name=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()) {
            value = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return value;
    }

    public boolean delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowNumber = db.delete("config", "name=?", new String[]{name});
        if (rowNumber == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean update(String name, String value) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("value", value);
        int rownumber =db.update("config", values, "name=?", new String[]{name});
        if (rownumber == 0) {
            return false;
        } else {
            return true;
        }
    }





}
