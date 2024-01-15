package com.pengshi.wokx.db;

import android.provider.BaseColumns;
/**
 *
 * @author pengshi
 * 数据库工具类
 */
public final class Menus implements BaseColumns {
    // 默认排序常量
    public static final String DEFAULT_SORT_ORDER = "id DESC";// 按桌号排序
    // 表名
    public static final String TABLE="MenuTal";
    // 表字段常量
    public static final String ID = "id";			//类型
    public static final String CATEGORIES_ID = "categories_id";			//类型
    public static final String HIDDEN= "hidden";				// 名称
    public static final String NAME= "name";				// 名称
    public static final String CODE= "Code";	// 菜谱介绍
    public static final String PRICE= "price";				// 价格
    public static final String PRICETYPE= "priceType";				// 单位
    public static final String TAXRATES= "taxRates";					// 图片
    public static final String TAXRATES_ID= "taxRates_id";			// 备注
    public static final String MODIFIERGROUPS_ID= "modifierGroups_id";					// 图片
    public static final String MODIFIERGROUPS_NAME= "modifierGroups_Name";			// 备注
}
