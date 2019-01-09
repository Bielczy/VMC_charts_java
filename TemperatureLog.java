package com.example.bielczy.vmc_charts_java.db;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.widget.DatePicker;

import java.time.LocalDate;
import java.util.Date;

//import com.foodrobotics.vmc.modules.Logger;



@Entity(tableName = "temperature_logs")
public class TemperatureLog {

    @PrimaryKey(autoGenerate = true)
    protected long uid;

    @ColumnInfo(name = "temperature")
    private float temperature = 0;

    @ColumnInfo(name = "humidity")
    private float humidity = 0;

    @ColumnInfo(name = "temperature_sensor_ok")
    private boolean temperatureSensorOk = false;

    @ColumnInfo(name = "freezing_current_consumption")
    private int freezingCurrentConsumption = 0;

    @ColumnInfo(name = "is_freezing_on")
    private int freezingDurationS = 0;

    @ColumnInfo(name = "current_consumption_sensor_ok")
    private boolean isCurrentConsumptionSensorOK = false;

    @ColumnInfo(name = "system_time")
    private long systemTime;

    @ColumnInfo(name = "date")
    protected String date;


    public TemperatureLog() {

    }

    //od sekund
    private String serializeTimeStamp(){
        long secondsInStamp = systemTime% 60;
        long minutesInStamp = (systemTime / (60)) % 60;
        long hoursInStamp =  (systemTime / (60 * 60)) % 24;
        long daysInStamp = systemTime / (60 * 60 * 24);

        return String.valueOf(daysInStamp) + ":" +
                String.valueOf(hoursInStamp) + ":" +
                String.valueOf(minutesInStamp) + ":" +
                String.valueOf(secondsInStamp);
    }

   /* //od milisekund
    private String serializeTimeStamp(){
        long milisInStamp = systemTime % 1000;
        long secondsInStamp = (systemTime / 1000) % 60;
        long minutesInStamp = (systemTime / (1000 * 60)) % 60;
        long hoursInStamp =  (systemTime / (1000 * 60 * 60)) % 24;
        long daysInStamp = systemTime / (1000 * 60 * 60 * 24);

        return String.valueOf(daysInStamp) + ":" +
                String.valueOf(hoursInStamp) + ":" +
                String.valueOf(minutesInStamp) + ":" +
                String.valueOf(secondsInStamp) + "." +
                String.valueOf(milisInStamp);
    }*/

    @Override
    public String toString() {
        return "uid=" + uid +
                ", temperature=  " + temperature +
                ", humidity=  " + humidity +
                ", temperatureSensorOk=  " + temperatureSensorOk  + "\n"+
                ", freezingCurrentConsumption=" + freezingCurrentConsumption +
                ", isFreezingOn=" + freezingDurationS +
                ", isCurrentConsumptionSensorOK=" + isCurrentConsumptionSensorOK +
                ", systemTime=" + serializeTimeStamp() +
                ", date='" + date + '\'';
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setTemperatureSensorOk(boolean temperatureSensorOk) {
        this.temperatureSensorOk = temperatureSensorOk;
    }

    public void setFreezingCurrentConsumption(int freezingCurrentConsumption) {
        this.freezingCurrentConsumption = freezingCurrentConsumption;
    }

    public void setFreezingDurationS(int freezingDurationS) {
        this.freezingDurationS = freezingDurationS;
    }

    public void setCurrentConsumptionSensorOK(boolean currentConsumptionSensorOK) {
        isCurrentConsumptionSensorOK = currentConsumptionSensorOK;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getUid() {
        return uid;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public boolean isTemperatureSensorOk() {
        return temperatureSensorOk;
    }

    public int getFreezingCurrentConsumption() {
        return freezingCurrentConsumption;
    }

    public int getFreezingDurationS() {
        return freezingDurationS;
    }

    public boolean isCurrentConsumptionSensorOK() {
        return isCurrentConsumptionSensorOK;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public String getDate() {
        return date;
    }


}
