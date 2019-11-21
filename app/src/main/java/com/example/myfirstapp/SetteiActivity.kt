package com.example.myfirstapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


//設定を呼び出すためのクラス。
class SetteiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentTrance = fragmentManager.beginTransaction()
        fragmentTrance.replace(android.R.id.content,SetteiFragment())
        fragmentTrance.commit()
    }
}