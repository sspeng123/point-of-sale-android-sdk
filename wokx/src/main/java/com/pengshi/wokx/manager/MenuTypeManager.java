package com.pengshi.wokx.manager;

/**
 * @author shipeng
 * 1/11/24
 */
//菜谱管理类

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pengshi.wokx.bean.MenuType;
import com.pengshi.wokx.db.DBHelper;
import com.pengshi.wokx.db.MenuTypes;
import com.pengshi.wokx.db.Menus;

import java.util.ArrayList;
import java.util.List;

public class MenuTypeManager {
    private Context context = null;

    public MenuTypeManager(Context context) {
        this.context = context;
    }

    @SuppressLint("Range")
    // 按照所有菜谱类型
    public List<MenuType> queryMenuTypes(){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select _id,id,name from "+MenuTypes.TABLE;
        // 执行语句
        Cursor cursor = database.rawQuery(sql,null);
        // 循环遍历结果集
        List<MenuType> list = new ArrayList<MenuType>();

        while(cursor.moveToNext()){
            Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            MenuType menuType = new MenuType(_id,id,name);
            list.add(menuType);
        }

        // 关闭数据库
        database.close();
        return list;
    }

    public void add(String id , String name){
// 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "insert into "+ MenuTypes.TABLE+" (id,name) " + "values ('"
                + id + "','"+ name+"')";
        // 执行语句
        database.execSQL(sql);
        Log.d("sql","sql-------------------" + sql);
        // 关闭数据库
        database.close();
    }

    public void  delete(){
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getWritableDatabase();

        String sql = "delete from "+ MenuTypes.TABLE;
        database.execSQL(sql);
        String sql1 = "delete from "+ Menus.TABLE;
        database.execSQL(sql1);
        database.close();
    }

}
