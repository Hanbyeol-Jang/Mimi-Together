package com.chd.mimitogether.ui.auction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.service.AuctionService
import com.chd.mimitogether.ui.auction.adapter.MyListAdapter
import com.chd.mimitogether.ui.auction.dto.Auction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.auction_fragment_mylist, container, false)
        val mainActivity : MainActivity = activity as MainActivity

        val adapter = MyListAdapter()
        val recyclerView = root.findViewById<RecyclerView>(R.id.mylist_recyler_view)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val uid = mainActivity.loadData("uid")

        if (uid != "0") {
            val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val auctionService = retrofit.create(AuctionService::class.java)

            auctionService.getDiningList(uid).enqueue(object: Callback<List<Auction>> {
                override fun onResponse(
                    call: Call<List<Auction>>,
                    response: Response<List<Auction>>
                ) {
                    adapter.contents.addAll(response.body() as List<Auction>)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<List<Auction>>, t: Throwable) {
                    Log.d("myLog", "MyListFragment: ${t.toString()}")
                }

            })
        } else {
            Log.d("myLog", "MyListFragment: uid 없음")
        }
        return root
    }

}