package com.teamup.Farm360.AllReqs;


import androidx.annotation.Keep;


@Keep
public class KhataReq {

    int id;
    private String name, mobile, expense, cat, description;


    public KhataReq() {
    }

    public KhataReq(int id, String name, String mobile, String expense, String cat, String description) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.expense = expense;
        this.cat = cat;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}