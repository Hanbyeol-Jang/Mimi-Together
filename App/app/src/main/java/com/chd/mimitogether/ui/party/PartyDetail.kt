package com.chd.mimitogether.ui.party

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.adapter.PartyMemberListAdapter
import com.chd.mimitogether.ui.party.adapter.StoreGridListAdapter
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.dto.Store
import com.chd.mimitogether.ui.party.dto.StorePageDto
import com.chd.mimitogether.ui.party.service.StoreService
import com.kakao.sdk.link.LinkClient
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PartyDetail : Fragment() {

    var pageno = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_partydetail, container, false)
        val mainActivity : MainActivity = activity as MainActivity

        val bundle = arguments
        val item : Party = bundle?.getSerializable("party_detail") as Party
        val name : TextView =  root.findViewById(R.id.party_detail_name)
        val location : TextView =  root.findViewById(R.id.partydetail_location)
        val membercount : TextView = root.findViewById(R.id.partydetail_member_count)
        val backbtn : ImageButton = root.findViewById(R.id.partydetail_backbtn)
        val scrollview : NestedScrollView = root.findViewById(R.id.partydetail_scrollview)
        val promise_time : TextView = root.findViewById(R.id.promise_time)
        val promise_location : TextView = root.findViewById(R.id.promise_location)

        name.text = item.ptName
        membercount.text = "("+item.userList.size+")"
        location.text = item.promiseLocation
        if(item.promiseTime != null){
            promise_time.text = item.promiseTime.toString()
        }
        if(item.promiseStore != null){
            promise_location.text = item.promiseStore.name
        }

        val adapter = StoreGridListAdapter()
        val gridview : RecyclerView = root.findViewById(R.id.store_grid)

        gridview.adapter = adapter
        gridview.layoutManager = GridLayoutManager(requireContext(), 2)


        val retrofit =
            Retrofit.Builder().baseUrl(getString(R.string.base_url))
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
        val storeService = retrofit.create(StoreService::class.java)

        storeService.getStoreList(pageno = 0)
            .enqueue(object : Callback<StorePageDto> {
                override fun onFailure(
                    call: Call<StorePageDto>,
                    t: Throwable
                ) {
                    Log.i("storeService", t.toString())
                }

                override fun onResponse(
                    call: Call<StorePageDto>,
                    response: Response<StorePageDto>
                ) {
                    adapter.storeList.addAll(response.body()?.content as List<Store>)
                    adapter.notifyDataSetChanged()
                }
            })

        scrollview.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(v.getChildAt(v.childCount - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
                    scrollY > oldScrollY) {

                    storeService.getStoreList(pageno = pageno)
                        .enqueue(object : Callback<StorePageDto> {
                            override fun onFailure(
                                call: Call<StorePageDto>,
                                t: Throwable
                            ) {
                                Log.i("storeService", t.toString())
                            }

                            override fun onResponse(
                                call: Call<StorePageDto>,
                                response: Response<StorePageDto>
                            ) {
                                Log.e("pageload", "here")
                                adapter.storeList.addAll(response.body()?.content as List<Store>)
                                adapter.notifyDataSetChanged()
                                pageno++
                            }
                        })
                }
            }
        }))


        adapter.setItemClickListener( object : StoreGridListAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                mainActivity.saveStore(adapter.storeList[position])

                mainActivity.replaceFragment(StoreDetail())
            }
        })

//        val adapter = PartyMemberListAdapter()
//        val recyclerView : RecyclerView = root.findViewById(R.id.party_member_list_view)
//        recyclerView?.adapter = adapter
//
//        recyclerView?.layoutManager = GridLayoutManager(requireContext(),3)
//
//        adapter.memberList.addAll(item.userList)
//        adapter.notifyDataSetChanged()
//
//        val share_btn : Button = root.findViewById(R.id.party_shared_btn)
//
//        share_btn.setOnClickListener {
//            val templateId = 39892L
//            val templateArgs = HashMap<String, String>()
//            Log.e("clickevent", item.id)
//            templateArgs["partyId"] = item.id
//
//            LinkClient.instance.customTemplate(requireContext(), templateId, templateArgs) { linkResult, error ->
//                if (error != null) {
//                    Log.e("myLog", "카카오링크 보내기 실패", error)
//                }
//                else if (linkResult != null) {
//                    Log.d("myLog", "카카오링크 보내기 성공 ${linkResult.intent}")
//                    startActivity(linkResult.intent)
//
//                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
//                    Log.w("myLog", "Warning Msg: ${linkResult.warningMsg}")
//                    Log.w("myLog", "Argument Msg: ${linkResult.argumentMsg}")
//                }
//            }
//
//        }

        backbtn.setOnClickListener {
            mainActivity.replaceFragment(PartyListFragment())
        }
        return root
    }

}