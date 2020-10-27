package com.ssafy.frontend.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.frontend.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onStart() {
        super.onStart()

        val adapter = TestAdapter()
        recyclerView2?.adapter = adapter

        recyclerView2?.layoutManager = LinearLayoutManager(requireContext())

        val retrofit = Retrofit.Builder().baseUrl("http://192.168.28.33:9999/").addConverterFactory(
            GsonConverterFactory.create()
        ).build()


        Log.d("mylog","hello")

        val testService = retrofit.create(TestService::class.java)
        testService.users().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("mylog", t.toString())
            }

            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                adapter.tempList.addAll(response.body() as List<User>)
                adapter.notifyDataSetChanged()
            }
        })

    }
}