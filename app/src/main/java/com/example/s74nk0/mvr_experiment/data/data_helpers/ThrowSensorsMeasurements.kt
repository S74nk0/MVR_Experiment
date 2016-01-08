package com.example.s74nk0.mvr_experiment.data.data_helpers


import android.hardware.Sensor
import android.hardware.SensorEvent
import com.example.s74nk0.mvr_experiment.data.db.tables.Throw
import com.example.s74nk0.mvr_experiment.data.db.tables.motion.*
import com.example.s74nk0.mvr_experiment.data.db.tables.position.*

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
class ThrowSensorsMeasurements {
    internal val OBJECT_POOL_SIZE: Long = 20000 // sampling rate ki je najverjetneje overkill ampak ajde

    private var isStart = false
    private var throwVar: Throw = Throw()

    // motion stuff
    private var accelerometerPool: PrealocatedList<Accelerometer> = PrealocatedList(OBJECT_POOL_SIZE, ::Accelerometer)
    private var gravityPool: PrealocatedList<Gravity> = PrealocatedList(OBJECT_POOL_SIZE, ::Gravity)
    private var gyroscopePool: PrealocatedList<Gyroscope> = PrealocatedList(OBJECT_POOL_SIZE, ::Gyroscope)
    private var gyroscope_uncalibratedPool: PrealocatedList<GyroscopeUncalibrated> = PrealocatedList(OBJECT_POOL_SIZE, ::GyroscopeUncalibrated)
    private var linear_accelerationPool: PrealocatedList<LinearAcceleration> = PrealocatedList(OBJECT_POOL_SIZE, ::LinearAcceleration)
    private var rotation_vectorPool: PrealocatedList<RotationVector> = PrealocatedList(OBJECT_POOL_SIZE, ::RotationVector)
    // position stuff
    private var game_rotation_vectorPool: PrealocatedList<GameRotationVector> = PrealocatedList(OBJECT_POOL_SIZE, ::GameRotationVector)
    private var geomagnetic_rotatorPool: PrealocatedList<GeomagneticRotationVector> = PrealocatedList(OBJECT_POOL_SIZE, ::GeomagneticRotationVector)
    private var magnetic_fieldPool: PrealocatedList<MagneticField> = PrealocatedList(OBJECT_POOL_SIZE, ::MagneticField)
    private var magnetic_field_uncalibratedPool: PrealocatedList<MagneticFieldUncalibrated> = PrealocatedList(OBJECT_POOL_SIZE, ::MagneticFieldUncalibrated)

    fun getIsStart() = isStart

    // methods
    fun start() {
        isStart = true
        throwVar.start()
    }

    fun setNext(type: Int, event: SensorEvent) {
        when(type) {
        // motion
            Sensor.TYPE_ACCELEROMETER -> accelerometerPool.setNext(event, throwVar.id)
            Sensor.TYPE_GRAVITY -> gravityPool.setNext(event, throwVar.id)
            Sensor.TYPE_GYROSCOPE -> gyroscopePool.setNext(event, throwVar.id)
            Sensor.TYPE_GYROSCOPE_UNCALIBRATED -> gyroscope_uncalibratedPool.setNext(event, throwVar.id)
            Sensor.TYPE_LINEAR_ACCELERATION -> linear_accelerationPool.setNext(event, throwVar.id)
            Sensor.TYPE_ROTATION_VECTOR -> rotation_vectorPool.setNext(event, throwVar.id)
        // position
            Sensor.TYPE_GAME_ROTATION_VECTOR -> game_rotation_vectorPool.setNext(event, throwVar.id)
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> geomagnetic_rotatorPool.setNext(event, throwVar.id)
            Sensor.TYPE_MAGNETIC_FIELD -> magnetic_fieldPool.setNext(event, throwVar.id)
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED -> magnetic_field_uncalibratedPool.setNext(event, throwVar.id)
            else -> Unit // do nothing
        }
    }

    fun end() {
        throwVar.end()
        accelerometerPool.save()
        gravityPool.save()
        gyroscopePool.save()
        gyroscope_uncalibratedPool.save()
        linear_accelerationPool.save()
        rotation_vectorPool.save()
        game_rotation_vectorPool.save()
        geomagnetic_rotatorPool.save()
        magnetic_fieldPool.save()
        magnetic_field_uncalibratedPool.save()
    }

}