package com.salon.mysalon.model;

/**
 * Created by anirbanjana on 23/11/2016.
 */

public class Service {
    private String name;
    private int time;
    private int id;
    private boolean isSelected;

    public Service(int id, String name, int time, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
