package com.example.myapplication.model;

import java.io.Serializable;

public class Workouts implements Serializable {
    public String name;
    public String rest;
    public String variant;
    public String sets;
    public String time;
    public String reps;
    public String level;

    public Workouts() {

    }

    public Workouts(String name, String rest, String variant, String sets, String reps, String level) {
        this.name = name;
        this.rest = rest;
        this.variant = variant;
        this.sets = sets;
        this.reps = reps;
        this.level = level;
    }


}
