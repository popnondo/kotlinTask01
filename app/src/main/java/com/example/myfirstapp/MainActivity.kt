package com.example.myfirstapp

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.Vibrator
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class MainActivity : AppCompatActivity(),SensorEventListener {

    var hensuu = "change"
    var jinja = ""
    val teisuu = "not changed"
    lateinit var manager : SensorManager
    lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val baibu = pref.getBoolean("button",false)

        battleBtn.visibility = if(baibu) View.VISIBLE else View.INVISIBLE

        val colors = arrayOf(
            "not EX-HARD","not HARD","not CLEAR")

        val groupDoudesho : RadioGroup = findViewById(R.id.doudesho)
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            colors // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        dekinexe.adapter = adapter

        dekinexe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                jinja = item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        groupDoudesho.setOnCheckedChangeListener{ _, checkedId: Int ->
            when (checkedId) {
                R.id.taiketsu01 -> hensuu = "FUJIMURA"
                R.id.taiketsu02 -> hensuu = "URESHINO"
                R.id.taiketsu03 -> hensuu = "MISTER"
                R.id.taiketsu04 -> hensuu = "SUZUMUSHI"
            }
        }

        //画像
        yumekawa.setImageResource(R.drawable.doubutsu1)

        //センサー
        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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
            val toast = Toast.makeText(this,"加速度：${v}",Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.item01){
            val toSetteiActivity = Intent(this,SetteiActivity::class.java)
            startActivity(toSetteiActivity)
        }
        else if (item?.itemId == R.id.item02){
            val toAboutActivity = Intent(this,AboutActivity::class.java)
            startActivity(toAboutActivity)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN){
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            if(pref.getBoolean("vibration",false)){
                vibrator.vibrate(50L)
            }
        }
        return super.onTouchEvent(event)
    }

    //button押下したら。
    fun changeTextView(view: View) {
        messageText.text = hensuu

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        if(pref.getBoolean("vibration",false)){
            val pattern = longArrayOf(3000, 1000, 2000, 5000, 3000, 1000)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val vibrationEffect = VibrationEffect.createWaveform(pattern, -1)
                vibrator.vibrate(vibrationEffect)
            } else {
                println("YUTAFO")
                vibrator.vibrate(pattern, -1)
            }
        }
    }

    fun rotationActivity(view: View){
        val transform = TranslateAnimation(0f,0f,0f,-200f)
        transform.repeatMode = Animation.REVERSE
        transform.repeatCount = 5
        transform.duration = 100

        val comboy = TranslateAnimation(0f,-36f,yumekawa.width/2f,yumekawa.height/2f)
        comboy.duration = 200

        val anime = AnimationSet(true)
        anime.addAnimation(transform)
        anime.addAnimation(comboy)

        yumekawa.startAnimation(anime)
//        yumekawa.setImageResource(R.drawable.doubutsu2)
    }

    fun moveActivity(view: View) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("NIKUOJA",editText.text.toString())
        startActivity(intent)
    }
}
