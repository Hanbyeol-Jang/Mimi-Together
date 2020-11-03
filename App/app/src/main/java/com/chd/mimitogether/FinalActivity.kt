package com.chd.mimitogether

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class FinalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        Log.d("myLog", "Here, the Final")
        Log.d("myLog", intent.data.toString())
    }
}