package com.example.s74nk0.mvr_experiment.data.db.tables.position;

import android.hardware.SensorEvent;

import com.example.s74nk0.mvr_experiment.data.db.DatabaseMain;
import com.example.s74nk0.mvr_experiment.data.db.tables.base.CommonMotionBase;
import com.example.s74nk0.mvr_experiment.data.db.tables.base.CommonPositionBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
@Table(databaseName = DatabaseMain.NAME, tableName = MagneticFieldUncalibrated.NAME)
public class MagneticFieldUncalibrated extends CommonPositionBase {
    public static final String NAME = "MagneticFieldUncalibrated";

    @Column
    @NotNull
    public float x_iron_bias; // 3
    @Column
    @NotNull
    public float y_iron_bias; // 4
    @Column
    @NotNull
    public float z_iron_bias; // 5

    @Override
    public void set(SensorEvent event, long iThrow_id) {
        super.set(event, iThrow_id);
        x_iron_bias = event.values[3];
        y_iron_bias = event.values[4];
        z_iron_bias = event.values[5];
    }

    public static String getCsvStringHeader() {
        return String.format("%s,%s",
                CommonPositionBase.getCsvStringHeader(),
                "values[3], values[4], values[5]");
    }

    @Override
    public String getCsvStringValues() {
        return String.format("%s,%s",
                super.getCsvStringValues(),
                String.format("%f,%f,%f", x_iron_bias, y_iron_bias, z_iron_bias));
    }

}
