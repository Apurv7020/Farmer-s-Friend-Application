package com.teamup.Farm360.AllReqs;

public class ChecklistReq {

    String id, title, date, email;
    int checked;

    public ChecklistReq() {
    }


    public ChecklistReq(String id, String title, String date, String email, int checked) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.email = email;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}