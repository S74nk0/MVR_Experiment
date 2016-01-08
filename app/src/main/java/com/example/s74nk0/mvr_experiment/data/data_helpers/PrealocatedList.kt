package com.example.s74nk0.mvr_experiment.data.data_helpers

import android.hardware.SensorEvent
import com.example.s74nk0.mvr_experiment.data.db.tables.base.CommonSensorBase
import com.raizlabs.android.dbflow.runtime.TransactionManager
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
class PrealocatedList<T: CommonSensorBase>(val mListSize: Long, factory: () -> T) {
    private var mCurrent: Int = 0
    private var mElements: MutableList<T> = arrayListOf()
    init {
        for(index in 0..mListSize) {
            mElements.add(factory())
        }
    }

    fun setNext(event: SensorEvent, throw_id: Long) {
        if(isValid()) {
            mElements.get(mCurrent).set(event, throw_id)
            ++mCurrent
        }
    }

    fun isValid() = mCurrent < mListSize

    fun save() {
        // shrani samo podseznam ki ima shranjene vrednosti
        TransactionManager.getInstance().addTransaction(
                SaveModelTransaction(ProcessModelInfo.withModels(
                        mElements.subList(0, mCurrent)
                )))
    }
}