package com.example.pe_prm_hta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class ItemDAO {
    private DatabaseHelper dbHelper;
    private Context context;

    public ItemDAO(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public long addItem(Item item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        TypeDAO typeDAO = new TypeDAO(context);
        if (!typeDAO.isTypeExists(item.getIdType())) {
            throw new IllegalArgumentException("Type does not exist");
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ITEM_NAME, item.getName());
        values.put(DatabaseHelper.ITEM_CREATOR, item.getCreator());
        values.put(DatabaseHelper.ITEM_RELEASE_DATE, item.getRelease_date());
        values.put(DatabaseHelper.ITEM_TYPE, item.getType());
        values.put(DatabaseHelper.ITEM_IDENTIFIER, item.getIdentifier());
        values.put(DatabaseHelper.ITEM_ID_TYPE, item.getIdType());
        return db.insert(DatabaseHelper.TABLE_ITEM, null, values);
    }

    public int updateItem(Item item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (!isTypeExists(item.getIdType())) {
            throw new IllegalArgumentException("Type does not exist");
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ITEM_NAME, item.getName());
        values.put(DatabaseHelper.ITEM_CREATOR, item.getCreator());
        values.put(DatabaseHelper.ITEM_RELEASE_DATE, item.getRelease_date());
        values.put(DatabaseHelper.ITEM_TYPE, item.getType());
        values.put(DatabaseHelper.ITEM_IDENTIFIER, item.getIdentifier());
        values.put(DatabaseHelper.ITEM_ID_TYPE, item.getIdType());
        return db.update(DatabaseHelper.TABLE_ITEM, values, DatabaseHelper.ITEM_ID + " = ?", new String[]{String.valueOf(item.getId())});
    }

    public ArrayList<Item> getAllItems() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Item> items = new ArrayList<>();

        try {
            cursor = db.query(DatabaseHelper.TABLE_ITEM, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_ID)));
                    item.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_NAME)));
                    item.setCreator(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_CREATOR)));
                    item.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_RELEASE_DATE)));
                    item.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_TYPE)));
                    item.setIdentifier(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_IDENTIFIER)));
                    item.setIdType(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_ID_TYPE)));
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ItemDAO", "Error while trying to get items from database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return items;
    }

    public Item getItemById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Item item = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_ITEM, null, DatabaseHelper.ITEM_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_ID)));
                item.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_NAME)));
                item.setCreator(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_CREATOR)));
                item.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_RELEASE_DATE)));
                item.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_TYPE)));
                item.setIdentifier(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_IDENTIFIER)));
                item.setIdType(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ITEM_ID_TYPE)));
            }
        } catch (Exception e) {
            Log.e("ItemDAO", "Error while trying to get item by ID from database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return item;
    }

    public int deleteItem(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABLE_ITEM, DatabaseHelper.ITEM_ID + " = ?", new String[]{String.valueOf(id)});
    }

    private boolean isTypeExists(int idType) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TYPE, null, DatabaseHelper.TYPE_ID + " = ?", new String[]{String.valueOf(idType)}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
