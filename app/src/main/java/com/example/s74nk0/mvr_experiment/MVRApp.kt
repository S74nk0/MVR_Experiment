package com.example.s74nk0.mvr_experiment

import android.app.Application
import com.raizlabs.android.dbflow.config.FlowManager

/**
 * Created by S74nk0 on 8. 01. 2016.
 * tole je base app extended samo za dbflow komunikacijo
 */
class MVRApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FlowManager.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        FlowManager.destroy()
    }
}
