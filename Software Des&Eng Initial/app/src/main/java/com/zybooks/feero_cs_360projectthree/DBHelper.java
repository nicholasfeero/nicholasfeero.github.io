package com.zybooks.feero_cs_360projectthree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

// Class to manage SQLite database creation, upgrades, and CRUD operations
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB.db";
    // Dependent on schema (tables and foreign key username integration)
    private static final int DATABASE_VERSION = 3;

    // Users table
    private static final String TABLE_USERS = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    // Daily weight table
    private static final String TABLE_WEIGHT = "daily_weight";
    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_WEIGHT = "weight";
    private static final String COL_DAILY_INTAKE = "daily_intake";
    private static final String COL_EXERCISE = "exercise";
    // Foreign key for usernames in user table
    private static final String COL_USER_FK = "user_username";

    // Constructor to initialize DB Helper
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " ("
                + COL_USERNAME + " TEXT PRIMARY KEY, "
                + COL_PASSWORD + " TEXT)");

        // Create daily weight table with link to username
        db.execSQL("CREATE TABLE " + TABLE_WEIGHT + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DATE + " TEXT, "
                + COL_WEIGHT + " REAL, "
                + COL_DAILY_INTAKE + " TEXT, "
                + COL_EXERCISE + " TEXT, "
                + COL_USER_FK + " TEXT, "
                + "FOREIGN KEY(" + COL_USER_FK + ") REFERENCES " + TABLE_USERS +
                "(" + COL_USERNAME + "))");
    }

    // Handles upgrades to the database (required through extending SQLiteOpenHelper)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        onCreate(db);
    }

    // Insert a new user into the Users table
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        // returns true if insertion is successful
        return result != -1;
    }

    // Checks if user with matching username and password exists
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Checks if a username already exists in the database
    public boolean userExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COL_USERNAME + "=?",
                new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Insert a new daily weight entry into the database
    public long insertDailyWeight(String username, String weight, String date,
                                  String dailyIntake, String exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_WEIGHT, weight);
        values.put(COL_DAILY_INTAKE, dailyIntake);
        values.put(COL_EXERCISE, exercise);
        values.put(COL_USER_FK, username);
        return db.insert(TABLE_WEIGHT, null, values);
    }

    // Get all daily weights for a specific user
    public Cursor getAllDailyWeights(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_WEIGHT + " WHERE " + COL_USER_FK + "=?",
                new String[]{username});
    }

    // Acquires all daily weight entries as a list of DataItem objects
    // Get all daily weight entries as a list for a specific user
    public List<DataItem> getAllWeightEntries(String username) {
        List<DataItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WEIGHT + " WHERE "
                        + COL_USER_FK + "=?",
                new String[]{username});

        // Handles Cursor movement
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String weight = cursor.getString(cursor.getColumnIndexOrThrow(COL_WEIGHT));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
                String intake = cursor.getString(cursor.getColumnIndexOrThrow(COL_DAILY_INTAKE));
                String exercise = cursor.getString(cursor.getColumnIndexOrThrow(COL_EXERCISE));

                list.add(new DataItem(id, username, weight, date, intake, exercise));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Update a daily weight entry by ID for a specific user
    public boolean updateDailyWeight(long id, String username, String date, String weight,
                                     String dailyIntake, String exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_WEIGHT, weight);
        values.put(COL_DAILY_INTAKE, dailyIntake);
        values.put(COL_EXERCISE, exercise);
        int rows = db.update(TABLE_WEIGHT, values, COL_ID + "=? AND "
                        + COL_USER_FK + "=?",
                new String[]{String.valueOf(id), username});
        return rows > 0;
    }

    // Delete a daily weight entry by ID for a specific user
    public boolean deleteDailyWeight(int id, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_WEIGHT, COL_ID + "=? AND " + COL_USER_FK + "=?",
                new String[]{String.valueOf(id), username});
        return rows > 0;
    }
}
