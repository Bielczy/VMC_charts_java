package com.example.bielczy.vmc_charts_java.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mateusz S on 24.08.2018.
 */

@Dao
public interface OvenCurrentsLogDao {

    @Query("SELECT * FROM oven_currents_logs")
    Single<List<OvenCurrentLog>> getAll();

    @Query("SELECT * FROM oven_currents_logs ORDER BY uid DESC LIMIT :limit")
    Single<List<OvenCurrentLog>> getLast(int limit);

    @Query("SELECT * FROM oven_currents_logs WHERE date BETWEEN :start AND :end")
    Single<List<OvenCurrentLog>> getAllLogs(String start, String end);

    @Insert
    void insertAll(OvenCurrentLog... logs);

    default Completable insert(OvenCurrentLog... logs){
        return Completable.fromRunnable(()->insertAll(logs))
                .subscribeOn(Schedulers.io());
    }
}
