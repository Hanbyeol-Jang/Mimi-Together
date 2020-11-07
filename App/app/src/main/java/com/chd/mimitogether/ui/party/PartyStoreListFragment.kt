package com.chd.mimitogether.ui.party

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.adapter.PartyListAdapter
import com.chd.mimitogether.ui.party.adapter.StoreGridListAdapter
import com.chd.mimitogether.ui.party.dto.Store
import com.chd.mimitogether.ui.party.dto.StorePageDto
import com.chd.mimitogether.ui.party.service.StoreService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PartyStoreListFragment : Fragment() {

    var pageno : Int = 1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_party_store_list, container, false)
        val mainActivity : MainActivity = activity as MainActivity

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

        gridview.addOnScrollListener( object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!recyclerView.canScrollVertically(1)){

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
                                adapter.storeList.addAll(response.body()?.content as List<Store>)
                                adapter.notifyDataSetChanged()
                                pageno++
                            }
                        })
                }
            }
        })

        adapter.setItemClickListener( object : StoreGridListAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                mainActivity.saveStore(adapter.storeList[position])
//
//                val f = StoreDetail()
//                val bundle = Bundle()
//                bundle.putSerializable("store_detail", adapter.storeList[position])
//                f.arguments = bundle

                mainActivity.replaceFragment(StoreDetail())
            }
        })

        return root
    }

}