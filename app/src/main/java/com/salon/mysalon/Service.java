package com.salon.mysalon;

/**
 * Created by anirbanjana on 23/11/2016.
 */

public class Service {
    private String name;
    private String time;
    private int id;
    private boolean isSelected;

    public Service(int id, String name, String time, boolean isSelected) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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
