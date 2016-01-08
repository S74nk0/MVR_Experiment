package com.example.s74nk0.mvr_experiment.data.db.tables.base;

import android.hardware.SensorEvent;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
public abstract class CommonSensorBase extends CommonBaseId {
    @Column
    @NotNull
    public long throw_id = -1;
    @Column
    @NotNull
    public long timestamp = -1;
    @Column
    @NotNull
    public long accuracy = -1;
    @Column
    @NotNull
    public long system_timestamp = -1;

    public void set(SensorEvent event, long iThrow_id) {
        throw_id = iThrow_id;
        timestamp = event.timestamp;
        accuracy = event.accuracy;
        system_timestamp = System.currentTimeMillis();
    }

    public static String getCsvStringHeader() {
        return "timestamp, accuracy, system_timestamp";
    }

    public String getCsvStringValues() {
        return String.format("%d,%d,%d", timestamp, accuracy, system_timestamp);
    }
}
