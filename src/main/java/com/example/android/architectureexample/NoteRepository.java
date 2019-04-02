package com.example.android.architectureexample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>>allNotes;
    public NoteRepository(Application application){
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        noteDao=noteDatabase.noteDao();
        allNotes=noteDao.getAllNotes();
    }
    public void insert(Note note){
        new InsertNoteAsynctask(noteDao).execute(note);
    }
    public void update(Note note){
     new UpdateNoteAsynctask(noteDao).execute(note);
    }
    public void delete(Note note){
    new DeleteNoteAsynctask(noteDao).execute(note);
    }
    public void deleteAllNotes(){
      new DeleteAllNoteAsynctask(noteDao).execute();
    }
    public LiveData<List<Note>>getAllNotes(){
        return allNotes;
    }
    public static class InsertNoteAsynctask extends AsyncTask<Note,Void,Void>{
      private NoteDao noteDao;
      public InsertNoteAsynctask(NoteDao noteDao){
          this.noteDao=noteDao;
      }
        @Override
        protected Void doInBackground(Note... notes) {
          noteDao.insert(notes[0]);
            return null;
        }
    }
    public static class UpdateNoteAsynctask extends AsyncTask<Note,Void,Void>{
     private NoteDao noteDao;
     public UpdateNoteAsynctask(NoteDao noteDao){
         this.noteDao=noteDao;
     }
        @Override
        protected Void doInBackground(Note... notes) {
           noteDao.update(notes[0]);
            return null;
        }
    }
    public static class DeleteNoteAsynctask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        public DeleteNoteAsynctask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    public static class DeleteAllNoteAsynctask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
        public DeleteAllNoteAsynctask(NoteDao noteDao){
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
