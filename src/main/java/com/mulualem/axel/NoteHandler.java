package com.mulualem.axel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NoteHandler extends DatabaseHelper {
    public  NoteHandler(Context context){
        super(context);
    }
// CRUD  C create

    public  boolean create(Note note){

        ContentValues values = new ContentValues();

        values.put("title",note.getTitle());
        values.put("description",note.getDescription());

        SQLiteDatabase database = this.getWritableDatabase();

       boolean isSuccessful =  database.insert("Note",null,values) > 0;
       database.close();
       return isSuccessful;

    }

    public ArrayList<Note> readNotes(){
           ArrayList<Note> notes = new ArrayList<>();

           String sqlQuery = "SELECT * FROM Note ORDER BY id ASC";

           SQLiteDatabase database = this.getWritableDatabase();

           Cursor cursor = database.rawQuery(sqlQuery,null);

           if(cursor.moveToFirst()){
               do{

                   int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                   String title = cursor.getString(cursor.getColumnIndex("title"));
                   String description = cursor.getString(cursor.getColumnIndex("description"));

                   Note note = new Note(title,description);
                   note.setId(id);
                   notes.add(note);

               }while (cursor.moveToNext());

               cursor.close();
               database.close();

           }
           return  notes;
    }
    // Reading single note
    public Note readSingleNote( int id ){
        Note note = null;
        String sqlQuery = " SELECT * FROM Note WHERE id ="+id ;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sqlQuery,null);

        if(cursor.moveToFirst()){
            int noteId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String description = cursor.getString(cursor.getColumnIndex("description"));

             note = new Note(title,description);
            note.setId(noteId);
        }
        cursor.close();
        database.close();
        return note;

    }

    //For updating a single note
    public  boolean update(Note note){

        ContentValues values = new ContentValues();
        values.put("title",note.getTitle());
        values.put("description", note.getDescription());
        SQLiteDatabase database = this.getWritableDatabase();
      boolean isSuccessfull = database.update("Note",values,"id='"+note.getId()+"'",null) > 0;
      database.close();
      return isSuccessfull;
    }

    public  boolean delete ( int id ){
        boolean isDeleted;
        SQLiteDatabase database = this.getWritableDatabase();
        // '3'
     isDeleted = database.delete("Note","id='" + id+"'",null) > 0;
     database.close();
     return isDeleted;
    }
}
