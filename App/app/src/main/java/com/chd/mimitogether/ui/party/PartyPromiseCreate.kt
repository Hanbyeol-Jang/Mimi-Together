package com.chd.mimitogether.ui.party

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.DateReq
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.service.PartyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class PartyPromiseCreate : Fragment(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    var day_text: TextView? = null
    var time_text: TextView? = null
    lateinit var date : DateReq

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_promise, container, false)
        val mainActivity: MainActivity = activity as MainActivity

        val store_name: TextView = root.findViewById(R.id.promise_store_name)
        day_text = root.findViewById(R.id.promise_day_text)
        time_text = root.findViewById(R.id.promise_time_text)
        val day_btn: ImageButton = root.findViewById(R.id.day_btn)
        val time_btn: ImageButton = root.findViewById(R.id.time_btn)
        val promise_btn: Button = root.findViewById(R.id.promise_btn)

        val store = mainActivity.loadStore()
        val party: Party = mainActivity.selectParty!!

        val c = Calendar.getInstance()
        store_name.text = store.name
        day_text!!.text = String.format(
            "%d-%02d-%02d",
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH) + 1,
            c.get(Calendar.DAY_OF_MONTH)
        )
        time_text!!.text =
            String.format("%02d : %02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE))

        date = DateReq(
            day = c.get(Calendar.DAY_OF_MONTH),
            hour = c.get(Calendar.HOUR_OF_DAY),
            min = c.get(
                Calendar.MINUTE
            ),
            month = c.get(Calendar.MONTH) + 1,
            pid = party.id,
            storeid = store.id.toString(),
            year = c.get(
                Calendar.YEAR
            )
        )

        time_btn.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePicker =
                TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
            timePicker.show()
        }

        day_btn.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(activity as MainActivity, this, year, month, day)
            datePicker.show()
        }

        promise_btn.setOnClickListener {
            val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url)).addConverterFactory(
                GsonConverterFactory.create()
            ).build()
            val partyService = retrofit.create(PartyService::class.java)

            partyService.createPromise(date).enqueue(object : Callback<Party> {
                override fun onResponse(call: Call<Party>, response: Response<Party>) {
                    mainActivity.replaceFragment(PartyListFragment())
                }

                override fun onFailure(call: Call<Party>, t: Throwable) {
                    Log.e("promiseError", t.toString())
                }
            })
        }

        return root
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d("myLog", "$hourOfDay $minute")
        time_text!!.text = String.format("%02d : %02d", hourOfDay, minute)
        date.hour = hourOfDay
        date.min = minute
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.d("myLog", "$year $month $dayOfMonth")
        day_text!!.text = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
        date.year = year
        date.month = month
        date.day = dayOfMonth
    }

}
