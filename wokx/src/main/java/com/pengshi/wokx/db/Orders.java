package com.pengshi.wokx.db;

import android.provider.BaseColumns;

/**
 * @author pengshi
 * 订单类
 */
public final class Orders implements BaseColumns {
    // 表名
    public static final String TABLE="OrderTal";

    // 表字段常量
    public static final String ORDERTIME="orderTime";			// 下单时间
    public static final String USERID="userID";				    // 下单用户
    public static final String TABLEID="tableID";			    // 桌号
    public static final String ISPAY="isPay";					// 是否结账 0未结账 1结账
    public static final String PERSONNUM="personNum";			// 就餐人数
    public static final String REMARK="remark";					// 备注
}
