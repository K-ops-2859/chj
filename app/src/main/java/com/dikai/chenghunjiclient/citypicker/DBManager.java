package com.dikai.chenghunjiclient.citypicker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static DBManager mDBManager;
    private static DBHelper helper;
    private static SQLiteDatabase db;
    private DBManager(){}

    public static DBManager getInstance(Context context){
        if(mDBManager == null){
            mDBManager = new DBManager();
        }
        if(helper == null){
            helper = new DBHelper(context.getApplicationContext());
            try {
                helper.createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        db = helper.getWritableDatabase();
        return mDBManager;
    }
      

    /** 
     * query all persons, return list 
     * @return List<Person> 
     */  
    public List<Province> queryProvinces() {
        List<Province> list = new ArrayList<>();
        Cursor c = queryLocation(1 + "");
//        Toast.makeText(mContext, "" + c.getColumnCount(), Toast.LENGTH_SHORT).show();
        while (c.moveToNext()) {
            String id = c.getString(0);
            String regionId = c.getString(c.getColumnIndex("REGION_ID"));
            String regionName = c.getString(c.getColumnIndex("REGION_NAME"));
            list.add(new Province(id,regionId,regionName));
        }
        c.close();
        closeDB();
        return list;
    }

    public List<City> queryCity(String parentId) {
        List<City> list = new ArrayList<>();
        Cursor c = queryLocation(parentId);

        while (c.moveToNext()) {

            int id = c.getInt(0);
            int regionId = c.getInt(c.getColumnIndex("REGION_ID"));
            String regionName = c.getString(c.getColumnIndex("REGION_NAME"));
            list.add(new City(id+"",regionId+"",regionName));
        }
        c.close();
        closeDB();
        return list;
    }

    public List<Country> queryCountry(String parentId) {
        List<Country> list = new ArrayList<>();
        Cursor c = queryLocation(parentId);
        while (c.moveToNext()) {

            String id = c.getString(0);
            String regionId = c.getString(c.getColumnIndex("REGION_ID"));
            String regionName = c.getString(c.getColumnIndex("REGION_NAME"));
            list.add(new Country(id,regionId,regionName));
        }
        c.close();
        closeDB();
        return list;
    }

    public String getCodeCity(String code) {
        String temp = "";
        if(code != null && !"".equals(code)){
            Cursor c = queryCode(code);
            while (c.moveToNext()) {
                String regionId = c.getString(c.getColumnIndex("REGION_ID"));
                String name = c.getString(c.getColumnIndex("REGION_NAME"));
                temp = regionId + "," + name;
                Log.e("查询执行5", "----------->" + temp);
            }
            c.close();
            closeDB();
        }
        return temp;
    }
    public String getRegionID(String CityCode) {
        String regionID = null;
        Cursor c = queryCode(CityCode);
        while (c.moveToNext()) {
            regionID = c.getString(c.getColumnIndex("REGION_ID"));
//                String name = c.getString(c.getColumnIndex("REGION_NAME"));
        }
        c.close();
        closeDB();
        return regionID;
    }

    public String getCityName (String regionCode) {
        Cursor c = queryRegion(regionCode);
        Log.e("执行至此1","============" + c.toString());
        String name = null;
//        String parentID = null;
        while (c.moveToNext()) {
            Log.e("执行至此2","============" + c.toString());
            name = c.getString(c.getColumnIndex("REGION_NAME"));
//            parentID = c.getString(c.getColumnIndex("PARENT_ID"));
        }
        c.close();
        closeDB();
        return name;
    }

    public String getInfoCity (String regionCode) {
        Cursor c = queryRegion(regionCode);
        Log.e("执行至此1","============" + c.toString());
        String name = null;
        String parentID = null;
        while (c.moveToNext()) {
            Log.e("执行至此2","============" + c.toString());
            name = c.getString(c.getColumnIndex("REGION_NAME"));
            parentID = c.getString(c.getColumnIndex("PARENT_ID"));
        }
        Cursor c1 = queryRegion(parentID);
        while (c1.moveToNext()) {
            name = c1.getString(c1.getColumnIndex("REGION_NAME")) + "-" + name;
        }
        c.close();
        closeDB();
        return name;
    }

    private Cursor queryLocation(String parentId) {
        Cursor c = db.rawQuery("select * from Region where parent_ID=" + parentId, null);
        return c;
    }


    private Cursor queryCode(String regionCode) {

        Log.e("查询执行4", "----------->");
        Cursor c = db.rawQuery("select * from Region where REGION_CODE=" + regionCode, null);
        return c;
    }

    private Cursor queryRegion(String regionCode) {
        Cursor c = db.rawQuery("select * from Region where REGION_ID=" + regionCode, null);
        return c;
    }

    private void closeDB() {
        db.close();  
    }
} 