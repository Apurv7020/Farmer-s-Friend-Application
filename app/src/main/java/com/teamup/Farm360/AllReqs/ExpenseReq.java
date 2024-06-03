package com.teamup.Farm360.AllReqs;


import androidx.annotation.Keep;


@Keep
public class ExpenseReq {

    int id;
    private String mobile, name, expense, cat, description, timer, title;


    public ExpenseReq() {
    }

    public ExpenseReq(int id, String mobile, String name, String expense, String cat, String description, String timer, String title) {
        this.id = id;
        this.mobile = mobile;
        this.name = name;
        this.expense = expense;
        this.cat = cat;
        this.description = description;
        this.timer = timer;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}