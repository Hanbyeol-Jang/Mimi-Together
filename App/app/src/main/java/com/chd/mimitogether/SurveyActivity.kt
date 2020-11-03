package com.chd.mimitogether

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SurveyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("myLog", "SurveyActivity")
        setContentView(R.layout.activity_survey)
    }

}