package com.example.myapplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutPlan implements Serializable {

    public String id;
    public String workout;
    public String title;
    public String desc;
    public String level;
    public int totalTime;
    public ArrayList<HashMap<String, String>> workouts;
    public String details;
    public long price;
    public int weeks;

    public WorkoutPlan() {
        this.workouts = new ArrayList<HashMap<String, String>>();

    }

    public WorkoutPlan(String id, String title, String description, long price, int weeks) {
        this.id = id;
        this.title = title;
        this.desc = description;

        this.price = price;
        this.weeks = weeks;
        this.details = details;
        this.workouts = new ArrayList<HashMap<String, String>>();

    }

    public String getPriceText() {
        return "LKR " + Integer.parseInt(String.valueOf(price)) + ".00";
    }
}
