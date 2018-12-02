package com.example.bielczy.vmc_charts_java.db;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Mateusz S on 06.12.2017.
 */

@Entity(tableName = "oven_currents_logs")
public class OvenCurrentLog {

    @PrimaryKey(autoGenerate = true)
    protected long uid;

    @ColumnInfo(name = "lamp")
    private int lamp = 0;

    @ColumnInfo(name = "lamp_on")
    private boolean lampOn = false;

    @ColumnInfo(name = "mag1")
    private int mag1 = 0;

    @ColumnInfo(name = "mag1_power")
    private int mag1Power = 0;

    @ColumnInfo(name = "mag2")
    private int mag2 = 0;

    @ColumnInfo(name = "mag2_power")
    private int mag2Power = 0;

    @ColumnInfo(name = "is_actual")
    private boolean isActual = false;

    @ColumnInfo(name = "tick")
    private long tick;

    @ColumnInfo(name = "date")
    protected String date;

    @Override
    public String toString() {
        return " uid=" + uid + "\n" +
                " systemTime=" + tick + "\n" +
                " lamp=" + lamp + ", lampOn=" + lampOn + "\n" +
                " mag1=" + mag1 + ", mag1Power=" + mag1Power + "\n" +
                " mag2=" + mag2 + ", mag2Power=" + mag2Power + "\n" +
                " isActual=" + isActual +
                " date='" + date + '\'';
    }

    private String serializeTimeStamp(){
        long secondsInStamp = tick% 60;
        long minutesInStamp = (tick / (60)) % 60;
        long hoursInStamp =  (tick / (60 * 60)) % 24;
        long daysInStamp = tick / (60 * 60 * 24);

        return String.valueOf(daysInStamp) + ":" +
                String.valueOf(hoursInStamp) + ":" +
                String.valueOf(minutesInStamp) + ":" +
                String.valueOf(secondsInStamp);
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getLamp() {
        return lamp;
    }

    public void setLamp(int lamp) {
        this.lamp = lamp;
    }

    public boolean isLampOn() {
        return lampOn;
    }

    public void setLampOn(boolean lampOn) {
        this.lampOn = lampOn;
    }

    public int getMag1() {
        return mag1;
    }

    public void setMag1(int mag1) {
        this.mag1 = mag1;
    }

    public int getMag2() {
        return mag2;
    }

    public void setMag2(int mag2) {
        this.mag2 = mag2;
    }

    public int getMag1Power() {
        return mag1Power;
    }

    public void setMag1Power(int mag1Power) {
        this.mag1Power = mag1Power;
    }

    public int getMag2Power() {
        return mag2Power;
    }

    public void setMag2Power(int mag2Power) {
        this.mag2Power = mag2Power;
    }

    public long getTick() {
        return tick;
    }

    public void setTick(long tick) {
        this.tick = tick;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isActual() {
        return isActual;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }

}