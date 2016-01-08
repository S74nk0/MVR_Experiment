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
    public float x_component = 0.0f; // 0
    @Column
    @NotNull
    public float y_component = 0.0f; // 1
    @Column
    @NotNull
    public float z_component = 0.0f; // 2
    // methods
    @Override
    public void set(SensorEvent event, long iThrow_id) {
        super.set(event, iThrow_id);
        x_component = event.values[0];
        y_component = event.values[1];
        z_component = event.values[2];
    }

    public static String getCsvStringHeader() {
        return String.format("%s,%s",
                CommonSensorBase.getCsvStringHeader(),
                "values[0], values[1], values[2]");
    }

    @Override
    public String getCsvStringValues() {
        return String.format("%s,%s",
                super.getCsvStringValues(),
                String.format("%f,%f,%f", x_component, y_component, z_component));
    }
}
