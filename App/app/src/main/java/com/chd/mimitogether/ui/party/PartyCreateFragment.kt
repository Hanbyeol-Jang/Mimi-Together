package com.chd.mimitogether.ui.party

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.dto.PartyCreate
import com.chd.mimitogether.ui.party.service.CityService
import com.chd.mimitogether.ui.party.service.PartyService
import kotlinx.android.synthetic.main.fragment_partylist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList


class PartyCreateFragment : Fragment() {

    var siText = ""
    var guText = ""
    var dongText = ""
    var item1 = ArrayList<String>()
    var item2 = ArrayList<String>()
    var item3 = ArrayList<String>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_partylist, container, false)


        val mainActivity: MainActivity = activity as MainActivity
        mainActivity.setToolbarTitle("모임 만들기")
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val clickListener = View.OnClickListener {
            // 키보드 숨기기
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(input_party_name.windowToken, 0)
        }
        val layout = root.findViewById<ConstraintLayout>(R.id.create_layout)
        layout.setOnClickListener(clickListener)

        val touchListener = View.OnTouchListener { _, _ ->
            // 키보드 숨기기
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(input_party_name.windowToken, 0)
        }

        val si: Spinner = root.findViewById(R.id.party_si)
        val gu: Spinner = root.findViewById(R.id.party_gu)
        val dong: Spinner = root.findViewById(R.id.party_dong)
        si.setOnTouchListener(touchListener)
        gu.setOnTouchListener(touchListener)
        dong.setOnTouchListener(touchListener)

        val btn: Button = root.findViewById(R.id.move_address_btn)

        val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create()).build()
        val cityService = retrofit.create(CityService::class.java)

        cityService.getSiList().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                item1.add("시")
                item1.addAll(response.body() as List<String>)
                val adapter1 =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item1)
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                si.adapter = adapter1
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(requireContext(), "서버가 불안정합니다.", Toast.LENGTH_SHORT).show()
                Log.e("getSiList", "오류: $t")
            }
        })

        item2.add("군/구")
        val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gu.adapter = adapter2

        item3.add("동")
        val adapter3 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item3)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dong.adapter = adapter3

        si.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                guText = ""
                dongText = ""

                if (item1[position] != "시") {
                    siText = item1[position]
                    cityService.getGuList(si = siText).enqueue(object : Callback<List<String>> {
                        override fun onResponse(
                            call: Call<List<String>>,
                            response: Response<List<String>>
                        ) {
                            item2.clear()
                            item2.add("군/구")
                            item3.clear()
                            item3.add("동")
                            item2.addAll(response.body() as List<String>)

                            val adapter2 = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                item2
                            )
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            gu.adapter = adapter2

                            val adapter3 = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                item3
                            )
                            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            dong.adapter = adapter3
                        }

                        override fun onFailure(call: Call<List<String>>, t: Throwable) {
                            Toast.makeText(requireContext(), "서버가 불안정합니다.", Toast.LENGTH_SHORT)
                                .show()
                            Log.e("getguList", "오류: $t")
                        }

                    })


                    val adapter2 =
                        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item2)
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    gu.adapter = adapter2
                } else {
                    siText = ""
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                siText = ""
            }

        }

        gu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dongText = ""
                if (item2[position] != "군/구") {
                    guText = item2[position]
                    cityService.getDongList(si = siText, gun = guText)
                        .enqueue(object : Callback<List<String>> {
                            override fun onResponse(
                                call: Call<List<String>>,
                                response: Response<List<String>>
                            ) {
                                item3.clear()
                                item3.add("동")
                                item3.addAll(response.body() as List<String>)

                                val adapter3 = ArrayAdapter(
                                    requireContext(),
                                    android.R.layout.simple_spinner_item,
                                    item3
                                )
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                dong.adapter = adapter3
                            }

                            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                                Toast.makeText(requireContext(), "서버가 불안정합니다.", Toast.LENGTH_SHORT)
                                    .show()
                                Log.e("getDongList", "오류: $t")
                            }

                        })
                } else {
                    guText = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                guText = ""
            }
        }

        dong.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (item3[position] != "동") {
                    dongText = item3[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                dongText = ""
            }
        }

        btn.setOnClickListener {
            val ptName = input_party_name.text.toString()
            if (ptName == "") {
                Toast.makeText(mainActivity.applicationContext, "모임 이름을 적어주세요.", Toast.LENGTH_SHORT)
                    .show()
            } else if (siText == "") {
                Toast.makeText(requireContext(), "시 를 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else if (guText == "") {
                Toast.makeText(requireContext(), "군/구 를 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else if (dongText == "") {
                Toast.makeText(requireContext(), "동 을 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val retrofit =
                    Retrofit.Builder().baseUrl(getString(R.string.base_url))
                        .addConverterFactory(
                            GsonConverterFactory.create()
                        ).build()
                val partyService = retrofit.create(PartyService::class.java)

                val uid = mainActivity.loadData("uid")
                val location = "$siText $guText $dongText"

                val partyCreate =
                    PartyCreate(ptName = ptName, userID = uid, promiseLocation = location)
                partyService.createParty(partyCreate)
                    .enqueue(object : Callback<Party> {
                        override fun onFailure(
                            call: Call<Party>,
                            t: Throwable
                        ) {
                            Toast.makeText(requireContext(), "서버가 불안정합니다.", Toast.LENGTH_SHORT)
                                .show()
                            Log.i("userService", t.toString())
                        }

                        override fun onResponse(
                            call: Call<Party>,
                            response: Response<Party>
                        ) {
                            Log.e("createParty", response.body().toString())
                            mainActivity.selectParty = response.body()

                            mainActivity.replaceFragment(PartyDetail(), false)
                        }
                    })
            }
        }

        return root
    }

}