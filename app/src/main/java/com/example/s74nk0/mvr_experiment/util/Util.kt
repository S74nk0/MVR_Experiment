package com.example.s74nk0.mvr_experiment.util

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import com.example.s74nk0.mvr_experiment.data.db.tables.Throw
import com.example.s74nk0.mvr_experiment.data.db.tables.base.CommonSensorBase
import com.example.s74nk0.mvr_experiment.data.db.tables.motion.*
import com.example.s74nk0.mvr_experiment.data.db.tables.position.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
object Util {

    private val dateTimeFormat = SimpleDateFormat("dd.MM.yyyy - HH:mm:ss")
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    val timeFormat = SimpleDateFormat("HH:mm:ss")

    fun getFormatetDateAndTime(date: Date): String {
        return dateTimeFormat.format(date)
    }

    val formatetDateAndTimeNow: String
        get() = getFormatetDateAndTime(Date())

    private fun getTimeNum(value: Int): String {
        if (value < 10) {
            return "0" + value
        }
        return "" + value
    }

    fun getTimeFromSeconds(secondsDuration: Long): String {
        val seconds = getTimeNum( (secondsDuration % 60).toInt() )
        val minutes = getTimeNum( ((secondsDuration / 60) % 60).toInt() )
        val hours = getTimeNum( ((secondsDuration / (60 * 60)) % 24).toInt() )
        return "$hours:$minutes:$seconds"
    }

    fun StringBuilder.appendAndLogWithNewline(appendString: String, TAG: String) {
        Log.d(TAG, appendString)
        this.append(appendString)
        this.append(newLine)
    }

    /// log stuff
    private val minusSeparator = "----------------------------------------------------------------"
    private val plusSeparator = "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
    private val newLine = System.getProperty("line.separator")

    // log device stuff
    val deviceInfo: String
        get() = "Pulse at: ${Util.formatetDateAndTimeNow}" + newLine +
                "MANUFACTURER: ${Build.MANUFACTURER}" + newLine +
                "BRAND: ${Build.BRAND}" + newLine +
                "PRODUCT: ${Build.PRODUCT}" + newLine +
                "MODEL: ${Build.MODEL}" + newLine


    fun getFulAccountAndDevice(context: Context): String {
        val TAG = "getFulAccountAndDevice"
        val stringBuilder = StringBuilder()
        val am = AccountManager.get(context)
        if (am != null) {
            stringBuilder.appendAndLogWithNewline("AccountManager : Accounts installed", TAG)
            val accounts = am.accounts
            for (ac in accounts) {
                stringBuilder.appendAndLogWithNewline("Accounts : ${ac.name}, ${ac.type}", TAG)
            }
        }
        val tMgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (tMgr != null) {
            stringBuilder.appendAndLogWithNewline("Phone info :", TAG)
            val mPhoneNumber = tMgr.line1Number
            stringBuilder.appendAndLogWithNewline("Phone number : " + mPhoneNumber, TAG)
        }
        stringBuilder.appendAndLogWithNewline("Device info :", TAG)
        stringBuilder.appendAndLogWithNewline(Util.deviceInfo, TAG)

        stringBuilder.appendAndLogWithNewline("Device weight: " + getPhoneWeight(context), TAG)

        // append avaliable sensors
        stringBuilder.appendAndLogWithNewline("Avaliable sensors: ", TAG)
        var sensorManager = context.getSystemService(Activity.SENSOR_SERVICE) as SensorManager
        for(sensorInt in Util.REGISTER_SENSORS) {
            var sensor = sensorManager?.getDefaultSensor(sensorInt)
            if(sensor != null) {
                stringBuilder.appendAndLogWithNewline(getSensorName(sensorInt) + " YES", TAG)
            } else {
                stringBuilder.appendAndLogWithNewline(getSensorName(sensorInt) + " NO", TAG)
            }
        }

        return stringBuilder.toString()
    }

    val REGISTER_SENSORS = intArrayOf(
            // motion
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_GRAVITY,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
            Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_ROTATION_VECTOR,
            // position
            Sensor.TYPE_GAME_ROTATION_VECTOR,
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
            Sensor.TYPE_MAGNETIC_FIELD,
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED
    )

