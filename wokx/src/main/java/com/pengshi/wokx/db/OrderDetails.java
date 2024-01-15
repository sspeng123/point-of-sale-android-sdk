package com.pengshi.wokx.db;

import android.provider.BaseColumns;

/**
 * @author pengshi
 * 订单明细类
 */
public final class OrderDetails implements BaseColumns {
    // 表名
    public static final String TABLE="OrderDetailTal";
    // 表字段常量
    public static final String ORDERID="orderID";			// 订单号
    public static final String MENUID="menuID";				// 菜谱号
    public static final String NUM="num";					// 数量
    public static final String STATE="state";				// 状态 0待烹饪 1烹饪中 2出锅
    public static final String REMARK="remark";				// 备注
}