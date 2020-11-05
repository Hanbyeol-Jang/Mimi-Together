package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.dto.PartyCreate
import com.chd.mimitogether.ui.party.service.PartyService
import kotlinx.android.synthetic.main.fragment_partylist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PartyCreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_partylist, container, false)
        val mainActivity : MainActivity = activity as MainActivity


        val btn : Button = root.findViewById(R.id.create_party_btn)

        btn.setOnClickListener {
            if(input_party_name.text.toString() == ""){
                Toast.makeText(mainActivity.applicationContext, "모임 이름을 적어주세요.", Toast.LENGTH_SHORT).show()
            }else{
                val retrofit =
                    Retrofit.Builder().baseUrl(getString(R.string.base_url))
                        .addConverterFactory(
                            GsonConverterFactory.create()
                        ).build()
                val partyService = retrofit.create(PartyService::class.java)

                val uid = mainActivity.loadData("uid")
                val partyName = input_party_name.text.toString()
                Log.e("create",uid)
                Log.e("create",partyName)
                val partyCreate = PartyCreate(ptName = partyName, userID = uid, promiseLocation = "string")
                partyService.createParty(partyCreate)
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
                            Log.e("createParty",response.body().toString())
                            val f = PartyDetail()
                            val bundle = Bundle()
                            bundle.putSerializable("party_detail", response.body())
                            f.arguments = bundle

                            mainActivity.replaceFragment(f)
                        }
                    })
            }
        }

        return root
    }

}