    private fun getCSVHeader(type: Int) : String {
        when(type) {
        // motion
            Sensor.TYPE_ACCELEROMETER -> return Accelerometer.getCsvStringHeader()
            Sensor.TYPE_GRAVITY -> return Gravity.getCsvStringHeader()
            Sensor.TYPE_GYROSCOPE -> return Gyroscope.getCsvStringHeader()
            Sensor.TYPE_GYROSCOPE_UNCALIBRATED -> return GyroscopeUncalibrated.getCsvStringHeader()
            Sensor.TYPE_LINEAR_ACCELERATION -> return LinearAcceleration.getCsvStringHeader()
            Sensor.TYPE_ROTATION_VECTOR -> return RotationVector.getCsvStringHeader()
        // position
            Sensor.TYPE_GAME_ROTATION_VECTOR -> return GameRotationVector.getCsvStringHeader()
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> return GeomagneticRotationVector.getCsvStringHeader()
            Sensor.TYPE_MAGNETIC_FIELD -> return MagneticField.getCsvStringHeader()
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED -> return MagneticFieldUncalibrated.getCsvStringHeader()
            else -> return "Unit" // do nothing
        }
    }

    fun getSensorName(type: Int) : String {
        val typeName = when(type) {
        // motion
            Sensor.TYPE_ACCELEROMETER -> "Accelerometer"
            Sensor.TYPE_GRAVITY -> "Gravity"
            Sensor.TYPE_GYROSCOPE -> "Gyroscope"
            Sensor.TYPE_GYROSCOPE_UNCALIBRATED -> "GyroscopeUncalibrated"
            Sensor.TYPE_LINEAR_ACCELERATION -> "LinearAcceleration"
            Sensor.TYPE_ROTATION_VECTOR -> "RotationVector"
        // position
            Sensor.TYPE_GAME_ROTATION_VECTOR -> "GameRotationVector"
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> "GeomagneticRotationVector"
            Sensor.TYPE_MAGNETIC_FIELD -> "MagneticField"
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED -> "MagneticFieldUncalibrated"
            else -> "Unit" // do nothing
        }

        return typeName
    }

    fun getCSVFileName(type: Int, throw_id: Long) : String {
        val typeName = getSensorName(type)
        return "measure-throwid-$throw_id-$typeName.csv"
    }

//    fun <T: CommonSensorBase>getCSVStringForThrow(type: Int, throw_id: Long, clazz: Class<T>) : String {
//        return getCSVStringForThrow(type, Throw.getWithId(throw_id), clazz)
//    }

    fun <T: CommonSensorBase>getCSVStringForThrow(type: Int, throwVar: Throw?, clazz: Class<T>) : String {
        if(throwVar != null) {
            val TAG = "getFulAccountAndDevice"
            val stringBuilder = StringBuilder()

            // add header
            stringBuilder.appendAndLogWithNewline(Util.getCSVHeader(type) ,TAG)
            // add measurements
            val entries = throwVar.getAllForSensor(clazz)
            for(entry in entries) {
                stringBuilder.appendAndLogWithNewline(entry.csvStringValues ,TAG)
            }
            return stringBuilder.toString()
        }
        return "prazno"
    }

    // weight shared preferances stuff
    val MY_PREFERANCES_KEY = "MY_PREFERANCES_KEY"
    val PHONE_WIGHT_KEY = "PHONE_WIGHT_KEY"
    fun savePhoneWeight(context: Context, phoneWeight: String) {
        val runCheckSettings = context.getSharedPreferences(MY_PREFERANCES_KEY, Context.MODE_PRIVATE) //load the preferences
        val edit = runCheckSettings.edit()
        edit.putString(PHONE_WIGHT_KEY, phoneWeight)
        edit.commit() //apply
    }

    fun getPhoneWeight(context: Context) : String {
        val runCheckSettings = context.getSharedPreferences(MY_PREFERANCES_KEY, Context.MODE_PRIVATE) //load the preferences
        return runCheckSettings.getString(PHONE_WIGHT_KEY, "0")
    }

}
