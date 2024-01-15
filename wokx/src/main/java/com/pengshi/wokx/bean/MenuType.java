package com.pengshi.wokx.bean;

public class MenuType {
    private  Integer _id;
    private String id;
    private String name;

    public MenuType() {
        super();
    }

    public MenuType(Integer _id,String id, String name) {
        super();
        this._id = _id;
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }
}
