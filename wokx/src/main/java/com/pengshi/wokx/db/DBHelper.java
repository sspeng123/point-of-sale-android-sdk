package com.pengshi.wokx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @author pengshi
 * 数据库工具类
 */
public class DBHelper extends SQLiteOpenHelper {
    // 数据库名称常量
    private static final String DATABASE_NAME = "Meal.db";
    // 数据库版本常量
    private static final int DATABASE_VERSION = 1;
    // 构造方法
    public DBHelper(Context context) {
        // 创建数据库
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    // 创建时调用
    public void onCreate(SQLiteDatabase db) {

        //创建表
        db.execSQL("CREATE TABLE " + MenuTypes.TABLE+ " ("
                + MenuTypes._ID + " INTEGER PRIMARY KEY,"
                + MenuTypes.ID + " TEXT,"
                + MenuTypes.NAME + " TEXT"
                + ");");
/*		//插入数据
		db.execSQL("insert into "+MenuTypes.TABLE+" (name) " +
				"values ('今日特价')");
		db.execSQL("insert into "+MenuTypes.TABLE+" (name) " +
				"values ('招牌菜')");
		db.execSQL("insert into "+MenuTypes.TABLE+" (name) " +
				"values ('主菜')");
		db.execSQL("insert into "+MenuTypes.TABLE+" (name) " +
				"values ('素菜')");
		db.execSQL("insert into "+MenuTypes.TABLE+" (name) " +
				"values ('凉菜')");
		db.execSQL("insert into "+MenuTypes.TABLE+" (name) " +
				"values ('煲汤')");
		db.execSQL("insert into "+MenuTypes.TABLE+" (name) " +
				"values ('酒水')");*/

        //创建表
        db.execSQL("CREATE TABLE " + Menus.TABLE+ " ("
                + Menus._ID + " INTEGER PRIMARY KEY,"
                + Menus.ID + " TEXT,"
                + Menus.CATEGORIES_ID + " TEXT,"
                + Menus.NAME + " TEXT,"
                + Menus.HIDDEN + " INTEGER,"
                + Menus.PRICE + " REAL,"
                + Menus.CODE + " TEXT,"
                + Menus.PRICETYPE + " TEXT,"
                + Menus.TAXRATES_ID + " TEXT,"
                + Menus.TAXRATES + " TEXT,"
                + Menus.MODIFIERGROUPS_ID + " TEXT,"
                + Menus.MODIFIERGROUPS_NAME + " REAL"
                + ");");

        db.execSQL("CREATE TABLE " + Orders.TABLE+ " ("
                + Orders._ID + " INTEGER PRIMARY KEY,"
                + Orders.ORDERTIME + " TEXT,"
                + Orders.USERID + " INTEGER,"
                + Orders.TABLEID + " INTEGER,"
                + Orders.ISPAY + " INTEGER,"
                + Orders.PERSONNUM + " INTEGER,"
                + Orders.REMARK + " TEXT"
                + ");");

        db.execSQL("CREATE TABLE " + OrderDetails.TABLE+ " ("
                + OrderDetails._ID + " INTEGER PRIMARY KEY,"
                + OrderDetails.ORDERID+ " INTEGER,"
                + OrderDetails.MENUID+ " INTEGER,"
                + OrderDetails.NUM + " INTEGER,"
                + OrderDetails.STATE + " INTEGER,"
                + OrderDetails.REMARK + " TEXT"
                + ");");
    }

    // 版本更新时调用
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除表
/*		db.execSQL("DROP TABLE IF EXISTS UserTal");
		db.execSQL("DROP TABLE IF EXISTS TableTbl");*/
        db.execSQL("DROP TABLE IF EXISTS MenuTypeTal");
        db.execSQL("DROP TABLE IF EXISTS MenuTal");
        db.execSQL("DROP TABLE IF EXISTS OrderTal");
        db.execSQL("DROP TABLE IF EXISTS OrderDetailTal");
        onCreate(db);
    }

}
