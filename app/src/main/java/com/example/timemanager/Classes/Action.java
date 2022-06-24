package com.example.timemanager.Classes;

import android.app.AlarmManager;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class Action {
    int id;
    String name, description;
    Calendar time;
    AlarmManager.AlarmClockInfo info;

    public Action(String name, String description, Calendar time,int id) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public AlarmManager.AlarmClockInfo getInfo() {
        return info;
    }

    public void setInfo(AlarmManager.AlarmClockInfo info) {
        this.info = info;
    }

    public Action() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
