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
@Table(databaseName = DatabaseMain.NAME, tableName = RotationVector.NAME)
public class RotationVector extends CommonMotionBase {
    public static final String NAME = "RotationVector";

    @Column
    @NotNull
    public float scalar_rotation_component; // 3

    @Override
    public void set(SensorEvent event, long iThrow_id) {
        super.set(event, iThrow_id);
        scalar_rotation_component = event.values[3];
    }
}
