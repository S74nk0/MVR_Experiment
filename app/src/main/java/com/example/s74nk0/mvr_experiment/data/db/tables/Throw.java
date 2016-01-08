package com.example.s74nk0.mvr_experiment.data.db.tables;


import android.hardware.Sensor;

import com.example.s74nk0.mvr_experiment.data.db.DatabaseMain;
import com.example.s74nk0.mvr_experiment.data.db.tables.base.CommonBaseId;
import com.example.s74nk0.mvr_experiment.data.db.tables.base.CommonSensorBase;
import com.example.s74nk0.mvr_experiment.data.db.tables.motion.Accelerometer;
import com.example.s74nk0.mvr_experiment.data.db.tables.motion.Accelerometer$Table;
import com.example.s74nk0.mvr_experiment.data.db.tables.motion.Gravity;
import com.example.s74nk0.mvr_experiment.data.db.tables.motion.Gyroscope;
import com.example.s74nk0.mvr_experiment.data.db.tables.motion.GyroscopeUncalibrated;
import com.example.s74nk0.mvr_experiment.data.db.tables.motion.LinearAcceleration;
import com.example.s74nk0.mvr_experiment.data.db.tables.motion.RotationVector;
import com.example.s74nk0.mvr_experiment.data.db.tables.position.GameRotationVector;
import com.example.s74nk0.mvr_experiment.data.db.tables.position.GeomagneticRotationVector;
import com.example.s74nk0.mvr_experiment.data.db.tables.position.MagneticField;
import com.example.s74nk0.mvr_experiment.data.db.tables.position.MagneticFieldUncalibrated;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
@Table(databaseName = DatabaseMain.NAME, tableName = Throw.NAME)
public class Throw extends CommonBaseId {
    public static final String NAME = "Throw";

    @Column
    @NotNull
    public long timestamp_start;
    @Column
    @NotNull
    public long timestamp_end;

    public void start() {
        timestamp_start = System.currentTimeMillis();
    }

    public void end() {
        timestamp_end = System.currentTimeMillis();
    }

    public static Throw getWithId(long id) {
        return new Select().from(Throw.class).where(Condition.column(Throw$Table.ID).eq(id)).querySingle();
    }

    public static <T extends CommonSensorBase>List<T> getAll(Class<T> tClass, long throw_id) {
        return new Select().from(tClass).where(Condition.column(Accelerometer$Table.THROW_ID).eq(throw_id)).queryList();
    }

    public <T extends CommonSensorBase>List<T> getAll(Class<T> tClass) {
        return getAll(tClass, this.id);
    }

}
