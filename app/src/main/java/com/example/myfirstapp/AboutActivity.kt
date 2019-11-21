package com.example.myfirstapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)


        var infoVer = packageManager.getPackageInfo(packageName,0)
        apuriVersion.text = "version : " + infoVer.versionName
    }
}