package com.teamup.Farm360.AllReqs;

public class GovtSchemesReq {
    String id, title, description, link;

    public GovtSchemesReq() {
    }

    public GovtSchemesReq(String id, String title, String description, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}