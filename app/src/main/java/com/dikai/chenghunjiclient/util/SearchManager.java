package com.dikai.chenghunjiclient.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dikai.chenghunjiclient.entity.JiedanRenBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lucio on 2018/10/26.
 */

public class SearchManager {

    private static SearchDBHelper helper;
    private static SQLiteDatabase db;
    private static SearchManager mDBManager;

    private SearchManager() {

    }

    public static SearchManager getInstance(Context context) {
        if (mDBManager == null) {
            mDBManager = new SearchManager();
        }
        if (helper == null) {
            helper = new SearchDBHelper(context);
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

    public void addRecord(JiedanRenBean bean) {
        if(!userHasExisted(bean.getUserId())){
            if(getCount() < 10){
                ContentValues cv = new ContentValues();
                cv.put("userid", bean.getUserId());
                cv.put("username", bean.getName());
                cv.put("userphone",bean.getPhone());
                db.insert("search", null, cv);//执行插入操作
            }else {
                Cursor c = queryUser();
                c.moveToFirst();
                db.delete("search", "_id = ?", new String[]{"" + c.getInt(c.getColumnIndex("_id"))});
                Log.e("_id========","" + c.getInt(c.getColumnIndex("_id")));

                ContentValues cv = new ContentValues();
                cv.put("userid", bean.getUserId());
                cv.put("username", bean.getName());
                cv.put("userphone",bean.getPhone());
                db.insert("search", null, cv);//执行插入操作
            }
        }
    }

    public List<JiedanRenBean> getRecord(){
        List<JiedanRenBean> temp = new ArrayList<>();
        Cursor c = queryUser();
        while (c.moveToNext()) {
            temp.add(new JiedanRenBean(c.getString(c.getColumnIndex("userid")),
                    c.getString(c.getColumnIndex("username")),
                    c.getString(c.getColumnIndex("userphone"))));
            Log.e("_id==============","" + c.getInt(c.getColumnIndex("_id")));
        }
        Collections.reverse(temp);
        return temp;
    }

    private long getCount(){
        Cursor cursor = queryUser();
        int count = cursor.getCount();
        Log.e("getCount==============",count+"");
        return count;
    }

    private Cursor queryUser() {
        Cursor c = db.rawQuery("SELECT * FROM search", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }

}

