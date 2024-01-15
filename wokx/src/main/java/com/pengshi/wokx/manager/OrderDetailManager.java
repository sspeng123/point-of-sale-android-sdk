package com.pengshi.wokx.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pengshi.wokx.bean.OrderItem;
import com.pengshi.wokx.db.DBHelper;
import com.pengshi.wokx.db.OrderDetails;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailManager {
    private Context context = null;

    public OrderDetailManager(Context context) {
        this.context = context;
    }

    // 添加订单明细项
    public void addOrderItem(int menuID){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "insert into "+ OrderDetails.TABLE+" (menuID,num,state) " + "values ('"
                + menuID + "','1','0')";
        // 执行语句
        database.execSQL(sql);
        // 关闭数据库
        database.close();
    }


    //查询是否存在
    public boolean query(int menuID){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select menuID from "+OrderDetails.TABLE+" where menuID=?";
        // 执行语句
        Cursor cursor = database.rawQuery(sql, new String[]{menuID+""});

        if(cursor.moveToFirst()){
            return true;
        }

        // 关闭数据库
        database.close();
        return false;
    }
    @SuppressLint("Range")
    //查询是否存在
    public int query(int menuID,int state){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select _id from "+OrderDetails.TABLE+" where menuID=? and state=?";
        // 执行语句
        Cursor cursor = database.rawQuery(sql, new String[]{menuID+"",state+""});

        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex("_id"));
        }

        // 关闭数据库
        database.close();
        return -1;
    }
    @SuppressLint("Range")
    //查询状态
    public int queryState(int id){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select state from "+OrderDetails.TABLE+" where _id=?";
        // 执行语句
        Cursor cursor = database.rawQuery(sql, new String[]{id+""});

        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex("state"));
        }

        // 关闭数据库
        database.close();
        return -1;
    }
    //根据状态查询是否存在
    public boolean queryByState(int state){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select menuID from "+OrderDetails.TABLE+" where state=?";
        // 执行语句
        Cursor cursor = database.rawQuery(sql, new String[]{state+""});

        if(cursor.moveToFirst()){
            return true;
        }

        // 关闭数据库
        database.close();
        return false;
    }
    @SuppressLint("Range")
    //更据菜单ID查询数量
    public int queryNum(int menuID){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select sum(num) nums from "+OrderDetails.TABLE+" where menuID=?";
        // 执行语句
        Cursor cursor = database.rawQuery(sql, new String[]{menuID+""});

        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex("nums"));
        }
        // 关闭数据库
        database.close();
        return 0;
    }
    @SuppressLint("Range")
    //查询总订单价格、总订单数量
    public int[] queryTotal(){
        int total[]=new int[2];
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select sum(m.price*o.num) total_prices,sum(num) total_num from OrderDetailTal o,MenuTal m where o.menuID=m._id";
        // 执行语句
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            total[0]=cursor.getInt(cursor.getColumnIndex("total_prices"));
            total[1]=cursor.getInt(cursor.getColumnIndex("total_num"));
        }
        // 关闭数据库
        database.close();
        return total;
    }
    //修改数量
    public void updateNum(int id,int update_num){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "update "+OrderDetails.TABLE+" set num=num+'"+update_num+"' where _id=?";
        // 执行语句
        database.execSQL(sql,new String[]{id+""});

        // 关闭数据库
        database.close();
    }
    //修改状态:提交、结账,从一种状态到另一种状态
    public void updateState(int from_state,int to_state){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "update "+OrderDetails.TABLE+" set state=? where state=?";
        // 执行语句
        database.execSQL(sql,new String[]{to_state+"",from_state+""});

        // 关闭数据库
        database.close();
    }
    //修改备注
    public void updateRemark(int id,String remark){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "update "+OrderDetails.TABLE+" set remark=? where _id=?";
        // 执行语句
        database.execSQL(sql,new String[]{remark,id+""});

        // 关闭数据库
        database.close();
    }
    //删除：根据ID
    public void delete(int id){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "delete from "+OrderDetails.TABLE+" where _id=?";
        // 执行语句
        database.execSQL(sql,new String[]{id+""});

        // 关闭数据库
        database.close();
    }
    //删除：所有数据
    public void truncate(){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();

        // 执行语句
        database.execSQL("drop table "+OrderDetails.TABLE);
        //创建表
        database.execSQL("CREATE TABLE " + OrderDetails.TABLE+ " ("
                + OrderDetails._ID + " INTEGER PRIMARY KEY,"
                + OrderDetails.ORDERID+ " INTEGER,"
                + OrderDetails.MENUID+ " INTEGER,"
                + OrderDetails.NUM + " INTEGER,"
                + OrderDetails.STATE + " INTEGER,"
                + OrderDetails.REMARK + " TEXT"
                + ");");
        // 关闭数据库
        Log.d("OrderDetailManager","truncate---------------------------------");
        database.close();
    }

    @SuppressLint("Range")
    //查询所有订单
    public List<OrderItem> queryOrderItems(){
        // 获取数据库
        DBHelper db = new DBHelper(this.context);
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select o._id _id,m.name name,m.price price,o.menuID menuID,o.num num,o.state state,o.remark remark from OrderDetailTal o,MenuTal m where o.menuID=m._id";
        // 执行语句
        Cursor cursor = database.rawQuery(sql,null);
        // 循环遍历结果集
        List<OrderItem> list = new ArrayList<OrderItem>();

        while(cursor.moveToNext()){

            Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
            Integer menuID = cursor.getInt(cursor.getColumnIndex("menuID"));
            Integer num = cursor.getInt(cursor.getColumnIndex("num"));
            Integer price = cursor.getInt(cursor.getColumnIndex("price"));
            Integer state = cursor.getInt(cursor.getColumnIndex("state"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            String unit  ="LB";
            OrderItem orderItem = new OrderItem(_id,menuID,name,num,price,unit,state,remark);
            list.add(orderItem);
        }

        // 关闭数据库
        database.close();
        return list;
    }
}
