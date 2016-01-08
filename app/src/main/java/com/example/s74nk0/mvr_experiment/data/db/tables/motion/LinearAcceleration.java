package com.example.s74nk0.mvr_experiment.data.db.tables.motion;

import com.example.s74nk0.mvr_experiment.data.db.DatabaseMain;
import com.example.s74nk0.mvr_experiment.data.db.tables.base.CommonMotionBase;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
@Table(databaseName = DatabaseMain.NAME, tableName = LinearAcceleration.NAME)
public class LinearAcceleration extends CommonMotionBase {
    public static final String NAME = "LinearAcceleration";
}
