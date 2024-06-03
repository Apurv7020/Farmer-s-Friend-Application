package com.teamup.Farm360.AllReqs;


import androidx.annotation.Keep;

@Keep
public class WeatherCityReq {

    private String name, region, url;


    public WeatherCityReq() {
    }

    public WeatherCityReq(String name, String region, String url) {
        this.name = name;
        this.region = region;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}