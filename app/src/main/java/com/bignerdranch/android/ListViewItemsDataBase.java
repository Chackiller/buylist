package com.bignerdranch.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListViewItemsDataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "LISTITEMS";
    private static final int DB_VERSION = 1;

    ListViewItemsDataBase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TODO(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "DESCRIPTION TEXT," +
                "TYPE INTEGER," +
                "ISCHECKED INTEGER);");

//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("NAME","LOL");
//        db.insert("TODO",null,contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
