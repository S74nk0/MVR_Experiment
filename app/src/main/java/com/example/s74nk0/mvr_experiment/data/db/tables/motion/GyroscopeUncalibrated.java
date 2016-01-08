package com.example.s74nk0.mvr_experiment.data.db.tables.motion;

import android.hardware.SensorEvent;

import com.example.s74nk0.mvr_experiment.data.db.DatabaseMain;
import com.example.s74nk0.mvr_experiment.data.db.tables.base.CommonMotionBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
@Table(databaseName = DatabaseMain.NAME, tableName = GyroscopeUncalibrated.NAME)
public class GyroscopeUncalibrated extends CommonMotionBase {
    public static final String NAME = "GyroscopeUncalibrated";

    @Column
    @NotNull
    public float x_axis_drift; // 3
    @Column
    @NotNull
    public float y_axis_drift; // 4
    @Column
    @NotNull
    public float z_axis_drift; // 5

    @Override
    public void set(SensorEvent event, long iThrow_id) {
        super.set(event, iThrow_id);
        x_axis_drift = event.values[3];
        y_axis_drift = event.values[4];
        z_axis_drift = event.values[5];
    }

    public static String getCsvStringHeader() {
        return String.format("%s,%s",
                CommonMotionBase.getCsvStringHeader(),
                "values[3], values[4], values[5]");
    }

    @Override
    public String getCsvStringValues() {
        return String.format("%s,%s",
                super.getCsvStringValues(),
                String.format("%f,%f,%f", x_axis_drift, y_axis_drift, z_axis_drift));
    }

}
