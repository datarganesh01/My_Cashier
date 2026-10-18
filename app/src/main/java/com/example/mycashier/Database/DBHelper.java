package com.example.mycashier.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mycashier.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE users ("+"id INTEGER PRIMARY KEY AUTOINCREMENT," +
                ""+"name TEXT ," +
                ""+"mobileno TEXT ," +
                ""+"emailid TEXT ," +
                ""+"username TEXT UNIQUE," +
                ""+"password TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);


    }

    public boolean registerUser(String name,String mobileno,String emailid, String username, String password){

        if (checkUserExists(username))
        {

            return false;
        }

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",name);
        contentValues.put("mobileno",mobileno);
        contentValues.put("emailid",emailid);
        contentValues.put("username",username);
        contentValues.put("password",password);

        long result = sqLiteDatabase.insert("users",null,contentValues);

        return result != -1;
    }

    private boolean checkUserExists(String username) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE username =?",
                new String[]{username});

        boolean exists = cursor.getCount() > 0;
        cursor.close();


        return exists;
    }

    public boolean validateLogin(String username,String password){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE username =? AND password =?",
                new String[]{username,password});

        boolean valid = cursor.getCount() >0;
        cursor.close();

        return valid;
    }

    public boolean updateUser(String oldUsername, String name,String mobileno, String emailid, String username)
    {
        if (!oldUsername.equals(username) && isUsernameExists(username)) {
            return false; // Cannot update to an already existing username
        }

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",name);
        contentValues.put("mobileno",mobileno);
        contentValues.put("emailid",emailid);
        contentValues.put("username",username);

        int result = sqLiteDatabase.update("users",contentValues,"username =?",
                new String[] {oldUsername});

        return result > 0;

        
    }

    public boolean deleteUser(String username)
    {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        int result = sqLiteDatabase.delete("users","username =?",
                new String[]{username});

        return result > 0;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


}
