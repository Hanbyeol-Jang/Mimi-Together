package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.adapter.ReviewListAdapter
import com.chd.mimitogether.ui.party.dto.Review.Content
import com.chd.mimitogether.ui.party.dto.Review.ReviewList
import com.chd.mimitogether.ui.party.dto.Store
import com.chd.mimitogether.ui.party.dto.StorePageDto
import com.chd.mimitogether.ui.party.service.ReviewService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewFragment : Fragment() {

    var pageno : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_review, container, false)
        val mainActivity : MainActivity = activity as MainActivity

        val adapter = ReviewListAdapter()
        val reviewView : RecyclerView = root.findViewById(R.id.recyclerview_review)

        reviewView.adapter = adapter
        reviewView.layoutManager = LinearLayoutManager(requireContext())


        val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build()
        val reviewService = retrofit.create(ReviewService::class.java)

        val store = mainActivity.loadStore()
        val reviewNotFound : LinearLayout = root.findViewById(R.id.review_not_found)
        reviewNotFound.visibility = View.GONE

        reviewService.getStoreview(id = store.id, pageno = 0).enqueue(object : Callback<ReviewList>{
            override fun onResponse(call: Call<ReviewList>, response: Response<ReviewList>) {

                adapter.reviewList.addAll(response.body()?.content as List<Content>)
                adapter.notifyDataSetChanged()
                if(adapter.reviewList.size == 0){
                    reviewNotFound.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ReviewList>, t: Throwable) {
                Toast.makeText( requireContext(), "서버가 불안정합니다.", Toast.LENGTH_SHORT).show()
                Log.e("reviewError", t.toString())
            }

        })

        reviewView.addOnScrollListener( object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!recyclerView.canScrollVertically(1)){
                    reviewService.getStoreview(id = store.id, pageno = pageno).enqueue(object : Callback<ReviewList>{
                        override fun onResponse(call: Call<ReviewList>, response: Response<ReviewList>) {

                            adapter.reviewList.addAll(response.body()?.content as List<Content>)
                            adapter.notifyDataSetChanged()
                            if(adapter.reviewList.size == 0){
                                reviewNotFound.visibility = View.VISIBLE
                            }
                            pageno++
                        }

                        override fun onFailure(call: Call<ReviewList>, t: Throwable) {
                            Toast.makeText( requireContext(), "서버가 불안정합니다.", Toast.LENGTH_SHORT).show()
                            Log.e("reviewError", t.toString())
                        }

                    })

                }
            }
        })

        return root
    }
}