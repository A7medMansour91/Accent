package com.ahmedmansour.accent10.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import com.ahmedmansour.accent10.BuildConfig;

import java.util.ArrayList;

/**
 * Created by Ahmed Mansour on 11/28/2017.
 */

public class DBAccent extends SQLiteOpenHelper{

    private Context context;
    public  static  final String DBNAME ="Accent.sqlite3";
    public  static  final String DBLOCATION = Environment.getDataDirectory()+"/data/"+ BuildConfig.APPLICATION_ID+"/databases/";
    public SQLiteDatabase CursorFactory;

    public DBAccent(Context context) {
        super(context, DBNAME, null , 1);
        this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
    //    db.execSQL("CREATE TABLE if not exists MoviesLibary ( `ID_Movie` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, `MovieName` TEXT, `MoviePath` TEXT, `SubtitlePath` TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop TABLE if exists MoviesLibary");
        onCreate(db);
    }

    public void openDatabase() {

    String dbPath = context.getDatabasePath(DBNAME).getPath();

        if (CursorFactory != null && CursorFactory.isOpen()) {
            return;
        }
        CursorFactory=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
        Toast.makeText(context ,"open database",Toast.LENGTH_LONG).show();
    }

    public void closeDatabase() {
        if (CursorFactory != null) {
            CursorFactory.close();
        }

    }

   public void Insert_TFSPath(String Title,String FilmPath,String SubPath){

       SQLiteDatabase db    = this.getWritableDatabase();
       ContentValues values = new ContentValues();
       values.put("MovieName", Title);
       values.put("MoviePath", FilmPath);
       values.put("SubtitlePath", SubPath);

       db.insert("MoviesLibary", null, values);
       Toast.makeText(context ,"add to database",Toast.LENGTH_LONG).show();
       db.close();
    }

    public ArrayList getAllMovies(){

        Toast.makeText(context ," start show",Toast.LENGTH_LONG).show();
    ArrayList arrayList =new ArrayList();
    SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from MoviesLibary",null);
        cursor.moveToNext();
        while (cursor.isAfterLast()==false){
            String MovieName=cursor.getString(cursor.getColumnIndex("MovieName"));
            arrayList.add(MovieName);
            cursor.moveToNext();
        }
        Toast.makeText(context ," show",Toast.LENGTH_LONG).show();
     //   db.close();
        return arrayList;

    }
}

