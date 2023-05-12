package com.example.uas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "crud";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        final String sql = "CREATE TABLE mahasiswa (id INTEGER PRIMARY KEY autoincrement, name TEXT NOT NULL, npm TEXT NOT NULL, jurusan TEXT NOT NULL)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS mahasiswa");
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAll(){
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        String QUERY = "SELECT * FROM mahasiswa";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("npm", cursor.getString(2));
                map.put("jurusan", cursor.getString(3));
                list.add(map);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public ArrayList<HashMap<String, String>> getSearch(String x){
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        String QUERY = "SELECT * FROM mahasiswa WHERE name LIKE '%"+x+"%' OR npm LIKE '%"+x+"%' OR jurusan LIKE '%"+x+"%'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("npm", cursor.getString(2));
                map.put("jurusan", cursor.getString(3));
                list.add(map);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void insert(String name, String npm, String jurusan){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "INSERT INTO mahasiswa (name, npm, jurusan) VALUES ('"+name+"', '"+npm+"', '"+jurusan+"')";
        database.execSQL(QUERY);
    }

    public void update(int id, String name, String npm, String jurusan){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "UPDATE mahasiswa SET name = '"+name+"', npm = '"+npm+"', jurusan = '"+jurusan+"' WHERE id = "+id;
        database.execSQL(QUERY);
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "DELETE FROM mahasiswa WHERE id = "+id;
        database.execSQL(QUERY);
    }
}
