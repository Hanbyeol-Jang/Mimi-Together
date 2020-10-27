package com.ssafy.frontend.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.frontend.CustomAdapter
import com.ssafy.frontend.GithubService
import com.ssafy.frontend.R
import com.ssafy.frontend.RepositoryItem
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onStart() {
        super.onStart()

        val adapter = CustomAdapter()
        recyclerView?.adapter = adapter

        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(
            GsonConverterFactory.create()).build()

        buttonRequest.setOnClickListener {
            val githubService = retrofit.create(GithubService::class.java)
            githubService.users().enqueue(object : Callback<List<RepositoryItem>> {
                override fun onFailure(call: Call<List<RepositoryItem>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<List<RepositoryItem>>,
                    response: Response<List<RepositoryItem>>
                ) {
                    adapter.userList.addAll(response.body() as List<RepositoryItem>)
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }


}