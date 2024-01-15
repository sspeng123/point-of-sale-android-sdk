package com.pengshi.wokx.bean;

public class OrderItem {
    private Integer _id;			    // 订单ID
    private Integer menuID;			    // 菜谱号
    private String name;				// 菜名
    private Integer num;				// 数量
    private Integer price;				// 价格
    private String unit;				// 单位
    private Integer state;				// 状态 0待烹饪 1烹饪中 2出锅
    private String remark;				// 备注


    public OrderItem(Integer _id, Integer menuID, String name, Integer num, Integer price,
                     String unit, Integer state, String remark) {
        super();
        this._id = _id;
        this.menuID = menuID;
        this.name = name;
        this.num = num;
        this.price = price;
        this.unit = unit;
        this.state = state;
        this.remark = remark;
    }



    public Integer getMenuID() {
        return menuID;
    }

    public void setMenuID(Integer menuID) {
        this.menuID = menuID;
    }

    public OrderItem() {
        super();
    }




    public String getUnit() {
        return unit;
    }




    public void setUnit(String unit) {
        this.unit = unit;
    }




    public Integer get_id() {
        return _id;
    }




    public void set_id(Integer _id) {
        this._id = _id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}