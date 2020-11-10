package com.chd.mimitogether.ui.profile

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.StoreDetail
import com.chd.mimitogether.ui.party.adapter.PartyAddressListAdapter
import com.chd.mimitogether.ui.party.adapter.StoreGridListAdapter
import com.chd.mimitogether.ui.party.dto.Review.ReviewRequest
import com.chd.mimitogether.ui.party.dto.Store
import com.chd.mimitogether.ui.party.service.ReviewService
import com.chd.mimitogether.ui.party.service.StoreService
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val mainActivity: MainActivity = activity as MainActivity
        val logout_btn: Button = root.findViewById(R.id.logout_btn)
        mainActivity.selectParty = null

        val nicname: TextView = root.findViewById(R.id.profile_name)
        nicname.text = mainActivity.loadData("uname") + " 님"

        val adapter = MyReviewAdapter()
        val recyclerView: RecyclerView = root.findViewById(R.id.myreivew_recyclerview)

        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val reviewService = retrofit.create(ReviewService::class.java)
        reviewService.getUserReview(mainActivity.loadData("uid"))
            .enqueue(object : Callback<List<ReviewRequest>> {
                override fun onResponse(
                    call: Call<List<ReviewRequest>>,
                    response: Response<List<ReviewRequest>>
                ) {
                    val list = response.body()
                    if (list != null) {
                        adapter.reviewlist.addAll(list)
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.e("myLog", "Failllllllllllllllllllll")
                    }
                }

                override fun onFailure(call: Call<List<ReviewRequest>>, t: Throwable) {
                    Log.e("myLog", t.toString())
                }


            })



        adapter.setItemClickListener(object : MyReviewAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val storeService = retrofit.create(StoreService::class.java)
                storeService.getStoreDetail(adapter.reviewlist[position].resId.toString())
                    .enqueue(object : Callback<Store> {
                        override fun onResponse(call: Call<Store>, response: Response<Store>) {
                            val store = response.body()
                            if (store != null) {
                                mainActivity.saveStore(store)
                                mainActivity.replaceFragment(StoreDetail())
                            } else {
                                Log.e("myLog", "Failllllllllllllllllllll")
                            }
                        }

                        override fun onFailure(call: Call<Store>, t: Throwable) {
                            Log.e("myLog", t.toString())
                        }
                    })


            }
        })


        logout_btn.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("로그아웃")
            builder.setMessage("정말 로그아웃 하시겠습니까?")
            builder.setPositiveButton(
                "확인"
            ) { dialogInterface: DialogInterface?, i: Int ->
                // 로그아웃
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Log.e("LogoutService", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    } else {
                        Log.i("LogoutService", "로그아웃 성공. SDK에서 토큰 삭제됨")
                        mainActivity.doLogout()
                    }
                }
            }
            builder.setNegativeButton(
                "취소"
            ) { dialogInterface: DialogInterface?, i: Int -> }
            builder.show()

        }



        return root
    }

}