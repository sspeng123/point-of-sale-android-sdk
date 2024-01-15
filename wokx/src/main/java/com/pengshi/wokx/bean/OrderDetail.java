package com.pengshi.wokx.bean;

public class OrderDetail {
    private Integer _id;			    // 主键
    private Integer orderID;			// 订单号
    private Integer menuID;				// 菜谱号
    private Integer num;				// 数量
    private Integer state;				// 状态 0待烹饪 1烹饪中 2出锅
    private String remark;				// 备注


    public OrderDetail() {
        super();
    }
    public OrderDetail(Integer _id, Integer orderID, Integer menuID,
                       Integer num, Integer state, String remark) {
        super();
        this._id = _id;
        this.orderID = orderID;
        this.menuID = menuID;
        this.num = num;
        this.state = state;
        this.remark = remark;
    }
    public Integer get_id() {
        return _id;
    }
    public void set_id(Integer _id) {
        this._id = _id;
    }
    public Integer getOrderID() {
        return orderID;
    }
    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }
    public Integer getMenuID() {
        return menuID;
    }
    public void setMenuID(Integer menuID) {
        this.menuID = menuID;
    }
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
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

