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
    public long timestamp_start = -1;
    @Column
    @NotNull
    public long timestamp_end = -1;

    public void start() {
        timestamp_start = System.currentTimeMillis();
        save();
    }

    public void end() {
        timestamp_end = System.currentTimeMillis();
        save();
    }

    public String getInfo() {
        return String.format("startTime: %d\n\nendTime: %d", timestamp_start, timestamp_end);
    }

//    public static Throw getWithId(long id) {
//        return new Select().from(Throw.class).where(Condition.column(Throw$Table.ID).eq(id)).querySingle();
//    }

    public static List<Throw> getAll() {
        return new Select().from(Throw.class).queryList();
    }

    public static <T extends CommonSensorBase>List<T> getAll(Class<T> tClass, long throw_id) {
        return new Select().from(tClass).where(Condition.column(Accelerometer$Table.THROW_ID).eq(throw_id)).queryList();
    }

    public <T extends CommonSensorBase>List<T> getAllForSensor(Class<T> tClass) {
        return getAll(tClass, this.id);
    }

    public static Class getClassForType(int type)  {
        switch (type) {
            // motion
            case Sensor.TYPE_ACCELEROMETER : return Accelerometer.class;
            case Sensor.TYPE_GRAVITY : return Gravity.class;
            case Sensor.TYPE_GYROSCOPE : return Gyroscope.class;
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED : return GyroscopeUncalibrated.class;
            case Sensor.TYPE_LINEAR_ACCELERATION : return LinearAcceleration.class;
            case Sensor.TYPE_ROTATION_VECTOR : return RotationVector.class;
            // position
            case Sensor.TYPE_GAME_ROTATION_VECTOR : return GameRotationVector.class;
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR : return GeomagneticRotationVector.class;
            case Sensor.TYPE_MAGNETIC_FIELD : return MagneticField.class;
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED : return MagneticFieldUncalibrated.class;
            default : return Accelerometer.class; // do nothing
        }
    }

}
