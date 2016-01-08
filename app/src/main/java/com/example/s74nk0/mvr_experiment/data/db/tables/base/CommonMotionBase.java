package com.example.s74nk0.mvr_experiment.data.db.tables.base;

import android.hardware.SensorEvent;

import com.example.s74nk0.mvr_experiment.data.db.DatabaseMain;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by S74nk0 on 7. 01. 2016.
 * razred ki ima skupne vrednosti za motion sensorje
 * nekateri imajo se dodatne vrednosti
 */
public abstract class CommonMotionBase extends CommonSensorBase {
    // common sensor data
    @Column
    @NotNull
    public float x_axis; // 0
    @Column
    @NotNull
    public float y_axis; // 1
    @Column
    @NotNull
    public float z_axis; // 2
    // methods
    @Override
    public void set(SensorEvent event, long iThrow_id) {
        super.set(event, iThrow_id);
        x_axis = event.values[0];
        y_axis = event.values[1];
        z_axis = event.values[2];
    }
}
