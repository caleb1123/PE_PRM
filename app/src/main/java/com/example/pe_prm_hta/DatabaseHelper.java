package com.example.pe_prm_hta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Item.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ITEM = "Item";
    public static final String TABLE_TYPE = "Type";

    public static final String ITEM_ID = "ID";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_CREATOR = "creator";
    public static final String ITEM_RELEASE_DATE = "release_date";
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_IDENTIFIER = "identifier";
    public static final String ITEM_ID_TYPE = "idType";

    public static final String TYPE_ID = "IDType";
    public static final String TYPE_NAME = "nameType";

    private static final String TABLE_CREATE_TYPE =
            "CREATE TABLE " + TABLE_TYPE + " (" +
                    TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TYPE_NAME + " TEXT);";

    private static final String TABLE_CREATE_ITEM =
            "CREATE TABLE " + TABLE_ITEM + " (" +
                    ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ITEM_NAME + " TEXT, " +
                    ITEM_CREATOR + " TEXT, " +
                    ITEM_RELEASE_DATE + " TEXT, " +
                    ITEM_TYPE + " TEXT, " +
                    ITEM_IDENTIFIER + " TEXT, " +
                    ITEM_ID_TYPE + " INTEGER, " +
                    "FOREIGN KEY(" + ITEM_ID_TYPE + ") REFERENCES " + TABLE_TYPE + "(" + TYPE_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_TYPE);
        db.execSQL(TABLE_CREATE_ITEM);
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Insert 3 sample records into the Type table
        db.execSQL("INSERT INTO " + TABLE_TYPE + " (" + TYPE_NAME + ") VALUES ('Software Engineering');");
        db.execSQL("INSERT INTO " + TABLE_TYPE + " (" + TYPE_NAME + ") VALUES ('Data Science');");
        db.execSQL("INSERT INTO " + TABLE_TYPE + " (" + TYPE_NAME + ") VALUES ('Cyber Security');");

        // Insert 3 sample records into the Item table
        db.execSQL("INSERT INTO " + TABLE_ITEM + " (" + ITEM_NAME + ", " + ITEM_CREATOR + ", " + ITEM_RELEASE_DATE + ", " + ITEM_TYPE + ", " + ITEM_IDENTIFIER + ", " + ITEM_ID_TYPE + ") VALUES " +
                "('Sample Item 1', 'Creator 1', '2024-01-01', 'Type A', 'ID001', 1);");
        db.execSQL("INSERT INTO " + TABLE_ITEM + " (" + ITEM_NAME + ", " + ITEM_CREATOR + ", " + ITEM_RELEASE_DATE + ", " + ITEM_TYPE + ", " + ITEM_IDENTIFIER + ", " + ITEM_ID_TYPE + ") VALUES " +
                "('Sample Item 2', 'Creator 2', '2024-02-15', 'Type B', 'ID002', 2);");
        db.execSQL("INSERT INTO " + TABLE_ITEM + " (" + ITEM_NAME + ", " + ITEM_CREATOR + ", " + ITEM_RELEASE_DATE + ", " + ITEM_TYPE + ", " + ITEM_IDENTIFIER + ", " + ITEM_ID_TYPE + ") VALUES " +
                "('Sample Item 3', 'Creator 3', '2024-03-10', 'Type A', 'ID003', 1);");
    }
}
