package com.example.myfirstapp
import android.os.Bundle
import android.preference.PreferenceFragment

//設定のレイアウトを出力するクラス。
class SetteiFragment : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }
}