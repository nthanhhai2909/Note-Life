package com.example.nthan.notelifeadvance.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nthan on 10/13/2017.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_CONTENT =
            "CREATE TABLE " + FeedReaderContract.FeedContent.TABLE_NAME + " (" +
                    FeedReaderContract.FeedContent.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FeedReaderContract.FeedContent.COLUMN_NAME_TITLE + " TEXT, " +
                    FeedReaderContract.FeedContent.COLUMN_NAME_CONTENTNOTE + " TEXT," +
                    FeedReaderContract.FeedContent.COLUMN_NAME_DATECREATENOTE + " TEXT, "+
                    FeedReaderContract.FeedContent.COLUMN_NAME_DATEUPDATELAST + " TEXT, " +
                    FeedReaderContract.FeedContent.COLUMN_NAME_COLORNOTE + " TEXT)";

    private static final String SQL_DROP_CONTENT = "DROP TABLE IF EXISTS " +
            FeedReaderContract.FeedContent.TABLE_NAME;


    private static final String DATABASE_NAME = "notedatabase.db";
    private static final int DATABASE_VERSION = 1;


    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONTENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_CONTENT);
        onCreate(db);
    }
}
