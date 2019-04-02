package com.example.android.architectureexample;
//ties all the pieces together and connects the entities to their corresponding DAO.
// Just as in an SQLiteOpenHelper,
// we have to define a version number and a migration strategy.
// With fallbackToDestructiveMigration
// we can let Room recreate our database
// if we increase the version number.
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
  private static NoteDatabase instance;
  // Abstract method to enter NoteDao class
    public abstract NoteDao noteDao();
    public static synchronized NoteDatabase getInstance(Context context){
        if(instance==null){
            //we will create new database
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }
    //Initiate anew database table if we @instance=null
    private static RoomDatabase.Callback roomCallBack=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynctask(instance).execute();
        }
    };
    private static class PopulateDbAsynctask extends AsyncTask<Void,Void,Void>{
     private NoteDao noteDao;
     private PopulateDbAsynctask(NoteDatabase db){

         noteDao=db.noteDao();
     }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("title 1","description 1",1));
            noteDao.insert(new Note("title 2","description 2",2));
            noteDao.insert(new Note("title 3","description 3",3));
            return null;
        }
    }
}
