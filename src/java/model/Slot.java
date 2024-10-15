/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import model.enums.WeekDay;
import java.sql.*;
/**
 *
 * @author ADMIN
 */
public class Slot {
    private int id;
    private WeekDay weekDay;
    private Time timeStart;
    private Time timeEnd;

    public Slot() {
    }

    public Slot(int id, WeekDay weekDay, Time timeStart, Time timeEnd) {
        this.id = id;
        this.weekDay = weekDay;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return "Slot{" + "id=" + id + ", weekDay=" + weekDay + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + '}';
    }
    
    
}
