package com.pengshi.wokx.bean;

public class Menu {


    private Integer _id;
    private String CATEGORIES_ID;
    private String id;
    private boolean hidden;
    private String name;
    private String Code="";
    private double price;
    private String priceType;
    private double taxRates;
    private String taxRates_id;
    private String modifierGroups_id ="";
    private String modifierGroups_Name ="";

    public Menu() {
        super();
    }

    public Menu(Integer _id,String CATEGORIES_ID, String id, String name, boolean hidden,
                double price, String Code, String priceType, double taxRates,String taxRates_id ,String modifierGroups_id,String modifierGroups_Name) {
        super();
        this._id = _id;
        this.CATEGORIES_ID = CATEGORIES_ID;
        this.id = id;
        this.name = name;
        this.hidden = hidden;
        this.price = price;
        this.Code = Code;
        this.priceType = priceType;
        this.taxRates = taxRates;
        this.taxRates_id = taxRates_id;
        this.modifierGroups_id = modifierGroups_id;
        this.modifierGroups_Name = modifierGroups_Name;
    }

    public String getCATEGORIES_ID() {
        return CATEGORIES_ID;
    }

    public void setCATEGORIES_ID(String CATEGORIES_ID) {
        this.CATEGORIES_ID = CATEGORIES_ID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public void setTaxRates(double taxRates) {
        this.taxRates = taxRates;
    }

    public void setTaxRates_id(String taxRates_id) {
        this.taxRates_id = taxRates_id;
    }

    public void setModifierGroups_id(String modifierGroups_id) {
        this.modifierGroups_id = modifierGroups_id;
    }

    public void setModifierGroups_Name(String modifierGroups_Name) {
        this.modifierGroups_Name = modifierGroups_Name;
    }

    public String getId() {
        return id;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return Code;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceType() {
        return priceType;
    }

    public double getTaxRates() {
        return taxRates;
    }

    public String getTaxRates_id() {
        return taxRates_id;
    }

    public String getModifierGroups_id() {
        return modifierGroups_id;
    }

    public String getModifierGroups_Name() {
        return modifierGroups_Name;
    }

    public Integer get_id() {
        return _id;
    }
}
