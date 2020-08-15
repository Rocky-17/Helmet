package com.example.lenovo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Mysqlitehelper extends SQLiteOpenHelper {
    public Mysqlitehelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Mysqlitehelper(Context context){
        super(context,Constant.DARABASE_NAME,null,Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("","----oncreat----");
        String sql=" create table shiyan(_id Integer primary key,sign Integer,focus Integer," +
                "face Integer,course varchar(10),time Integer)";
        db.execSQL(sql);//执行sql语句
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("","----onupgrade----");
    }
}
