package com.pengshi.wokx.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pengshi.wokx.db.DBHelper;
import com.pengshi.wokx.db.Orders;
import com.pengshi.wokx.util.MyUtil;

/**
 * @author shipeng
 * 1/11/24
 */
public class OrderManager {
    private Context context = null;

    public OrderManager(Context context) {
        this.context = context;
    }

    // 添加订单
    public void addOrder(int user_id,int table_id,String table_no,int personNum){

        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "insert into "+Orders.TABLE+" (orderTime,userID,tableID,isPay,personNum,remark) " + "values ('"
                + MyUtil.getCurrentTime() + "','"
                + user_id + "','"
                + table_id + "','0','"
                + personNum + "','')";
        // 执行语句
        database.execSQL(sql);
        // 关闭数据库
        database.close();
    }

    public void truncate(){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();

        // 执行语句
        database.execSQL("drop table "+ Orders.TABLE);
        //创建表
        database.execSQL("CREATE TABLE " + Orders.TABLE+ " ("
                + Orders._ID + " INTEGER PRIMARY KEY,"
                + Orders.ORDERTIME + " TEXT,"
                + Orders.USERID + " INTEGER,"
                + Orders.TABLEID + " INTEGER,"
                + Orders.ISPAY + " INTEGER,"
                + Orders.PERSONNUM + " INTEGER,"
                + Orders.REMARK + " TEXT"
                + ");");
        // 关闭数据库
        database.close();
    }
}
