package com.pengshi.wokx.manager;


//菜谱管理类

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pengshi.wokx.bean.Menu;
import com.pengshi.wokx.db.DBHelper;
import com.pengshi.wokx.db.Menus;

import java.util.ArrayList;
import java.util.List;
/**
 * @author shipeng
 * 1/11/24
 */
public class MenuManager {
    private Context context = null;

    public MenuManager(Context context) {
        this.context = context;
    }

    @SuppressLint("Range")
    // 按照类型查询所有菜谱
    public List<Menu> queryMenus(String type_id){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select _id,id,name,categories_id,hidden,Code,price,priceType,taxRates,taxRates_id,modifierGroups_id,modifierGroups_Name from "+Menus.TABLE+" where categories_id=?";
        //String sql = "";
        // 执行语句
        Cursor cursor = database.rawQuery(sql,new String[]{type_id+""});
        // 循环遍历结果集
        List<Menu> list = new ArrayList<Menu>();

        while(cursor.moveToNext()){
            Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String categories_id = cursor.getString(cursor.getColumnIndex("categories_id"));
            Integer inthidden = cursor.getInt(cursor.getColumnIndex("hidden"));
            boolean hidden =false;
            if(inthidden == 1){
                hidden = true;
            }
            String Code = cursor.getString(cursor.getColumnIndex("Code"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String priceType = cursor.getString(cursor.getColumnIndex("priceType"));
            double taxRates = cursor.getDouble(cursor.getColumnIndex("taxRates"));
            String taxRates_id = cursor.getString(cursor.getColumnIndex("taxRates_id"));
            String modifierGroups_id = cursor.getString(cursor.getColumnIndex("modifierGroups_id"));
            String modifierGroups_Name = cursor.getString(cursor.getColumnIndex("modifierGroups_Name"));
            Menu user = new Menu(_id,categories_id,id,name,hidden,price,Code,priceType,taxRates,taxRates_id,modifierGroups_id,modifierGroups_Name);
            list.add(user);
        }
        Log.d("sql--","sql-------------------list" + list.size());
        // 关闭数据库
        database.close();
        return list;
    }
    // 根据ID查询一个菜谱
    public Menu query(int id){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "";
        //"select _id,typeId,name,description,price,unit,pic,remark from "+Menus.TABLE+" where _id=?";
        // 执行语句
        Cursor cursor = database.rawQuery(sql,new String[]{id+""});
        // 循环遍历结果集
        Menu  menu =null;
        if(cursor.moveToFirst()){
    	/*	Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
    		Integer typeId = cursor.getInt(cursor.getColumnIndex("typeId"));
    		String name = cursor.getString(cursor.getColumnIndex("name"));
    		String description = cursor.getString(cursor.getColumnIndex("description"));
    		Integer price = cursor.getInt(cursor.getColumnIndex("price"));
    		String unit = cursor.getString(cursor.getColumnIndex("unit"));
    		String pic = cursor.getString(cursor.getColumnIndex("pic"));
    		String remark = cursor.getString(cursor.getColumnIndex("remark"));
    		menu = new Menu(_id,typeId,name,description,price,unit,pic,remark);*/
        }

        // 关闭数据库
        database.close();
        return menu;
    }

    public void add(List<Menu> ml){
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        for(Menu menu : ml){
            Log.d("sql", "sql-------------------add(List<Menu> ml)" );
            boolean ishidden = false;
            if(menu.isHidden()){
                ishidden = true;
            }
            String sql = "insert into "+ Menus.TABLE+" (id,name,categories_id,hidden,Code,price,priceType,taxRates,taxRates_id,modifierGroups_id,modifierGroups_Name) " + "values ('"
                    + menu.getId() +
                    "','"+ menu.getName()+
                    "','"+ menu.getCATEGORIES_ID()+
                    "','"+ ishidden +
                    "','"+ menu.getCode()+
                    "','"+ menu.getPrice()+
                    "','" + menu.getPriceType()+
                    "','" + menu.getTaxRates()+
                    "','"+ menu.getTaxRates_id()+
                    "','"+ menu.getModifierGroups_id()+
                    "','"+ menu.getModifierGroups_Name()+
                    "')";
            database.execSQL(sql);
        }
        database.close();
    }
}
