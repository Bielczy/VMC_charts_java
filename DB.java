package com.example.bielczy.vmc_charts_java.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Mateusz S on 06.12.2017.
 */
@Database(
        entities = {
                TemperatureLog.class,
                OvenCurrentLog.class,
        },
        version = 1,
        exportSchema = false
)
public abstract class DB extends RoomDatabase {
    public static String DB_NAME = "vmc_database";

    public abstract TemperatureLogDao temperatureLogs();

    public abstract OvenCurrentsLogDao ovenCurrentsLogs();

    private static volatile DB INSTANCE;

    static DB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DB.class, "Logs_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    void generateTemperatureLogs(){
        List<TemperatureLog> logs = new ArrayList<>();

        long baseTimeMs = new Date(2018, 11,21, 10,11,0).getTime();

        int dateStep = 1000 * 60 * 5;

        for(int i = 0; i < 100; i++){
            TemperatureLog log = new TemperatureLog();

            log.setHumidity(new Random().nextFloat() % 100);
            log.setTemperature((new Random().nextFloat() % 100) - 20);

            baseTimeMs += dateStep;
            Date nextDate = new Date(baseTimeMs);
            log.setDate(DateFormatter.toString(nextDate));

            logs.add(log);
        }

        temperatureLogs().insertAll(logs);
    }
    //tutaj da sie zrobic override na metode, ktora uruchamia sie raz po utworzeniu bazy danych

   // tutorial na   https://android.jlelse.eu/pre-populate-room-database-6920f9acc870

}

