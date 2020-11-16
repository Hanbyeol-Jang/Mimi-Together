package com.chd.mimitogether.ui.party

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.dto.jusoSearch.JusoResponse
import com.chd.mimitogether.service.JusoService
import com.chd.mimitogether.ui.party.adapter.PartyAddressListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PartyInputAddressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_party_addressinput, container, false)
        val mainActivity : MainActivity = activity as MainActivity

        val bundle = arguments
        val ptName : String = bundle?.getSerializable("partyName") as String

        val adapter = PartyAddressListAdapter()
        val recyclerView : RecyclerView = root.findViewById(R.id.recyclerView_address)
        recyclerView?.adapter = adapter

        recyclerView?.layoutManager = LinearLayoutManager(requireContext())


        val search_Btn : Button = root.findViewById(R.id.address_search_btn)

        search_Btn.setOnClickListener {


            val retrofit = Retrofit.Builder().baseUrl("http://www.juso.go.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val keyword : TextView = root.findViewById(R.id.input_address)

            // 키보드 숨기기
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(keyword.windowToken, 0)

            val jusoService = retrofit.create(JusoService::class.java)
            jusoService.getJuso(keyword.text.toString()).enqueue(object: Callback<JusoResponse> {
                override fun onResponse(call: Call<JusoResponse>, response: Response<JusoResponse>) {
                    if(response.body()!!.results.common.errorCode== "0"){
                        adapter.jusoList.clear()
                        adapter.jusoList.addAll(response.body()!!.results.juso)
                        adapter.notifyDataSetChanged()
                    }else{
                        Log.e("mylog",response.body()!!.results.common.errorCode)
                        adapter.notifyDataSetChanged()
                    }

                }

                override fun onFailure(call: Call<JusoResponse>, t: Throwable) {
                    Toast.makeText( requireContext(), "서버가 불안정합니다.", Toast.LENGTH_SHORT).show()
                    Log.e("myLog", t.toString())
                }

            })
        }

        adapter.setItemClickListener( object : PartyAddressListAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Log.d("mylog","ㅇㅇㅇㅇ")
                val juso = adapter.jusoList[position]

                val f = PartyCreateResultFragment()
                val bundle = Bundle()
                bundle.putSerializable("partyName", ptName)
                bundle.putSerializable("partyAddress", juso.jibunAddr)
                f.arguments = bundle
                mainActivity.replaceFragment(f, false)
            }
        })


        return root
    }
}