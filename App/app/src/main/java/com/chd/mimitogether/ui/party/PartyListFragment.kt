package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.adapter.PartyListAdapter
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.service.PartyService
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        val btn : FloatingActionButton = root.findViewById(R.id.f_a_b)

        val adapter = PartyListAdapter()
        val recyclerView : RecyclerView = root.findViewById(R.id.party_list_recycler_view)

        mainActivity.setToolbarTitle("모임 리스트")

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
                    Toast.makeText( requireContext(), "서버가 불안정합니다.", Toast.LENGTH_SHORT).show()
                    Log.i("userService", t.toString())
                }

                override fun onResponse(
                    call: Call<List<Party>>,
                    response: Response<List<Party>>
                ) {
                    val partyList = response.body()
                    partyList?.let{
                        if(partyList.isEmpty()) {
                            val emptyLayout = root.findViewById<ConstraintLayout>(R.id.empty_layout)
                            emptyLayout.visibility = View.VISIBLE
                        } else {
                            adapter.partyList.addAll(response.body() as List<Party>)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            })

        adapter.setItemClickListener( object : PartyListAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                mainActivity.selectParty = adapter.partyList[position]

                mainActivity.replaceFragment(PartyDetail(), true)
            }
        })

        btn.setOnClickListener {
            val mainActivity : MainActivity = activity as MainActivity
            mainActivity.replaceFragment(PartyCreateFragment(), true)

//            val transaction: FragmentTransaction = mainActivity.supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.frame_layout, PartyCreateFragment()).addToBackStack("partyList").commit()
        }



        return root
    }
}