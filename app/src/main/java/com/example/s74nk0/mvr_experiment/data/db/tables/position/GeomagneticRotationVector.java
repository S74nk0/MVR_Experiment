package com.example.s74nk0.mvr_experiment.data.db.tables.position;

import com.example.s74nk0.mvr_experiment.data.db.DatabaseMain;
import com.example.s74nk0.mvr_experiment.data.db.tables.base.CommonPositionBase;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
@Table(databaseName = DatabaseMain.NAME, tableName = GeomagneticRotationVector.NAME)
public class GeomagneticRotationVector extends CommonPositionBase {
    public static final String NAME = "GeomagneticRotationVector";
}
