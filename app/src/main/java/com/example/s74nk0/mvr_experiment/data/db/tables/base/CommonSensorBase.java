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
    public long throw_id;
    @Column
    @NotNull
    public long timestamp;
    @Column
    @NotNull
    public long accuracy;
    @Column
    @NotNull
    public long system_timestamp;

    public void set(SensorEvent event, long iThrow_id) {
        throw_id = iThrow_id;
        timestamp = event.timestamp;
        accuracy = event.accuracy;
        system_timestamp = System.currentTimeMillis();
    }
}
