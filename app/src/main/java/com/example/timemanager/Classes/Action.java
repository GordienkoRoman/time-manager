package com.example.timemanager.Classes;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class Action {
    String name,description;
    Calendar time;

    public Action(String name, String description, Calendar time) {
        this.name = name;
        this.description = description;
        this.time = time;
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
