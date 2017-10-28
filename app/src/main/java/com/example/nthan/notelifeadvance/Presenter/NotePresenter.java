package com.example.nthan.notelifeadvance.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nthan.notelifeadvance.Model.FeedReaderContract;
import com.example.nthan.notelifeadvance.Model.FeedReaderDbHelper;
import com.example.nthan.notelifeadvance.Model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nthan on 10/13/2017.
 */

public class NotePresenter {

    private FeedReaderDbHelper feedReaderDbHelper;

    public NotePresenter(Context context){
        this.feedReaderDbHelper = new FeedReaderDbHelper(context);
    }

    // ADD NOTE
    public void addNote(Note note){
        SQLiteDatabase database = feedReaderDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_TITLE, note.getTitle());
        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_CONTENTNOTE, note.getContent());
        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_DATECREATENOTE, note.getDate_create());
        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_DATEUPDATELAST, note.getDate_Update_last());
        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_COLORNOTE, note.getColor());

        database.insertOrThrow(FeedReaderContract.FeedContent.TABLE_NAME, null, contentValues);

        database.close();
    }

    // UPDATE NOTE

    public void updateNote(Note note){
        SQLiteDatabase database = feedReaderDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_TITLE, note.getTitle());
        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_CONTENTNOTE, note.getContent());
        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_DATECREATENOTE, note.getDate_create());
        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_DATEUPDATELAST, note.getDate_Update_last());
        contentValues.put(FeedReaderContract.FeedContent.COLUMN_NAME_COLORNOTE, note.getColor());
        String arr[] = {String.valueOf(note.getId())};
        database.update(FeedReaderContract.FeedContent.TABLE_NAME, contentValues,
                FeedReaderContract.FeedContent.COLUMN_NAME_ID + "=?" , arr);
        database.close();
    }

    // DELETE NOTE
    public void deleteNote(List<String> ids){
        if(!ids.isEmpty()){
            SQLiteDatabase database = feedReaderDbHelper.getWritableDatabase();
            for (int i = 0; i <ids.size() ; i++) {
                String[] arr = new String[]{ids.get(i)};
                database.delete(FeedReaderContract.FeedContent.TABLE_NAME,
                FeedReaderContract.FeedContent.COLUMN_NAME_ID + "=?", arr);
            }
            database.close();
        }

    }

    // GET ALL NOTE
    public List<Note> getAllNote(){

        List<Note> listNote = new ArrayList<>();
        SQLiteDatabase database = feedReaderDbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + FeedReaderContract.FeedContent.TABLE_NAME;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Note note = new Note(cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_TITLE)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_CONTENTNOTE)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_DATECREATENOTE)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_DATEUPDATELAST)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_COLORNOTE)));
                listNote.add(note);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listNote;
    }

    public Note getNoteByID(int idNote){
        Note note = null;
        SQLiteDatabase database = feedReaderDbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + FeedReaderContract.FeedContent.TABLE_NAME + " WHERE " +
                String.valueOf(idNote) + " = " + FeedReaderContract.FeedContent.COLUMN_NAME_ID;
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                note = new Note(cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_TITLE)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_CONTENTNOTE)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_DATECREATENOTE)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_DATEUPDATELAST)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedContent.COLUMN_NAME_COLORNOTE)));
            }while (cursor.moveToNext());
        }
        return note;
    }
}
