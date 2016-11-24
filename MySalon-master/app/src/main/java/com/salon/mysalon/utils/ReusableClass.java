package com.salon.mysalon.utils;

/**
 * Created by anirbanjana on 24/11/2016.
 */

public class ReusableClass {
    public static String getHoursMin(int time) {
        int hours = time / 60; //since both are ints, you get an int
        int minutes = time % 60;

        return String.format("%d:%02d", hours, minutes);
    }
}
