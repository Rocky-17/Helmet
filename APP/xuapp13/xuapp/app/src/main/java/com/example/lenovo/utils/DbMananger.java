package com.example.lenovo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/*数据库操作的工具类*/
public class DbMananger {
    private static Mysqlitehelper helper;
    public static Mysqlitehelper getIntance(Context context){
        if(helper == null){
            helper=new Mysqlitehelper(context);
        }
        return helper;
    }

    /**根据sql语句在数据库中执行语句
    * @param db  数据库对象
    * @param sql  sql语句
    **/
    public static void exexSQL(SQLiteDatabase db,String sql){
        if(db!=null){
            if(sql!=null && !"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }

}
