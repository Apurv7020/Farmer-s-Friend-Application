package com.teamup.Farm360.AllReqs;


import androidx.annotation.Keep;


@Keep
public class WeatherReq {

    private String temp_c, time, icon, text;


    public WeatherReq() {
    }

    public WeatherReq(String temp_c, String time, String icon, String text) {
        this.temp_c = temp_c;
        this.time = time;
        this.icon = icon;
        this.text = text;
    }

    public String getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(String temp_c) {
        this.temp_c = temp_c;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}