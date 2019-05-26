package com.xuanvu.hungthinh.litenote.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.xuanvu.hungthinh.litenote.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Note_Manager";
    private static final String TABLE_NAME = "Note";

    private static final String ID = "Note_Id";
    private static final String TITLE = "Note_Title";
    private static final String CONTENT = "Note_Content";
    private static final String COLOR  = "Note_Color";
    private static final String CREATED_AT  = "Note_Created_At";

    private String [] columns = {"Note_Id", "Note_Title", "Note_Content", "Note_Color", "Note_Created_At"};

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = " CREATE TABLE " + TABLE_NAME + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + TITLE + " TEXT, " + CONTENT + " TEXT, " + COLOR + " INTEGER ," + CREATED_AT +" TEXT " + " )";
        db.execSQL( script );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( " DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( db );
    }

    public int addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( TITLE, note.getmTitle() );
        values.put( CONTENT, note.getmContent() );
        values.put( COLOR, String.valueOf(note.getmColor()));
        values.put( CREATED_AT, note.getmCreated_at() );
        db.insert( TABLE_NAME, null, values );
        db = this.getReadableDatabase();
        String id = " SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery( id, null );
        if (cursor != null) cursor.moveToFirst();
        db.close();
        return Integer.parseInt( cursor.getString( 0 ) );
    }

    public boolean deleteContact(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        int check = db.delete( TABLE_NAME, ID + " = " + note.getID(), null );
        if (check != 0) {

            db.close();
            return true;
        } else {

            db.close();
            return false;

        }

    }

    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        int check = db.delete( TABLE_NAME, null, null );

        if (check != 0) {

            db.close();
            return true;
        } else {

            db.close();
            return false;

        }
    }

    public List<Note> getAllNotes() {
        List<Note> notesArrayList = new ArrayList<Note>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query( TABLE_NAME, columns, null, null, null, null, null );

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID( cursor.getInt( 0 ) );
                note.setmTitle( cursor.getString( 1 ) );
                note.setmContent( cursor.getString( 2 ) );
                note.setmColor( cursor.getString( 3 ) );
                note.setmCreated_at( cursor.getString( 4 ) );
                notesArrayList.add( note );
            } while (cursor.moveToNext());
        }
        return notesArrayList;
    }
}
