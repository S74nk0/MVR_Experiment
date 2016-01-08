package com.example.s74nk0.mvr_experiment.util

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import com.example.s74nk0.mvr_experiment.data.data_helpers.UUIDTriplet
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
object UUID_Device {
    fun getDeviceUUID(context: Context): UUIDTriplet {
        // #1 compute IMEI; this requires READ_PHONE_STATE
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val IMEI_String = telephonyManager?.deviceId ?: "NO IMEI - TelephonyManager N/A"

        // #2 compute DEVICE ID
        val DEVICE_ID = "35" + //we make this look like a valid IMEI
                Build.BOARD.length%10 + Build.BRAND.length%10 +
                Build.CPU_ABI.length%10 + Build.DEVICE.length%10 +
                Build.DISPLAY.length%10 + Build.HOST.length%10 +
                Build.ID.length%10 + Build.MANUFACTURER.length%10 +
                Build.MODEL.length%10 + Build.PRODUCT.length%10 +
                Build.TAGS.length%10 + Build.TYPE.length%10 +
                Build.USER.length%10 //13 digits
        // #3 android ID - unreliable
        val contentResolver = context.contentResolver
        val Android_ID = if(contentResolver != null) { Secure.getString(contentResolver, Secure.ANDROID_ID) } else { "NO ANDROID_ID - ContentResolver N/A" }

        // #4 wifi manager, read MAC address - requires  android.permission.ACCESS_WIFI_STATE or comes as null
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val WLAN_MAC = wifiManager?.connectionInfo?.macAddress ?: "NO WLAN_MAC - WifiManager N/A"

        // #5 Bluetooth MAC address  android.permission.BLUETOOTH required
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val BT_MAC = bluetoothAdapter?.address ?: "NO BT_MAC - BluetoothAdapter N/A"

        // #6 sum the data, for hashing
        val HashAlg = "SHA-1"
        var messageDigest: MessageDigest? = null
        try {
            messageDigest = MessageDigest.getInstance(HashAlg)
        } catch(e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        val UUID_String = if(messageDigest != null) {
            val Long_ID = IMEI_String + DEVICE_ID + Android_ID + WLAN_MAC + BT_MAC
            messageDigest.update(Long_ID.toByteArray(), 0, Long_ID.length)
            val hashData = messageDigest.digest()
            var stringBulider = StringBuilder()
            for(i in 0..hashData.size-1) {
                val b_int = (0xFF and hashData[i].toInt())
                if( b_int <= 0xF) { stringBulider.append("0") }
                stringBulider.append(Integer.toHexString(b_int))
            }
            stringBulider.toString().toUpperCase()
        } else {
            "MessageDigest.getInstance(\"${HashAlg}\") N/A"
        }

        val HUMAN_READABLE_LOG = "Android Unique Device ID\n\n" +
                "IMEI     :\t $IMEI_String \n" +
                "DeviceID :\t $DEVICE_ID \n" +
                "AndroidID:\t $Android_ID \n" +
                "WLAN_MAC :\t $WLAN_MAC \n" +
                "BT_MAC   :\t $BT_MAC \n\n" +
                "UNIQUE ID (${HashAlg}):\t $UUID_String \n"


        // + DEV_ID + Android_ID + WLAN_MAC + BT_MAC
        return UUIDTriplet(UUIDString = UUID_String, HumanReadable = HUMAN_READABLE_LOG, HashAlgorithm = HashAlg)
    }
}