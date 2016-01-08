package com.example.s74nk0.mvr_experiment.data.db.tables.base;

import android.hardware.SensorEvent;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
public abstract class CommonBaseId extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;
}
