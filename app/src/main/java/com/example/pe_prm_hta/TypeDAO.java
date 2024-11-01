package com.example.pe_prm_hta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class TypeDAO {
    private DatabaseHelper dbHelper;

    public TypeDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addType(Type type) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TYPE_NAME, type.getName());
        return db.insert(DatabaseHelper.TABLE_TYPE, null, values);
    }

    public int updateType(Type type) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TYPE_NAME, type.getName());
        return db.update(DatabaseHelper.TABLE_TYPE, values, DatabaseHelper.TYPE_ID + " = ?", new String[]{String.valueOf(type.getId())});
    }

    public ArrayList<Type> getAllTypes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Type> types = new ArrayList<>();

        try {
            cursor = db.query(DatabaseHelper.TABLE_TYPE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Type type = new Type();
                    type.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TYPE_ID)));
                    type.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TYPE_NAME)));
                    types.add(type);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("TypeDAO", "Error while trying to get types from database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return types;
    }

    public Type getTypeById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Type type = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_TYPE, null, DatabaseHelper.TYPE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                type = new Type();
                type.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TYPE_ID)));
                type.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TYPE_NAME)));
            }
        } catch (Exception e) {
            Log.e("TypeDAO", "Error while trying to get type by ID from database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return type;
    }

    public int deleteType(int id) {
        if (isTypeInUse(id)) {
            throw new IllegalArgumentException("Cannot delete Type in use by Items");
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABLE_TYPE, DatabaseHelper.TYPE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean isTypeExists(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TYPE, null, DatabaseHelper.TYPE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    private boolean isTypeInUse(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_ITEM, null, DatabaseHelper.ITEM_ID_TYPE + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        boolean inUse = (cursor.getCount() > 0);
        cursor.close();
        return inUse;
    }
}
