package com.teamup.Farm360.AllReqs;

public class CropRatesReq {
    String id, title, description, rate, imgUrl;

    public CropRatesReq() {
    }

    public CropRatesReq(String id, String title, String description, String rate, String imgUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rate = rate;
        this.imgUrl = imgUrl;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
