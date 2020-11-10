package com.chd.mimitogether

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        startLoading()

    }

    private fun startLoading() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent) //Loagin화면을 띄운다.
            finish() //현재 액티비티 종료
        }, 1000) // 화면에 Logo 2초간 보이기
    } // startLoading Method..

}