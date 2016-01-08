package com.example.s74nk0.mvr_experiment.activities


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.s74nk0.mvr_experiment.R

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToMeasureActivity(v: View) {
        startActivity(Intent(this, MeasureActivity::class.java))
    }
}
