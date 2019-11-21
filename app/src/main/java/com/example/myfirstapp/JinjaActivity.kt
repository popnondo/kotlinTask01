package com.example.myfirstapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class JinjaActivity : AppCompatActivity(), SensorEventListener {

    lateinit var manager : SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jinja)

        //センサー
        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onPause() {
        super.onPause()
        manager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        var sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        manager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val v = event?.values?.get(0)
        if(v != null && 10 < v){
            val toast = Toast.makeText(this,"加速度：${v}", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

}