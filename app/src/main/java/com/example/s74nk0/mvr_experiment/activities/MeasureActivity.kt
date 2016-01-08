package com.example.s74nk0.mvr_experiment.activities

import android.app.Activity
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.LinearLayout
import com.example.s74nk0.mvr_experiment.R
import com.example.s74nk0.mvr_experiment.data.data_helpers.ThrowSensorsMeasurements
import com.example.s74nk0.mvr_experiment.util.Util


class MeasureActivity : Activity(), SensorEventListener {
    internal var mExitParams: BooleanArray = booleanArrayOf(false, false, false)
    internal var mMainLayout: LinearLayout? = null
    internal var mSensorManager: SensorManager? = null

    internal var mIsMeasure = false

    internal var throwSensorsMeasurements: ThrowSensorsMeasurements = ThrowSensorsMeasurements()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure)

        // naredimo array button id-ev in nastavimo da mora vse gumbe pritisniti da gre ven
        // s tem si nekak zagarantiramo da ne gre med zajemanjem ven
        val buttonIds = intArrayOf(R.id.exit1, R.id.exit2, R.id.exit3)
        for(index in 0..2) {
            var buttonView = findViewById(buttonIds[index])
            if(buttonView != null) {
                buttonView.setOnClickListener {
                    mExitParams[index] = true
                    if(checkIfFinished()) {
                        finish()
                    }
                }
            } else {
                Log.e("MeasureActivity", "Button je null nekaj si zajebal idiot")
            }
        }
        // indicator merjenja
        mMainLayout = findViewById(R.id.mainLayout) as LinearLayout?
        if(mMainLayout != null && mMainLayout is LinearLayout) {

            mMainLayout?.setOnTouchListener { view, motionEvent ->
                val action = motionEvent.action
                if(action == MotionEvent.ACTION_DOWN) {
                    actionDown()
                } else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    actionUp()
                }
                true
            }

        } else {
            Log.e("MeasureActivity", "mMainLayout je null nekaj si zajebal idiot")
        }
        // sensor manager setting
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        registerSensorManagerListeners()
    }

    private fun registerSensorManagerListeners() {
        for(sensorInt in Util.REGISTER_SENSORS) {
            var sensor = mSensorManager?.getDefaultSensor(sensorInt)
            if(sensor != null) {
                mSensorManager?.registerListener(
                        this,
                        sensor,
                        SensorManager.SENSOR_DELAY_FASTEST
                        )
            } else {
                // TODO
                Log.d("MeasureActivity", "sensor je null")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // STUB
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // ce ni measure izhod iz funkcije
        if(!mIsMeasure) { return }
        if(event != null) {
            if(event.sensor != null) {
                throwSensorsMeasurements.setNext(event.sensor.type, event)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerSensorManagerListeners()
    }

    override fun onStop() {
        super.onStop()
        mSensorManager?.unregisterListener(this)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(this)
    }

    private fun actionDown() {
        mIsMeasure = false
        mMainLayout?.setBackgroundColor(Color.GREEN)
        if(throwSensorsMeasurements.getIsStart() == false) {
            throwSensorsMeasurements.start()
        } else {
            throwSensorsMeasurements.end()
            throwSensorsMeasurements = ThrowSensorsMeasurements()
        }
        resetExitParams()
    }

    private fun actionUp() {
        resetExitParams()
        mMainLayout?.setBackgroundColor(Color.BLUE)
        mIsMeasure = true
    }

    private fun resetExitParams() {
        mExitParams = booleanArrayOf(false, false, false)
    }

    private fun checkIfFinished() = mExitParams.reduce { b1, b2 -> b1 && b2 }

    // ce slučajno klikne nzaj naj ignorira zaradi meritev
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false // super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }


}
