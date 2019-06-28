package com.demo.ccss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 成君 943193747@qq.com
 * on 2019/6/26  18:26
 */
public class UtilDao {
    private DatabaseUtil du;
    private SQLiteDatabase db;

    public UtilDao(Context context){
        du = new DatabaseUtil(context);
        db = du.getWritableDatabase();
    }


    /**
     * 添加数据
     * */
    public void addData(String tableName,String[] key,String[] values){
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < key.length; i ++){
            contentValues.put(key[i],values[i]);
        }
        db.insert(tableName,null,contentValues);
        contentValues.clear();
    }

    /**
     * 删除数据
     * */
    public int delData(String where,String[] values){
        int del_data;
        del_data = db.delete("UserInfo",where,values);
        return del_data;
    }

    /**
     * 修改数据
     * */
    public void update(String[] values){
        db.execSQL("update UserInfo set userName=?,userPhone=?,userSort=? where userName=? ",values);
    }

    /**
     * 查询数据
     * */
    public List<User> inquireData(){
        List<User> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select userName,userPhone,userSort" +
                " from UserInfo",null);
        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String phone = cursor.getString(1);
            String sort = cursor.getString(2);

            User user = new User();
            user.setName(name);
            user.setPhone(phone);
            user.setSort(sort);

            list.add(user);
        }

        return list;
    }

    /**
     * 根据分类查询数据
     */
    public List<User> listBySort(String sort){
        List<User> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select userName,userPhone,userSort" +
                " from UserInfo",null);
        while(cursor.moveToNext()){
            if (sort.equals(cursor.getString(2))) {
                String name = cursor.getString(0);
                String phone = cursor.getString(1);
                User user = new User();
                user.setName(name);
                user.setPhone(phone);
                user.setSort(sort);
                list.add(user);
            }
        }
        return list;
    }

    /**
     * 关闭数据库连接
     * */
    public void getClose(){
        if(db != null){
            db.close();
        }
    }
}