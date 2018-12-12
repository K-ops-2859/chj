package com.dikai.chenghunjiclient.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Lucio on 2016/12/1.
 */
public class UserDBManager {

    private static UserDBHelper helper;
    private static SQLiteDatabase db;
    private static UserDBManager mDBManager;

    private UserDBManager() {

    }

    public static UserDBManager getInstance(Context context){
        if(mDBManager == null){
            mDBManager = new UserDBManager();
        }
        if(helper == null){
            helper = new UserDBHelper(context);
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


    public void updateUserInfo(String userName, String userPwd) {
        ContentValues cv = new ContentValues();
        cv.put("userName", userName);
        cv.put("userPwd",userPwd);
        db.update("userinfo", cv, "_id = ?", new String[]{"0"});
    }

    public String[] getUserInfo() {
        String[] data = {"",""};
        Cursor c = queryUser();
        while (c.moveToNext()) {
            if("0".equals("" + c.getInt(c.getColumnIndex("_id")))){
                data = new String[]{
                        c.getString(c.getColumnIndex("username")),
                        c.getString(c.getColumnIndex("userpwd"))};
            }
//            Log.e("数据库查询","" + c.getInt(c.getColumnIndex("_id")));
        }
        return data;
    }

    void updateLocation(String userID, String location) {
        try {
            if(!userHasExisted(userID)){
                ContentValues cv = new ContentValues();
                cv.put("userid", userID);
                db.insert("userinfo", null, cv);//执行插入操作
            }
            ContentValues cv = new ContentValues();
            cv.put("res1", location);
            db.update("userinfo", cv, "userid = ?", new String[]{userID});
        }catch (Exception e){
            Log.e("updateLocation：",e.toString());
        }
    }


    public String getLocation(String userID){
        String location = "";
        Cursor c = queryUser();
        while (c.moveToNext()) {
            if(userID.equals(c.getString(c.getColumnIndex("userid")))){
                location = c.getString(c.getColumnIndex("res1"));
            }
        }
        return location;
    }

    public void updateFirstUse() {
        ContentValues cv = new ContentValues();
        cv.put("res2", "1");
        db.update("userinfo", cv, "_id = ?", new String[]{"0"});
    }

    public boolean isFirstUse(){
        boolean isFirst = true;
        Cursor c = queryUser();
        while (c.moveToNext()) {
            if("0".equals("" + c.getInt(c.getColumnIndex("_id")))){
                String data = c.getString(c.getColumnIndex("res2"));
                if(data != null && "1".equals(data))
                    isFirst = false;
            }
//            Log.e("数据库查询","" + c.getInt(c.getColumnIndex("_id")));
        }
        return isFirst;
    }
//==============================================================================
    public void updateHomeAd(String ad) {
        ContentValues cv = new ContentValues();
        cv.put("res3", ad);
        db.update("userinfo", cv, "_id = ?", new String[]{"0"});
    }

    public String getHomeAd(){
        String ad = "";
        Cursor c = queryUser();
        while (c.moveToNext()) {
            if("0".equals("" + c.getInt(c.getColumnIndex("_id")))){
                String data = c.getString(c.getColumnIndex("res3"));
                if(data != null)
                    ad = data;
            }
        }
        return ad;
    }

    public void updateHomeAdType(int type) {
        ContentValues cv = new ContentValues();
        cv.put("res4", type);
        db.update("userinfo", cv, "_id = ?", new String[]{"0"});
    }

    public int getHomeAdType(){
        int type = 0;
        Cursor c = queryUser();
        while (c.moveToNext()) {
            if("0".equals("" + c.getInt(c.getColumnIndex("_id")))){
                int data = c.getInt(c.getColumnIndex("res4"));
                type = data;
            }
        }
        return type;
    }

    public void updateHomeAdCode(String code) {
        ContentValues cv = new ContentValues();
        cv.put("res5", code);
        db.update("userinfo", cv, "_id = ?", new String[]{"0"});
    }

    public String getHomeAdCode(){
        String code = "";
        Cursor c = queryUser();
        while (c.moveToNext()) {
            if("0".equals("" + c.getInt(c.getColumnIndex("_id")))){
                String data = c.getString(c.getColumnIndex("res5"));
                if(data != null)
                    code = data;
            }
        }
        return code;
    }
//==============================================================================
//
//    public void updateLogoVer(String userID, String logoVer) {
//
//        if(!userHasExisted(userID)){
//            ContentValues cv = new ContentValues();
//            cv.put("userid", userID);
//            db.insert("userinfo", null, cv);//执行插入操作
//        }
//        ContentValues cv = new ContentValues();
//        cv.put("logover", logoVer);
//        db.update("userinfo", cv, "userid = ?", new String[]{userID});
//    }
//
//    public String getLogoVer(String userID){
//        String ver = "0";
//        Cursor c = queryUser();
//        while (c.moveToNext()) {
//            if(userID.equals("" + c.getInt(c.getColumnIndex("userid")))){
//                ver = c.getLong(c.getColumnIndex("logover")) + "";
//            }
//        }
//        Log.e("数据库查询ver：============","" + ver);
//        return ver;
//    }

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
