package com.pengshi.wokx.bean;

public class Order {
    private Integer _id;			    // 主键
    private String orderTime;			// 下单时间
    private Integer userID;				// 下单用户
    private Integer tableID;			// 桌号
    private Integer isPay;				// 是否结账 0未结账 1结账
    private String personNum;			// 就餐人数
    private String remark;				// 备注
    public Integer get_id() {
        return _id;
    }
    public void set_id(Integer _id) {
        this._id = _id;
    }
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public Integer getUserID() {
        return userID;
    }
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    public Integer getTableID() {
        return tableID;
    }
    public void setTableID(Integer tableID) {
        this.tableID = tableID;
    }
    public Integer getIsPay() {
        return isPay;
    }
    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
    public String getPersonNum() {
        return personNum;
    }
    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }


}

