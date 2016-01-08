package com.example.s74nk0.mvr_experiment.data.db.tables.base;

import android.hardware.SensorEvent;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
public abstract class CommonPositionBase extends CommonSensorBase {
    // common sensor data
    @Column
    @NotNull
    public float x_component; // 0
    @Column
    @NotNull
    public float y_component; // 1
    @Column
    @NotNull
    public float z_component; // 2
    // methods
    @Override
    public void set(SensorEvent event, long iThrow_id) {
        super.set(event, iThrow_id);
        x_component = event.values[0];
        y_component = event.values[1];
        z_component = event.values[2];
    }
}
