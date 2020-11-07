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
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.dto.PartyCreate
import com.chd.mimitogether.ui.party.service.PartyService
import kotlinx.android.synthetic.main.fragment_partylist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PartyCreateResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_partycreateresult, container, false)
        val mainActivity : MainActivity = activity as MainActivity

        val bundle = arguments
        val ptName : String = bundle?.getSerializable("partyName") as String
        val ptAddress : String = bundle?.getSerializable("partyAddress") as String

        val textview_ptName : TextView = root.findViewById(R.id.result_ptName)
        val textview_ptAddress : TextView = root.findViewById(R.id.result_ptAddress)
        val btn :Button = root.findViewById(R.id.party_create_button)

        textview_ptName.text = ptName
        textview_ptAddress.text = ptAddress

        btn.setOnClickListener {
            val retrofit =
                Retrofit.Builder().baseUrl(getString(R.string.base_url))
                    .addConverterFactory(
                        GsonConverterFactory.create()
                    ).build()
            val partyService = retrofit.create(PartyService::class.java)

            val uid = mainActivity.loadData("uid")

            val partyCreate = PartyCreate(ptName = ptName, userID = uid, promiseLocation = ptAddress )
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

        return root
    }
}