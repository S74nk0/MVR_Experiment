package com.example.s74nk0.mvr_experiment.util

import android.accounts.AccountManager
import android.content.Context
import android.hardware.Sensor
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import com.example.s74nk0.mvr_experiment.data.db.tables.Throw
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

        return stringBuilder.toString()
    }


    // TODO tole se porihtaj da vrne CSV header pri attachmentih
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

    fun getCSVStringForThrow(throw_id: Long) : String {
        return getCSVStringForThrow(Throw.getWithId(throw_id))
    }

    fun getCSVStringForThrow(throwVar: Throw?) : String {
        if(throwVar != null) {

        }
        return "prazno"
    }


}
