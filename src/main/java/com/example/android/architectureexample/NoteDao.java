package com.example.android.architectureexample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
//describe all the operation that do in the database
@Dao
public interface NoteDao {
    // Insert anew element into the database
    @Insert
    void insert (Note note);
    //Delete Element from database
    @Delete
    void delete(Note note);
    //delete all the database
    @Query("DELETE FROM note_table")
    void deleteAllNotes();
    // get information from the database
    @Query("SELECT *FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>>getAllNotes();
    //Update information from the database
    @Update()
    void update(Note note);
}
