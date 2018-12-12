package com.dikai.chenghunjiclient.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dikai.chenghunjiclient.entity.NewUserInfo;

/**
 * Created by Lucio on 2018/8/9.
 */

public class NewUserDBManager {
    private static NewUserDBHelper helper;
    private static SQLiteDatabase db;
    private static NewUserDBManager mDBManager;

    private NewUserDBManager() {

    }

    public static NewUserDBManager getInstance(Context context){
        if(mDBManager == null){
            mDBManager = new NewUserDBManager();
        }
        if(helper == null){
            helper = new NewUserDBHelper(context);
            db = helper.getWritableDatabase();
        }
        return mDBManager;
    }

    private boolean userHasExisted(String userID){
        boolean hasExisted = false;
        Cursor c = queryUser();
        while (c.moveToNext()) {
            if(userID.equals(c.getString(c.getColumnIndex("userid")))) {
                hasExisted = true;
                break;
            }
        }
        return hasExisted;
    }

    public void updateUserInfo(NewUserInfo info) {
//        userid TEXT, logo TEXT, username TEXT, profession TEXT, phone TEXT, supid TEXT
        ContentValues cv = new ContentValues();
        cv.put("userid", info.getUserId());
        cv.put("logo",info.getHeadportrait());
        cv.put("username",info.getName());
        cv.put("phone",info.getPhone());
        cv.put("profession",info.getProfession());
        cv.put("supid",info.getFacilitatorId());
        db.update("userinfo", cv, "_id = ?", new String[]{"0"});
    }

    public NewUserInfo getUserInfo() {
        NewUserInfo info = new NewUserInfo("","","","","","");
        Cursor c = queryUser();
        while (c.moveToNext()) {
            if("0".equals("" + c.getInt(c.getColumnIndex("_id")))){
                info.setUserId(c.getString(c.getColumnIndex("userid")));
                info.setName(c.getString(c.getColumnIndex("username")));
                info.setHeadportrait(c.getString(c.getColumnIndex("logo")));
                info.setPhone(c.getString(c.getColumnIndex("phone")));
                info.setProfession(c.getString(c.getColumnIndex("profession")));
                info.setFacilitatorId(c.getString(c.getColumnIndex("supid")));

            }
        }
        Log.e("数据库查询",info.toString());
        return info;
    }

    public Cursor queryUser() {
        Cursor c = db.rawQuery("SELECT * FROM userinfo", null);
        return c;
    }
    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
