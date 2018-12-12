package com.dikai.chenghunjiclient.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dikai.chenghunjiclient.entity.AccountBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucio on 2018/11/13.
 */

public class AccountDBManager {
    private static AccountDBHelper helper;
    private static SQLiteDatabase db;
    private static AccountDBManager mDBManager;

    private AccountDBManager() {

    }

    public static AccountDBManager getInstance(Context context) {
        if (mDBManager == null) {
            mDBManager = new AccountDBManager();
        }
        if (helper == null) {
            helper = new AccountDBHelper(context);
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
//    userid TEXT, username TEXT, userphone TEXT, userlogo TEXT
    public void addRecord(AccountBean bean) {
        if(!userHasExisted(bean.getUserId())){
            ContentValues cv = new ContentValues();
            cv.put("userid", bean.getUserId());
            cv.put("username", bean.getUserName());
            cv.put("userphone",bean.getUserPhone());
            cv.put("userlogo",bean.getUserLogo());
            db.insert("account", null, cv);//执行插入操作
        }
    }

    public List<AccountBean> getRecord(){
        List<AccountBean> temp = new ArrayList<>();
        Cursor c = queryUser();
        while (c.moveToNext()) {
            temp.add(new AccountBean(c.getString(c.getColumnIndex("userid")),
                    c.getString(c.getColumnIndex("username")),
                    c.getString(c.getColumnIndex("userphone")),
                    c.getString(c.getColumnIndex("userlogo"))));
            Log.e("_id==============","" + c.getInt(c.getColumnIndex("_id")));
        }
//        Collections.reverse(temp);
        return temp;
    }

    public void clearAdd(String nowUserID){
        Cursor c = queryUser();
        while (c.moveToNext()) {
            if(!nowUserID.equals(c.getString(c.getColumnIndex("userid")))){
                db.delete("account", "_id = ?", new String[]{"" + c.getInt(c.getColumnIndex("_id"))});
            }
        }
    }
    public void clearAll(){
        Cursor c = queryUser();
        while (c.moveToNext()) {
            db.delete("account", "_id = ?", new String[]{"" + c.getInt(c.getColumnIndex("_id"))});
        }
    }

    private Cursor queryUser() {
        Cursor c = db.rawQuery("SELECT * FROM account", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
