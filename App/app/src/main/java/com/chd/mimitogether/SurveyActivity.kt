package com.chd.mimitogether

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.chd.mimitogether.dto.SurveyScoreRequest
import com.chd.mimitogether.dto.SurveyStoreResponse
import com.chd.mimitogether.service.SurveyService
import kotlinx.android.synthetic.main.activity_survey.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SurveyActivity : AppCompatActivity() {

    private var number: Int = 0
    private val scoreList: List<SurveyScoreRequest> = List(5) { _ -> SurveyScoreRequest(0f, 0, "") }
    private var storeList: List<SurveyStoreResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("myLog", "SurveyActivity")
        setContentView(R.layout.activity_survey)

        val pref = getSharedPreferences("user", 0)
        scoreList.forEach { score -> score.uid = pref.getLong("uid", 0).toString() }
        Log.d("myLog", scoreList.toString())
    }

    override fun onStart() {
        super.onStart()

        val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url)).addConverterFactory(
            GsonConverterFactory.create()
        ).build()

        val surveyService = retrofit.create(SurveyService::class.java)

        surveyService.getStoreList().enqueue(object : Callback<List<SurveyStoreResponse>> {
            override fun onFailure(call: Call<List<SurveyStoreResponse>>, t: Throwable) {
                Log.i("myLog", t.toString())
            }

            override fun onResponse(
                call: Call<List<SurveyStoreResponse>>,
                response: Response<List<SurveyStoreResponse>>
            ) {
                storeList = response.body()
                surveyStart()
            }
        })
    }

    private fun surveyStart() {
        ratingBar.rating = 0f
        var store: SurveyStoreResponse? = storeList?.get(0)
        Glide.with(this).load(store?.img).into(foodImage)
        foodName?.text = store?.name

        inputScore()
    }

    private fun inputScore() {
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Prev.isClickable = true
            Next.isClickable = true
            if (fromUser && rating == 0f) {
                ratingBar.rating = 0.5f
            }
        }
        Prev.setOnClickListener {
            scoreList?.get(number)?.rating = ratingBar.rating
            scoreList?.get(number)?.rid = storeList?.get(number)?.id!!

            if (scoreList?.get(number)?.rating == 0f) {
                Next.isClickable = false
                Prev.isClickable = false
            } else {
                number--
                moveSurvey(number)
            }
        }

        Next.setOnClickListener {
            scoreList?.get(number)?.rating = ratingBar.rating
            scoreList?.get(number)?.rid = storeList?.get(number)?.id!!

            if (scoreList?.get(number)?.rating == 0f) {
                Next.isClickable = false
                Prev.isClickable = false
            } else {
                number++
                if (number == 5) {
                    val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create()).build()

                    val surveyService = retrofit.create(SurveyService::class.java)

                    Log.d("myLog", scoreList.toString())
                    val call = surveyService.surveyComplete(scoreList!!)

                    call.enqueue(object : Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.d("myLog", t.toString())
                        }

                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>
                        ) {
                            Log.d("myLog", "보내는데 성공했어요!!")
                        }
                    })

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    moveSurvey(number)
                }
            }
        }
    }

    private fun moveSurvey(num: Int) {
        if (num == 0) {
            Prev.visibility = View.INVISIBLE
        } else {
            Prev.visibility = View.VISIBLE
        }

        if (num != 5) {
            ratingBar.rating = scoreList?.get(number)?.rating!!

            var store: SurveyStoreResponse? = storeList?.get(num)
            Glide.with(this).load(store?.img).into(foodImage)
            foodName?.text = store?.mainMn
        }

    }
}