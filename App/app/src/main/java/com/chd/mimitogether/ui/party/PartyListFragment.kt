package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.adapter.PartyListAdapter
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.service.PartyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PartyListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_party, container, false)
        val mainActivity : MainActivity = activity as MainActivity
        val btn : ImageButton = root.findViewById(R.id.party_create_btn01)

        val adapter = PartyListAdapter()
        val recyclerView : RecyclerView = root.findViewById(R.id.party_list_recycler_view)

        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        val retrofit =
            Retrofit.Builder().baseUrl(getString(R.string.base_url))
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
        val partyService = retrofit.create(PartyService::class.java)

        val uid = mainActivity.loadData("uid")


        partyService.getPartyList(id = uid).enqueue(object : Callback<List<Party>> {
                override fun onFailure(
                    call: Call<List<Party>>,
                    t: Throwable
                ) {
                    Log.i("userService", t.toString())
                }

                override fun onResponse(
                    call: Call<List<Party>>,
                    response: Response<List<Party>>
                ) {
                    Log.d("mylog2",response.body().toString())
                    adapter.partyList.addAll(response.body() as List<Party>)
                    adapter.notifyDataSetChanged()
                }
            })

        adapter.setItemClickListener( object : PartyListAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                Log.d("mylog",adapter.partyList[position].toString())
                val f = PartyDetail()
                val bundle = Bundle()
                bundle.putSerializable("party_detail", adapter.partyList[position])
                f.arguments = bundle

                mainActivity.selectParty = adapter.partyList[position]

                mainActivity.replaceFragment(f)
            }
        })

        btn.setOnClickListener {
            val mainActivity : MainActivity = activity as MainActivity
            mainActivity.replaceFragment(PartyCreateFragment())
        }

        return root
    }

}