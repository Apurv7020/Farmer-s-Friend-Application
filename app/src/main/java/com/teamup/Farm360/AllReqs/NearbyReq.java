package com.teamup.Farm360.AllReqs;

public class NearbyReq {
    String id, title, description, cat, location;

    public NearbyReq() {
    }

    public NearbyReq(String id, String title, String description, String cat, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cat = cat;
        this.location = location;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}