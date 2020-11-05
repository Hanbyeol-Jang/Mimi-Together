package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.dto.UserRequest
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.service.PartyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PartyJoin: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_party_join, container, false)
        val mainActivity : MainActivity = activity as MainActivity

        val bundle = arguments
        val party_id : String = bundle?.getSerializable("partyId") as String
        val user_id = mainActivity.loadData("uid")
        val user_name = mainActivity.loadData("uname")

        var party : Party? = null

        val fragment_party_detail = PartyDetail()
        val response_bundle = Bundle()

        val retrofit =
            Retrofit.Builder().baseUrl(getString(R.string.base_url))
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
        val partyService = retrofit.create(PartyService::class.java)

        partyService.getPartyDetail(id = party_id).enqueue(object : Callback<Party>{
            override fun onResponse(call: Call<Party>, response: Response<Party>) {

                Log.d("detailresult", response.body().toString())
                party = response.body()

                Log.e("mylog",party.toString())

                val ptName : TextView = root.findViewById(R.id.party_join_ptname)
                ptName.text = party?.ptName

                for (i in 0 until party!!.userList.size){
                    if(party!!.userList[i].id == user_id){
                        response_bundle.putSerializable("party_detail", party)
                        fragment_party_detail.arguments = response_bundle

                        mainActivity.replaceFragment(fragment_party_detail)

                        break
                    }
                }

            }

            override fun onFailure(call: Call<Party>, t: Throwable) {
                Log.i("JoinService", t.toString())
            }
        })


        val btn : Button = root.findViewById(R.id.party_join_btn)
        btn.setOnClickListener {
            val retrofit =
                Retrofit.Builder().baseUrl(getString(R.string.base_url))
                    .addConverterFactory(
                        GsonConverterFactory.create()
                    ).build()
            val partyService = retrofit.create(PartyService::class.java)

            partyService.joinParty(partyId = party_id, userId = user_id)
                .enqueue(object : Callback<Party> {
                    override fun onFailure(
                        call: Call<Party>,
                        t: Throwable
                    ) {
                        Log.i("userService", t.toString())
                    }

                    override fun onResponse(
                        call: Call<Party>,
                        response: Response<Party>
                    ) {
                        party?.userList?.add(UserRequest(id = user_id, uiName = user_name, isSurvey = "true", uiProfile = "",uiThumb = "",uiToken = ""))
                        Log.d("joinParty",party.toString())

                        response_bundle.putSerializable("party_detail", party)
                        fragment_party_detail.arguments = response_bundle

                        mainActivity.replaceFragment(fragment_party_detail)

                    }
                })

        }


        return root
    }

}