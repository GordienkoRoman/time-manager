package com.example.timemanager.restAPI;

public class InsultModel {

    int number,shown,active;
    String comment="";
    String created_by="";
    String insult="";
    String language ="";
    String created="";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type="";

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getShown() {
        return shown;
    }

    public void setShown(int shown) {
        this.shown = shown;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getInsult() {
        return insult;
    }

    public void setInsult(String insult) {
        this.insult = insult;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
