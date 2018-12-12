package com.dikai.chenghunjiclient.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lucio on 2016/12/1.
 */
public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 2;

    public UserDBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("CREATE TABLE IF NOT EXISTS number" +
//                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,userid INTEGER, other INTEGER, friends INTEGER, date INTEGER, myorder INTEGER, " +
//                "othermsg TEXT, friendsmsg TEXT, datemsg TEXT, myordermsg TEXT)");
//        db.execSQL(sql);//执行SQL语句
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, logover INTEGER, username TEXT, userpwd TEXT, res1 TEXT, res2 TEXT)");
        String sql = "insert into userinfo(_id) values ('0')";//插入操作的SQL语句
        db.execSQL(sql);//执行SQL语句
        db.execSQL("ALTER TABLE userinfo ADD COLUMN res3 TEXT");
        db.execSQL("ALTER TABLE userinfo ADD COLUMN res4 INTEGER");
        db.execSQL("ALTER TABLE userinfo ADD COLUMN res5 TEXT");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS todaycity" +
//                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, userid INTEGER, cityid TEXT, cityname TEXT, isopen TEXT)");
//        db.execSQL("ALTER TABLE number ADD COLUMN discover INTEGER");
        if(oldVersion == 1){
            db.execSQL("ALTER TABLE userinfo ADD COLUMN res3 TEXT");
            db.execSQL("ALTER TABLE userinfo ADD COLUMN res4 INTEGER");
            db.execSQL("ALTER TABLE userinfo ADD COLUMN res5 TEXT");
        }
    }
}