package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.Review.ReviewRequest
import com.chd.mimitogether.ui.party.service.ReviewService
import kotlinx.android.synthetic.main.activity_survey.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ReviewWriteFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_reviewwrite, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.setToolbarTitle("리뷰 작성")

        val currentStore = mainActivity.loadStore()
        val currentUid = mainActivity.loadData("uid")
        val currentUname = mainActivity.loadData("uname")

        val storeName = root.findViewById<TextView>(R.id.review_store_name)
        val ratingBar = root.findViewById<RatingBar>(R.id.review_write_rating)
        val reviewText = root.findViewById<EditText>(R.id.review_store_content)
        val btn = root.findViewById<Button>(R.id.review_write_submit)

        storeName.text = currentStore.name
        ratingBar.rating = 3f
        ratingBar.setOnRatingBarChangeListener{ ratingBar, rating, fromUser ->
            if (fromUser && rating == 0f) {
                ratingBar.rating = 0.5f
            }
        }

        btn.setOnClickListener {
            val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build()
            val reviewService = retrofit.create(ReviewService::class.java)

            reviewService.writeReview(ReviewRequest(rating = ratingBar.rating, resId = currentStore.id, resName = currentStore.name, review = reviewText.text.toString(),userId = currentUid,userName = currentUname)).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    mainActivity.onBackPressed()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("myLog", "$t")
                }

            })
        }

        return root
    }

}