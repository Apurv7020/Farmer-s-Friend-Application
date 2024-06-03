package com.teamup.Farm360.AllReqs;

public class CityReq {
    int id;
    String city;
    boolean selected;

    public CityReq() {
    }

    public CityReq(int id, String city, boolean selected) {
        this.id = id;
        this.city = city;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}